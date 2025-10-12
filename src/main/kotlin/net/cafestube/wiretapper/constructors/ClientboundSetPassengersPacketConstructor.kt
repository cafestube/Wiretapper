package net.cafestube.wiretapper.constructors

import io.netty.buffer.Unpooled
import net.minecraft.network.FriendlyByteBuf
import net.minecraft.network.protocol.game.ClientboundSetPassengersPacket

fun ClientboundSetPassengersPacket(entityId: Int, passengerIds: IntArray) : ClientboundSetPassengersPacket {
    val buf = FriendlyByteBuf(Unpooled.buffer())
    buf.writeVarInt(entityId)
    buf.writeVarIntArray(passengerIds)
    val packet = ClientboundSetPassengersPacket.STREAM_CODEC.decode(buf)
    buf.release()
    return packet
}