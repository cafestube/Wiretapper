package net.cafestube.wiretapper.constructors

import io.netty.buffer.Unpooled
import net.minecraft.network.FriendlyByteBuf
import net.minecraft.network.protocol.game.ClientboundRotateHeadPacket
import net.minecraft.network.protocol.game.ClientboundSetCameraPacket

fun NewClientboundSetCameraPacket(entityId: Int): ClientboundSetCameraPacket {
    val buf = FriendlyByteBuf(Unpooled.buffer())
    buf.writeVarInt(entityId)
    val packet = ClientboundSetCameraPacket.STREAM_CODEC.decode(buf)
    buf.release()
    return packet
}