package net.pitsim.skywars.game;

import dev.kyro.arcticapi.misc.AOutput;
import me.clip.placeholderapi.PlaceholderAPI;
import net.pitsim.skywars.events.DeathEvent;
import net.pitsim.skywars.events.KillEvent;
import net.pitsim.skywars.misc.Misc;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class KillManager implements Listener {

	public static Map<Player, Integer> kills = new HashMap<>();

	@EventHandler
	public void onKill(KillEvent event) {
		if(GameManager.status == GameStatus.QUEUE) {
			Location loc = MapManager.map.getSpawnLocations().get(QueueManager.playerCages.get(event.dead));
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
		for (ItemStack item : inv.getContents()) {
			if (item != null) {
				loc.getWorld().dropItemNaturally(loc, item.clone());
			}
		}
		inv.clear();
		dead.teleport(MapManager.queueSpawn);
		SpectatorManager.setSpectator(dead);
		Misc.sendTitle(dead, "&c&lYOU DIED!", 100);
		Misc.sendSubTitle(dead, "", 100);

		String killerName = "%luckperms_prefix%" + killer.getDisplayName();
		String deadName = "%luckperms_prefix%" + dead.getDisplayName();
		AOutput.broadcast(PlaceholderAPI.setPlaceholders(dead,deadName) + " &ewas killed by " + PlaceholderAPI.setPlaceholders(killer,killerName) + "&e.");
		GameManager.alivePlayers.remove(dead);
		if(kills.containsKey(killer)) {
			kills.put(killer, kills.get(killer) + 1);
		} else kills.put(killer, 1);

		if(GameManager.alivePlayers.size() <= 1) GameManager.endGame();

	}

	@EventHandler
	public void onDeath(DeathEvent event) {
		if(GameManager.status == GameStatus.QUEUE) {
			Location loc = MapManager.map.getSpawnLocations().get(QueueManager.playerCages.get(event.getPlayer()));
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
		for (ItemStack item : inv.getContents()) {
			if (item != null) {
				loc.getWorld().dropItemNaturally(loc, item.clone());
			}
		}
		inv.clear();
		dead.teleport(MapManager.queueSpawn);
		SpectatorManager.setSpectator(dead);
		Misc.sendTitle(dead, "&c&lYOU DIED!", 100);
		Misc.sendSubTitle(dead, "", 100);

		String deadName = "%luckperms_prefix%" + dead.getDisplayName();
		AOutput.broadcast(PlaceholderAPI.setPlaceholders(dead,deadName) + " &efell into the void.");
		GameManager.alivePlayers.remove(dead);

		if(GameManager.alivePlayers.size() <= 1) GameManager.endGame();

	}


}
