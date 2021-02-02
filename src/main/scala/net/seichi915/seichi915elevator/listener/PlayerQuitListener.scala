package net.seichi915.seichi915elevator.listener

import net.seichi915.seichi915elevator.Seichi915Elevator
import org.bukkit.event.player.PlayerQuitEvent
import org.bukkit.event.{EventHandler, Listener}

class PlayerQuitListener extends Listener {
  @EventHandler
  def onPlayerQuit(event: PlayerQuitEvent): Unit =
    if (Seichi915Elevator.coolDownMap.contains(event.getPlayer))
      Seichi915Elevator.coolDownMap.remove(event.getPlayer)
}
