package net.pitsim.skywars.game;

import dev.kyro.arcticapi.misc.AOutput;
import me.clip.placeholderapi.PlaceholderAPI;
import net.pitsim.skywars.events.KillEvent;
import net.pitsim.skywars.misc.Misc;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.HashMap;
import java.util.Map;

public class KillManager implements Listener {

	public static Map<Player, Integer> kills = new HashMap<>();

	@EventHandler
	public void onKill(KillEvent event) {
		Player killer = event.killer;
		Player dead = event.dead;

		Bukkit.getWorld("game").strikeLightningEffect(dead.getLocation());
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


}
