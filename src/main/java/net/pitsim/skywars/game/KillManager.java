package net.pitsim.skywars.game;

import dev.kyro.arcticapi.misc.AOutput;
import me.clip.placeholderapi.PlaceholderAPI;
import net.pitsim.skywars.controllers.DamageManager;
import net.pitsim.skywars.controllers.objects.PitPlayer;
import net.pitsim.skywars.events.DeathEvent;
import net.pitsim.skywars.events.KillEvent;
import net.pitsim.skywars.misc.Misc;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class KillManager implements Listener {

	public static Map<Player, Integer> kills = new HashMap<>();

	@EventHandler
	public void onKill(KillEvent event) {
		if(GameManager.status == GameStatus.QUEUE) {
			Location loc = MapManager.map.getSpawnLocations().get(QueueManager.playerCages.get(event.dead) - 1);
			event.dead.teleport(loc);
			return;
		} else if(GameManager.status == GameStatus.ENDING) {
			event.dead.teleport(new Location(MapManager.getWorld(), 0, 100, 0));
			return;
		}
		Player killer = event.killer;
		Player dead = event.dead;

		Bukkit.getWorld("game").strikeLightningEffect(dead.getLocation());
		Location loc = dead.getLocation().clone();
		Inventory inv = dead.getInventory();
		for(ItemStack item : inv.getContents()) {
			if(item != null) {
				loc.getWorld().dropItemNaturally(loc, item.clone());
			}
		}
		if(!Misc.isAirOrNull(dead.getInventory().getHelmet()))
			loc.getWorld().dropItemNaturally(loc, dead.getInventory().getHelmet().clone());
		if(!Misc.isAirOrNull(dead.getInventory().getChestplate()))
			loc.getWorld().dropItemNaturally(loc, dead.getInventory().getChestplate().clone());
		if(!Misc.isAirOrNull(dead.getInventory().getLeggings()))
			loc.getWorld().dropItemNaturally(loc, dead.getInventory().getLeggings().clone());
		if(!Misc.isAirOrNull(dead.getInventory().getBoots()))
			loc.getWorld().dropItemNaturally(loc, dead.getInventory().getBoots().clone());
		Misc.clearArmor(dead);
		inv.clear();
		dead.teleport(MapManager.queueSpawn);
		SpectatorManager.setSpectator(dead);
		Misc.sendTitle(dead, "&c&lYOU DIED!", 100);
		Misc.sendSubTitle(dead, "", 100);

		String killerName = "%luckperms_prefix%" + killer.getDisplayName();
		String deadName = "%luckperms_prefix%" + dead.getDisplayName();
		PitPlayer pitKiller = PitPlayer.getPitPlayer(killer);
		pitKiller.stats.kills++;
		AOutput.broadcast(PlaceholderAPI.setPlaceholders(dead, deadName) + " &ewas killed by &r" + PlaceholderAPI.setPlaceholders(killer, killerName) + "&e.");
		GameManager.alivePlayers.remove(dead);
		if(kills.containsKey(killer)) {
			kills.put(killer, kills.get(killer) + 1);
		} else kills.put(killer, 1);

		if(GameManager.alivePlayers.size() <= 1) GameManager.endGame();

	}

	@EventHandler
	public void onDeath(DeathEvent event) {
		if(GameManager.status == GameStatus.QUEUE) {
			Location loc = MapManager.map.getSpawnLocations().get(QueueManager.playerCages.get(event.getPlayer()) - 1);
			event.getPlayer().teleport(loc);
			return;
		} else if(GameManager.status == GameStatus.ENDING) {
			event.getPlayer().teleport(new Location(MapManager.getWorld(), 0, 100, 0));
			return;
		}
		Player dead = event.getPlayer();

		Bukkit.getWorld("game").strikeLightningEffect(dead.getLocation());
		Location loc = dead.getLocation().clone();
		Inventory inv = dead.getInventory();
		for(ItemStack item : inv.getContents()) {
			if(item != null) {
				loc.getWorld().dropItemNaturally(loc, item.clone());
			}
		}
		if(!Misc.isAirOrNull(dead.getInventory().getHelmet()))
			loc.getWorld().dropItemNaturally(loc, dead.getInventory().getHelmet().clone());
		if(!Misc.isAirOrNull(dead.getInventory().getChestplate()))
			loc.getWorld().dropItemNaturally(loc, dead.getInventory().getChestplate().clone());
		if(!Misc.isAirOrNull(dead.getInventory().getLeggings()))
			loc.getWorld().dropItemNaturally(loc, dead.getInventory().getLeggings().clone());
		if(!Misc.isAirOrNull(dead.getInventory().getBoots()))
			loc.getWorld().dropItemNaturally(loc, dead.getInventory().getBoots().clone());
		Misc.clearArmor(dead);
		inv.clear();
		dead.teleport(MapManager.queueSpawn);
		SpectatorManager.setSpectator(dead);
		Misc.sendTitle(dead, "&c&lYOU DIED!", 100);
		Misc.sendSubTitle(dead, "", 100);
		GameManager.alivePlayers.remove(dead);

		if(GameManager.alivePlayers.size() <= 1) GameManager.endGame();

	}

	@EventHandler
	public void onMove(PlayerMoveEvent event) {
		if(GameManager.status != GameStatus.ACTIVE) return;
		Player player = event.getPlayer();
		if(SpectatorManager.spectators.contains(player)) return;
		Location location = event.getPlayer().getLocation();

		if(location.getX() > 125 || location.getX() < -125 || location.getZ() > 125 || location.getZ() < -125) {
			String playerName = "%luckperms_prefix%" + player.getDisplayName();
			PitPlayer pitPlayer = PitPlayer.getPitPlayer(player);
			if(pitPlayer.lastHitUUID == null)
				AOutput.broadcast(PlaceholderAPI.setPlaceholders(player, playerName + " &etried to leave the map."));
			DamageManager.death(player);
		}
	}


}
