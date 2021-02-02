package net.seichi915.seichi915elevator.listener

import net.seichi915.seichi915elevator.Seichi915Elevator
import net.seichi915.seichi915elevator.configuration.Configuration
import net.seichi915.seichi915elevator.util.Implicits._
import org.bukkit.{Location, Material}
import org.bukkit.event.player.PlayerToggleSneakEvent
import org.bukkit.event.{EventHandler, Listener}

class PlayerToggleSneakListener extends Listener {
  @EventHandler
  def onPlayerToggleSneak(event: PlayerToggleSneakEvent): Unit = {
    if (!event.isSneaking) return
    if (!event.getPlayer.hasPermission("seichi915elevator.use")) return
    if (event.getPlayer.getLocation.getWorld
          .getBlockAt(event.getPlayer.getLocation)
          .isNull || !event.getPlayer.getLocation.getWorld
          .getBlockAt(event.getPlayer.getLocation)
          .getType
          .equals(Material.HEAVY_WEIGHTED_PRESSURE_PLATE)) return
    if (event.getPlayer.getLocation.getWorld
          .getBlockAt(event.getPlayer.getLocation
            .add(0, -1, 0))
          .isNull || (!event.getPlayer.getLocation.getWorld
          .getBlockAt(event.getPlayer.getLocation.add(0, -1, 0))
          .getType
          .equals(Material.IRON_BLOCK) && !event.getPlayer.getLocation.getWorld
          .getBlockAt(event.getPlayer.getLocation.add(0, -1, 0))
          .getType
          .equals(Material.GLASS))) return
    for (i <- 3 to 23) {
      val y = event.getPlayer.getLocation.add(0, -i, 0).getY
      if (y <= 0) return
      val plateLocation = new Location(event.getPlayer.getLocation.getWorld,
                                       event.getPlayer.getLocation.getBlockX,
                                       y,
                                       event.getPlayer.getLocation.getBlockZ)
      val plate =
        event.getPlayer.getLocation.getWorld.getBlockAt(plateLocation)
      if (plate.nonNull && plate.getType.equals(
            Material.HEAVY_WEIGHTED_PRESSURE_PLATE)) {
        val block = event.getPlayer.getLocation.getWorld
          .getBlockAt(plate.getLocation.add(0, -1, 0))
        if (block.nonNull && (block.getType.equals(Material.IRON_BLOCK) || block.getType
              .equals(Material.GLASS))) {
          if (event.getPlayer.getLocation.getWorld
                .getBlockAt(plate.getLocation
                  .add(0, 1, 0))
                .isNull || event.getPlayer.getLocation.getWorld
                .getBlockAt(plate.getLocation.add(0, 1, 0))
                .getType
                .equals(Material.AIR)) {
            if (Configuration.isCoolDownEnabled && Seichi915Elevator.coolDownMap
                  .contains(event.getPlayer)) {
              event.getPlayer.sendActionBar("クールダウン中です。")
              return
            }
            event.getPlayer.teleport(
              new Location(
                event.getPlayer.getLocation.getWorld,
                event.getPlayer.getLocation.getX,
                plate.getLocation.getY,
                event.getPlayer.getLocation.getZ,
                event.getPlayer.getLocation.getYaw,
                event.getPlayer.getLocation.getPitch
              ))
            event.getPlayer.setSneaking(false)
            Seichi915Elevator.coolDownMap += event.getPlayer -> Configuration.getCoolDownTime
            return
          } else {
            event.getPlayer.sendActionBar("移動先がブロックで埋まっているため移動できません。")
            return
          }
        }
      }
    }
  }
}
