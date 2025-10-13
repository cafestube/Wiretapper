package net.cafestube.wiretapper.plugin

import net.cafestube.wiretapper.PacketInterceptor
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.server.PluginDisableEvent
import org.bukkit.plugin.java.JavaPlugin

internal var pluginInstance: WiretapperPlugin? = null

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