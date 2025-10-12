package net.cafestube.wiretapper

fun interface PacketEventCallback<T> {

    fun handle(event: PacketEvent, packet: T)

}
