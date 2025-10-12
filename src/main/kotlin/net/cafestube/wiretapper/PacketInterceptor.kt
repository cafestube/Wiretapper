package net.cafestube.wiretapper

import io.netty.channel.Channel
import io.netty.channel.ChannelDuplexHandler
import io.netty.channel.ChannelHandlerContext
import io.netty.channel.ChannelPromise
import io.papermc.paper.connection.PlayerConnection
import io.papermc.paper.network.ChannelInitializeListener
import io.papermc.paper.network.ChannelInitializeListenerHolder
import net.kyori.adventure.key.Key
import net.minecraft.network.Connection
import net.minecraft.network.protocol.Packet
import net.minecraft.server.network.ServerCommonPacketListenerImpl
import org.bukkit.plugin.Plugin
import org.bukkit.plugin.java.JavaPlugin

class PacketInterceptor(
    val owningPlugin: JavaPlugin
) : ChannelInitializeListener {

    private val key = Key.key(owningPlugin.namespace(), "wiretapper/packet-interceptor${ChannelInitializeListenerHolder.getListeners()
        .keys.indexOfLast { it.namespace() == owningPlugin.namespace() && it.value().startsWith("wiretapper/") } + 1}")

    private val listenersByPlugin = HashMap<Plugin, MutableList<PacketListenerBase>>()

    private var hasReceiveListener: Boolean = false
    private var hasSendListener: Boolean = false


    init {
        ChannelInitializeListenerHolder.addListener(key, this)
    }

    fun registerListener(plugin: JavaPlugin, listener: PacketListenerBase) {
        if(listener is IncomingPacketListener) hasReceiveListener = true
        if(listener is OutgoingPacketListener) hasSendListener = true
        listenersByPlugin.computeIfAbsent(plugin) { ArrayList() }.add(listener)
    }

    inline fun <reified T: Any> registerListener(plugin: JavaPlugin, direction: PacketDirection, crossinline callback: (PacketEvent, T) -> Unit) {
        if(direction == PacketDirection.SERVERBOUND) {
            registerListener(plugin, IncomingPacketListener { event ->
                if(event.packet is T) { callback(event, event.getPacketAs()) }
            })
            return
        } else {
            registerListener(plugin, OutgoingPacketListener { event ->
                if(event is T) { callback(event, event.getPacketAs()) }
            })
        }
    }


    fun unregisterListeners(plugin: Plugin) {
        listenersByPlugin.remove(plugin)
        //We don't update hasReceiveListener/hasSendListener as it's not a big deal if they stay true even if no listeners exist
        //It's an optimization that doesn't need to be perfect
    }

    override fun afterInitChannel(p0: Channel) {
        p0.pipeline().addBefore("packet_handler", key.asString(), object : ChannelDuplexHandler() {
            val packetHandler = p0.pipeline()["packet_handler"] as Connection

            override fun write(ctx: ChannelHandlerContext, msg: Any, promise: ChannelPromise) {
                val connection = getPlayerConnection(packetHandler)

                if(msg is Packet<*> && connection != null) {
                    val result = handleOutgoingPacket(connection, msg) ?: return
                    ctx.write(result, promise)
                    return
                }

                ctx.write(msg, promise)
            }


            override fun channelRead(ctx: ChannelHandlerContext, msg: Any) {
                val connection = getPlayerConnection(packetHandler)

                if(msg is Packet<*> && connection != null) {
                    val result = handleIncomingPacket(connection, msg) ?: return
                    ctx.fireChannelRead(result)
                    return
                }
                ctx.fireChannelRead(msg)
            }

        })
    }

    private fun getPlayerConnection(connection: Connection): PlayerConnection? {
        val packetListener = connection.packetListener
        if(packetListener is ServerCommonPacketListenerImpl) {
            return packetListener.paperConnection()
        }

        return null
    }

    private fun handleIncomingPacket(
        player: PlayerConnection,
        msg: Packet<*>
    ): Packet<*>? {
        if(!hasReceiveListener) return msg

        return handlePacket(player, msg, PacketDirection.SERVERBOUND)
    }

    private fun handleOutgoingPacket(
        player: PlayerConnection,
        msg: Packet<*>
    ): Packet<*>? {
        if(!hasSendListener) return msg

        return handlePacket(player, msg, PacketDirection.CLIENTBOUND)
    }

    private fun handlePacket(player: PlayerConnection, msg: Packet<*>, direction: PacketDirection): Packet<*>? {
        val packetEvent = PacketEvent(player, msg, direction)

        for ((_, list) in listenersByPlugin) {
            for (base in list) {
                if(base is OutgoingPacketListener) {
                    base.handleOutgoing(packetEvent)
                }
            }
        }

        if(packetEvent.isCancelled) return null

        val packet = packetEvent.packet
        check(packet !is Packet<*>) { "Packet modified to a non-packet type!" }

        return packetEvent.packet as Packet<*>
    }

    fun destroy() {
        this.listenersByPlugin.clear()
        ChannelInitializeListenerHolder.removeListener(key)
    }
}