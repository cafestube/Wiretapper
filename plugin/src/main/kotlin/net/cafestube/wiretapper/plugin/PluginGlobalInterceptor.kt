package net.cafestube.wiretapper.plugin

import net.cafestube.wiretapper.GlobalInterceptor
import net.cafestube.wiretapper.PacketInterceptor

class PluginGlobalInterceptor : GlobalInterceptor {
    override fun getPacketInterceptor(): PacketInterceptor {
        return pluginInstance?.interceptor ?: throw IllegalStateException("Plugin is not initialized")
    }
}