package net.cafestube.wiretapper

import io.netty.channel.Channel
import io.netty.channel.ChannelDuplexHandler
import io.netty.channel.ChannelHandlerContext
import io.netty.channel.ChannelPromise
import io.papermc.paper.network.ChannelInitializeListener
import io.papermc.paper.network.ChannelInitializeListenerHolder
import net.kyori.adventure.key.Key
import net.minecraft.network.Connection
import org.bukkit.plugin.java.JavaPlugin


class Wiretapper(
    val plugin: JavaPlugin
) : ChannelInitializeListener {

    private val key = Key.key(plugin.namespace(), "wiretapper/packet-interceptor${ChannelInitializeListenerHolder.getListeners()
        .keys.indexOfLast { it.namespace() == plugin.namespace() && it.value().startsWith("wiretapper/") } + 1}")

    init {
        ChannelInitializeListenerHolder.addListener(key, this)
    }


    override fun afterInitChannel(p0: Channel) {
        p0.pipeline().addBefore("packet_handler", key.asString(), object : ChannelDuplexHandler() {
            val packetHandler = p0.pipeline()["packet_handler"] as Connection

            override fun write(ctx: ChannelHandlerContext, msg: Any, promise: ChannelPromise) {

                ctx.write(msg, promise)
            }

            override fun channelRead(ctx: ChannelHandlerContext, msg: Any) {

                ctx.fireChannelRead(msg)
            }

        })
    }

    fun destroy() {
        ChannelInitializeListenerHolder.removeListener(key)
    }
}