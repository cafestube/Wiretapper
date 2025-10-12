package net.cafestube.wiretapper.plugin

import net.cafestube.wiretapper.PacketInterceptor
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.server.PluginDisableEvent
import org.bukkit.plugin.java.JavaPlugin

private var pluginInstance: WiretapperPlugin? = null

fun getGlobalInterceptor(): PacketInterceptor {
    checkNotNull(pluginInstance) { "WiretapperPlugin is not initialized yet or not installed. If you use the library standalone, please create your own interceptor." }
    return pluginInstance!!.interceptor
}

internal class WiretapperPlugin : JavaPlugin(), Listener {

    lateinit var interceptor: PacketInterceptor

    override fun onEnable() {
        interceptor = PacketInterceptor(this)
        pluginInstance = this
        server.pluginManager.registerEvents(this, this)
    }

    override fun onDisable() {
        interceptor.destroy()
    }

    @EventHandler
    fun onUnload(plugin: PluginDisableEvent) {
        interceptor.unregisterListeners(plugin.plugin)
    }
}