package net.pitsim.skywars.game;

import net.pitsim.skywars.PitSim;
import net.pitsim.skywars.controllers.DamageManager;
import net.pitsim.skywars.events.AttackEvent;
import net.pitsim.skywars.events.KillEvent;
import net.pitsim.skywars.misc.Misc;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryInteractEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerPickupArrowEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;

public class SpectatorManager implements Listener {

	public static List<Player> spectators = new ArrayList<>();

	static {
		new BukkitRunnable() {
			@Override
			public void run() {
				for (Player spectator : spectators) {
					Misc.sendActionBar(spectator, "&aYou are currently spectating the game.");
				}
			}
		}.runTaskTimer(PitSim.INSTANCE, 60L, 40L);
	}

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

	@EventHandler
	public void onInteract(PlayerInteractEvent event) {
		if(!spectators.contains(event.getPlayer())) return;
		event.setCancelled(true);
	}

	@EventHandler
	public void onShoot(EntityShootBowEvent event) {
		if(!(event.getEntity() instanceof Player)) return;
		Player player = (Player) event.getEntity();
		if(!spectators.contains(player)) return;
		event.setCancelled(true);
	}

	@EventHandler
	public void onPickup(PlayerPickupItemEvent event) {
		if(!spectators.contains(event.getPlayer())) return;
		event.setCancelled(true);
	}

	@EventHandler
	public void onArrowPickup(PlayerPickupArrowEvent event) {
		if(!spectators.contains(event.getPlayer())) return;
		event.setCancelled(true);
	}


}
