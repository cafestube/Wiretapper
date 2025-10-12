package net.cafestube.wiretapper

import org.bukkit.entity.Player

class PacketEvent(
    val player: Player,
    var packet: Any
) {
    val packetName: String
        get() = packet::class.java.simpleName

    var cancelled: Boolean = false

    inline fun <reified T> getPacket(): T {
        return if (packet is T) {
            packet as T
        } else {
            throw IllegalArgumentException("Packet is not of type ${T::class.java.simpleName}")
        }
    }

    inline fun <reified T> packetIs(): Boolean {
        return packet is T
    }

}