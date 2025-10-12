package net.cafestube.wiretapper

/**
 * Indicates the direction of a packet in relation to the server.
 * - CLIENTBOUND: Packet is sent from the server to the client.
 * - SERVERBOUND: Packet is sent from the client to the server.
 */
enum class PacketDirection {
    CLIENTBOUND,
    SERVERBOUND
}