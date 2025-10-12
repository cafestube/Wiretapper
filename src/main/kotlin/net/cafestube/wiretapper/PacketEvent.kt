package net.cafestube.wiretapper

import io.papermc.paper.connection.PlayerConnection
import io.papermc.paper.connection.PlayerGameConnection
import org.bukkit.entity.Player
import org.bukkit.event.Cancellable

/**
 * Represents a network packet with additional state like its destination/source.
 *
 * @param connection The player connection associated with the packet (can be Login, Game or Configuration connection)
 * @param packet The actual packet object
 * @param direction The direction of the packet (to or from the server)
 */
class PacketEvent(
    val connection: PlayerConnection,
    var packet: Any,
    val direction: PacketDirection
) : Cancellable {

    /**
     * Convenience property to get the player associated with the connection.
     *
     * @throws IllegalStateException if the connection is not a PlayerGameConnection yet.
     */
    val player: Player
        get() = (connection as? PlayerGameConnection?)?.player ?: throw IllegalStateException("Connection is not a PlayerGameConnection")

    /**
     * Indicates if the connection is a PlayerGameConnection (i.e. the player has fully logged in) and the player property can be accessed.
     */
    val hasPlayer: Boolean
        get() = connection is PlayerGameConnection

    val packetName: String
        get() = packet::class.java.simpleName

    private var cancelled: Boolean = false

    override fun setCancelled(cancel: Boolean) {
        this.cancelled = cancel
    }

    override fun isCancelled(): Boolean {
        return cancelled
    }

    /**
     * Gets the packet cast to the specified type [T].
     *
     * @throws IllegalArgumentException if the packet is not of type [T].
     */
    inline fun <reified T> getPacketAs(): T {
        return if (packet is T) {
            packet as T
        } else {
            throw IllegalArgumentException("Packet is not of type ${T::class.java.simpleName}")
        }
    }

}