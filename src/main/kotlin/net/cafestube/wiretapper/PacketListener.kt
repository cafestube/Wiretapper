package net.cafestube.wiretapper

interface PacketListener {

    fun handleIncoming(packetEvent: PacketEvent) {
        // Default empty implementation
    }

    fun handleOutgoing(packetEvent: PacketEvent) {
        // Default empty implementation
    }

}