package net.cafestube.wiretapper.constructors

import io.netty.buffer.Unpooled
import net.minecraft.network.FriendlyByteBuf
import net.minecraft.network.protocol.game.ClientboundAnimatePacket

@Deprecated(message = "Avoid collisions with actual types. Use NewClientboundAnimatePacket instead.")
fun ClientboundAnimatePacket(entityId: Int, action: Int) : ClientboundAnimatePacket {
    return NewClientboundAnimatePacket(entityId, action)
}

fun NewClientboundAnimatePacket(entityId: Int, action: Int) : ClientboundAnimatePacket {
    val buf = FriendlyByteBuf(Unpooled.buffer())
    buf.writeVarInt(entityId)
    buf.writeByte(action)
    val packet = ClientboundAnimatePacket.STREAM_CODEC.decode(buf)
    buf.release()
    return packet
}