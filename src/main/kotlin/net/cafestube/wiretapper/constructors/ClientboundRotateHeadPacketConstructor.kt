package net.cafestube.wiretapper.constructors

import io.netty.buffer.Unpooled
import net.minecraft.network.FriendlyByteBuf
import net.minecraft.network.protocol.game.ClientboundRotateHeadPacket

@Deprecated(message = "Avoid collisions with actual types. Use NewClientboundRotateHeadPacket instead.")
fun ClientboundRotateHeadPacket(entityId: Int, yaw: Byte): ClientboundRotateHeadPacket {
    return newClientboundRotateHeadPacket(entityId, yaw)
}

fun newClientboundRotateHeadPacket(entityId: Int, yaw: Byte): ClientboundRotateHeadPacket {
    val buf = FriendlyByteBuf(Unpooled.buffer())
    buf.writeVarInt(entityId)
    buf.writeByte(yaw.toInt())
    val packet = ClientboundRotateHeadPacket.STREAM_CODEC.decode(buf)
    buf.release()
    return packet
}