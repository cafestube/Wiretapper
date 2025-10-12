package net.cafestube.wiretapper.plugin

import net.cafestube.wiretapper.PacketDirection
import net.cafestube.wiretapper.PacketEvent
import net.cafestube.wiretapper.PacketListenerBase
import org.bukkit.plugin.java.JavaPlugin

fun JavaPlugin.registerPacketListener(listener: PacketListenerBase) {
    getGlobalInterceptor().registerListener(this, listener)
}

inline fun <reified T: Any> JavaPlugin.registerListener(direction: PacketDirection, crossinline callback: (PacketEvent, T) -> Unit) {
    getGlobalInterceptor().registerListener(this, direction, callback)
}

inline fun <reified T: Any> JavaPlugin.registerServerbound(crossinline callback: (PacketEvent, T) -> Unit) {
    getGlobalInterceptor().registerListener(this, PacketDirection.SERVERBOUND, callback)
}

inline fun <reified T: Any> JavaPlugin.registerClientbound(crossinline callback: (PacketEvent, T) -> Unit) {
    getGlobalInterceptor().registerListener(this, PacketDirection.CLIENTBOUND, callback)
}