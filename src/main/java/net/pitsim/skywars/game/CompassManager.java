package net.pitsim.skywars.game;

import me.clip.placeholderapi.PlaceholderAPI;
import net.pitsim.skywars.PitSim;
import net.pitsim.skywars.misc.Misc;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CompassManager {

	public static Map<Player, Player> compassPlayers = new HashMap<>();

	public static void onGameStart() {
		new BukkitRunnable() {

			@Override
			public void run() {
				if(GameManager.alivePlayers.size() < 2) return;
				for(Player player : Bukkit.getOnlinePlayers()) {
					if(SpectatorManager.spectators.contains(player)) continue;
					List<Entity> entities = player.getNearbyEntities(500, 500, 500);

					Player nearestPlayer = null;
					double distance = 100000;

					for(Entity entity : entities) {
						if(!(entity instanceof Player)) continue;
						if(entity == player) continue;
						if(SpectatorManager.spectators.contains((Player) entity)) continue;

						if(entity.getLocation().distance(player.getLocation()) < distance) {
							distance = entity.getLocation().distance(player.getLocation());
							nearestPlayer = (Player) entity;
						}
						if(nearestPlayer != null) compassPlayers.put(player, nearestPlayer);
 					}
					if(compassPlayers.containsKey(player)) player.setCompassTarget(compassPlayers.get(player).getLocation());

					if(Misc.isAirOrNull(player.getItemInHand())) return;
					if(player.getItemInHand().getType() != Material.COMPASS) return;
					if(!compassPlayers.containsKey(player)) return;

					Misc.sendActionBar(player, PlaceholderAPI.setPlaceholders(compassPlayers.get(player),
							"%luckperms_prefix%" + compassPlayers.get(player).getDisplayName()) + " &7" +
							(int) player.getLocation().distance(compassPlayers.get(player).getLocation()) + "m");
				}
			}
		}.runTaskTimer(PitSim.INSTANCE, 0L, 20L);
	}


}
