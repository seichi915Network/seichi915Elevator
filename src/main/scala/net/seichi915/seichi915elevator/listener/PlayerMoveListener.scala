package net.seichi915.seichi915elevator.listener

import net.seichi915.seichi915elevator.util.Implicits._
import org.bukkit.{Location, Material}
import org.bukkit.event.player.PlayerMoveEvent
import org.bukkit.event.{EventHandler, Listener}

class PlayerMoveListener extends Listener {
  @EventHandler
  def onPlayerMove(event: PlayerMoveEvent): Unit = {
    if (!(event.getFrom.getY < event.getTo.getY)) return
    if (!event.getPlayer.hasPermission("seichi915elevator.use")) return
    if (event.getFrom.getWorld
          .getBlockAt(event.getFrom)
          .isNull || !event.getFrom.getWorld
          .getBlockAt(event.getFrom)
          .getType
          .equals(Material.HEAVY_WEIGHTED_PRESSURE_PLATE)) return
    if (event.getFrom.getWorld
          .getBlockAt(event.getFrom.clone().add(0, -1, 0))
          .isNull || (!event.getFrom.getWorld
          .getBlockAt(event.getFrom.clone().add(0, -1, 0))
          .getType
          .equals(Material.IRON_BLOCK) && !event.getFrom.getWorld
          .getBlockAt(event.getFrom.clone().add(0, -1, 0))
          .getType
          .equals(Material.GLASS)))
      return
    for (i <- 3 to 23) {
      val y = event.getFrom.clone().add(0, i, 0).getY
      if (y >= 256) return
      val plateLocation = new Location(event.getFrom.getWorld,
                                       event.getFrom.getBlockX,
                                       y,
                                       event.getFrom.getBlockZ)
      val plate =
        event.getPlayer.getLocation.getWorld.getBlockAt(plateLocation)
      if (plate.nonNull && plate.getType.equals(
            Material.HEAVY_WEIGHTED_PRESSURE_PLATE)) {
        val block = event.getFrom.getWorld
          .getBlockAt(plate.getLocation.add(0, -1, 0))
        if (block.nonNull && (block.getType.equals(Material.IRON_BLOCK) || block.getType
              .equals(Material.GLASS))) {
          if (event.getFrom.getWorld
                .getBlockAt(plate.getLocation
                  .add(0, 1, 0))
                .isNull || event.getFrom.getWorld
                .getBlockAt(plate.getLocation.add(0, 1, 0))
                .getType
                .equals(Material.AIR)) {
            event.getPlayer.teleport(
              new Location(
                event.getPlayer.getLocation.getWorld,
                event.getPlayer.getLocation.getX,
                plate.getLocation.getY,
                event.getPlayer.getLocation.getZ,
                event.getPlayer.getLocation.getYaw,
                event.getPlayer.getLocation.getPitch
              ))
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
