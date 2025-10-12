package net.cafestube.wiretapper.constructors

import io.netty.buffer.Unpooled
import net.minecraft.network.FriendlyByteBuf
import net.minecraft.network.protocol.game.ClientboundAnimatePacket

fun ClientboundAnimatePacket(entityId: Int, action: Int) : ClientboundAnimatePacket {
    val buf = FriendlyByteBuf(Unpooled.buffer())
    buf.writeVarInt(entityId)
    buf.writeByte(action)
    val packet = ClientboundAnimatePacket.STREAM_CODEC.decode(buf)
    buf.release()
    return packet
}