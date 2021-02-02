package net.seichi915.seichi915elevator.task

import net.seichi915.seichi915elevator.Seichi915Elevator
import org.bukkit.entity.Player
import org.bukkit.scheduler.BukkitRunnable

class CoolDownTimerTask extends BukkitRunnable {
  override def run(): Unit =
    Seichi915Elevator.coolDownMap.foreach {
      case (player: Player, remaining: Int) =>
        if (remaining <= 1) Seichi915Elevator.coolDownMap.remove(player)
        else Seichi915Elevator.coolDownMap.update(player, remaining - 1)
    }
}
