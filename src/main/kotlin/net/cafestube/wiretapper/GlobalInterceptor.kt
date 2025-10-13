package net.cafestube.wiretapper

import java.util.ServiceLoader


private val globalInterceptor by lazy { ServiceLoader.load(GlobalInterceptor::class.java) }

fun getGlobalPacketInterceptor(): PacketInterceptor {
    return globalInterceptor.firstOrNull()?.getPacketInterceptor()
        ?: error("No GlobalInterceptor found. Make sure you have the service file in META-INF/services or have the Wiretapper plugin installed.")
}

interface GlobalInterceptor {

    fun getPacketInterceptor(): PacketInterceptor

}