package net.cafestube.wiretapper.plugin

import net.cafestube.wiretapper.PacketDirection
import net.cafestube.wiretapper.PacketEvent
import net.cafestube.wiretapper.PacketListenerBase
import net.cafestube.wiretapper.getGlobalPacketInterceptor
import org.bukkit.plugin.java.JavaPlugin

fun JavaPlugin.registerPacketListener(listener: PacketListenerBase) {
    getGlobalPacketInterceptor().registerListener(this, listener)
}

inline fun <reified T: Any> JavaPlugin.registerListener(direction: PacketDirection, crossinline callback: (PacketEvent, T) -> Unit) {
    getGlobalPacketInterceptor().registerListener(this, direction, callback)
}

inline fun <reified T: Any> JavaPlugin.registerServerbound(crossinline callback: (PacketEvent, T) -> Unit) {
    getGlobalPacketInterceptor().registerListener(this, PacketDirection.SERVERBOUND, callback)
}

inline fun <reified T: Any> JavaPlugin.registerClientbound(crossinline callback: (PacketEvent, T) -> Unit) {
    getGlobalPacketInterceptor().registerListener(this, PacketDirection.CLIENTBOUND, callback)
}