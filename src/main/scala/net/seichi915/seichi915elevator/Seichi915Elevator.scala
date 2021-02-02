package net.seichi915.seichi915elevator

import net.seichi915.seichi915elevator.configuration.Configuration
import net.seichi915.seichi915elevator.listener._
import net.seichi915.seichi915elevator.task._
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.plugin.java.JavaPlugin
import org.bukkit.scheduler.BukkitRunnable

import scala.collection.mutable

object Seichi915Elevator {
  var instance: Seichi915Elevator = _

  var coolDownMap: mutable.HashMap[Player, Int] = mutable.HashMap()
}

class Seichi915Elevator extends JavaPlugin {
  Seichi915Elevator.instance = this

  override def onEnable(): Unit = {
    Configuration.saveDefaultConfig()
    Seq(
      new PlayerMoveListener,
      new PlayerQuitListener,
      new PlayerToggleSneakListener
    ).foreach(Bukkit.getPluginManager.registerEvents(_, this))
    Map(
      (1, 1) -> new CoolDownTimerTask
    ).foreach {
      case ((delay: Int, period: Int), bukkitRunnable: BukkitRunnable) =>
        bukkitRunnable.runTaskTimer(this, delay, period)
    }

    getLogger.info("seichi915Elevatorが有効になりました。")
  }

  override def onDisable(): Unit = {
    getLogger.info("seichi915Elevatorが無効になりました。")
  }
}
