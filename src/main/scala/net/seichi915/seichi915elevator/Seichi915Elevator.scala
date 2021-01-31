package net.seichi915.seichi915elevator

import net.seichi915.seichi915elevator.listener._
import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin

object Seichi915Elevator {
  var instance: Seichi915Elevator = _
}

class Seichi915Elevator extends JavaPlugin {
  Seichi915Elevator.instance = this

  override def onEnable(): Unit = {
    Seq(
      new PlayerMoveListener,
      new PlayerToggleSneakListener
    ).foreach(Bukkit.getPluginManager.registerEvents(_, this))

    getLogger.info("seichi915Elevatorが有効になりました。")
  }

  override def onDisable(): Unit = {
    getLogger.info("seichi915Elevatorが無効になりました。")
  }
}
