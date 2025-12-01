package net.cafestube.wiretapper.constructors

import io.netty.buffer.Unpooled
import net.minecraft.network.FriendlyByteBuf
import net.minecraft.network.protocol.game.ClientboundSetPassengersPacket

@Deprecated(message = "Avoid collisions with actual types. Use NewClientboundSetPassengersPacket instead.")
fun ClientboundSetPassengersPacket(entityId: Int, passengerIds: IntArray) : ClientboundSetPassengersPacket {
    val buf = FriendlyByteBuf(Unpooled.buffer())
    buf.writeVarInt(entityId)
    buf.writeVarIntArray(passengerIds)
    val packet = ClientboundSetPassengersPacket.STREAM_CODEC.decode(buf)
    buf.release()
    return packet
}

fun NewClientboundSetPassengersPacket(entityId: Int, passengerIds: IntArray) : ClientboundSetPassengersPacket {
    val buf = FriendlyByteBuf(Unpooled.buffer())
    buf.writeVarInt(entityId)
    buf.writeVarIntArray(passengerIds)
    val packet = ClientboundSetPassengersPacket.STREAM_CODEC.decode(buf)
    buf.release()
    return packet
}