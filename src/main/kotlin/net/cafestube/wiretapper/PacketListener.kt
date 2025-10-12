package net.cafestube.wiretapper

/**
 * Base interface for packet listeners.
 * API consumers should implement either IncomingPacketListener, OutgoingPacketListener, or both.
 */
sealed interface PacketListenerBase

fun interface IncomingPacketListener : PacketListenerBase {

    /**
     * Called when a packet is received from the client.
     *
     * @param packetEvent The event containing the packet and context information
     * @see PacketEvent
     */
    fun handleIncoming(packetEvent: PacketEvent)

}

fun interface OutgoingPacketListener : PacketListenerBase {

    /**
     * Called when a packet is sent from the server.
     *
     * @param packetEvent The event containing the packet and context information
     * @see PacketEvent
     */
    fun handleOutgoing(packetEvent: PacketEvent)

}