package net.pitsim.skywars.game;

import net.pitsim.skywars.PitSim;
import net.pitsim.skywars.controllers.DamageManager;
import net.pitsim.skywars.misc.Misc;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.Map;

public class FeatherManager implements Listener {

    private static Map<Player, Location> lastLocation = new HashMap<>();

    static {
        new BukkitRunnable() {
            @Override
            public void run() {
                for (Player player : Bukkit.getOnlinePlayers()) {
                    Block standingBlock = MapManager.getWorld().getBlockAt(player.getLocation().subtract(0, 1, 0));
                    if(standingBlock.getType() == Material.AIR) return;

                    lastLocation.put(player, standingBlock.getLocation().add(0, 1, 0));
                }
            }
        }.runTaskTimer(PitSim.INSTANCE, 60L, 10L);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();

        if(player.getLocation().getY() < 20) {
            boolean feather = FunkyFeather.useFeather(player, false);

            if(!feather) {
                DamageManager.death(player);
                return;
            }
            if(!lastLocation.containsKey(player)) {
                DamageManager.death(player);
                return;
            }
            player.teleport(lastLocation.get(player));
        }
    }
}
