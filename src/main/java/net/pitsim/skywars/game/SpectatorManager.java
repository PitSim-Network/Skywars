package net.pitsim.skywars.game;

import net.pitsim.skywars.events.AttackEvent;
import net.pitsim.skywars.events.KillEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryInteractEvent;
import org.bukkit.event.player.PlayerDropItemEvent;

import java.util.ArrayList;
import java.util.List;

public class SpectatorManager implements Listener {

	public static List<Player> spectators = new ArrayList<>();

	public static void setSpectator(Player player) {
		spectators.add(player);
		player.setAllowFlight(true);
		player.setFlying(true);
		for(Player onlinePlayer : Bukkit.getOnlinePlayers()) {
			onlinePlayer.hidePlayer(player);
		}
	}

	@EventHandler
	public void onHit(AttackEvent.Pre event) {
		if(!spectators.contains(event.attacker) && !spectators.contains(event.defender)) return;
		event.setCancelled(true);
	}

	@EventHandler
	public void onClick(InventoryInteractEvent event) {
		if(!spectators.contains((Player) event.getWhoClicked())) return;
		event.setCancelled(true);
 	}

	@EventHandler
	public void onDrop(PlayerDropItemEvent event) {
		if(!spectators.contains(event.getPlayer())) return;
		event.setCancelled(true);
	}
}
