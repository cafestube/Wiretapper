package net.cafestube.wiretapper.dsl

import net.minecraft.network.protocol.Packet
import org.bukkit.craftbukkit.entity.CraftPlayer
import org.bukkit.entity.Player

fun Player.sendPacket(packet: Packet<*>) = (this as CraftPlayer).handle.connection.send(packet)