package net.seichi915.seichi915elevator.configuration

import net.seichi915.seichi915elevator.Seichi915Elevator

object Configuration {
  def saveDefaultConfig(): Unit = Seichi915Elevator.instance.saveDefaultConfig()

  def isCoolDownEnabled: Boolean =
    Seichi915Elevator.instance.getConfig.getBoolean("CoolDown.Enabled")

  def getCoolDownTime: Int =
    Seichi915Elevator.instance.getConfig.getInt("CoolDown.Time")
}
