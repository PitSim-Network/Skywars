package net.pitsim.skywars.game;

import de.tr7zw.nbtapi.NBTItem;
import net.pitsim.skywars.PitSim;
import net.pitsim.skywars.enums.NBTTag;
import net.pitsim.skywars.events.AttackEvent;
import net.pitsim.skywars.inventories.SpectatorGUI;
import net.pitsim.skywars.misc.Misc;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.inventory.InventoryInteractEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;

public class SpectatorManager implements Listener {

	public static List<Player> spectators = new ArrayList<>();

	static {
		new BukkitRunnable() {
			@Override
			public void run() {
				for(Player spectator : spectators) {
					Misc.sendActionBar(spectator, "&aYou are currently spectating the game.");
				}
			}
		}.runTaskTimer(PitSim.INSTANCE, 60L, 40L);
	}

	public static void setSpectator(Player player) {
		spectators.add(player);
		player.setAllowFlight(true);
		giveCompass(player);
		giveRequeue(player);
		giveLobby(player);
		for(Player onlinePlayer : Bukkit.getOnlinePlayers()) {
			onlinePlayer.hidePlayer(player);
		}

		new BukkitRunnable() {
			@Override
			public void run() {
				player.setFlying(true);
			}
		}.runTaskLater(PitSim.INSTANCE, 2L);
	}

	public static void giveCompass(Player player) {
		ItemStack compass = new ItemStack(Material.COMPASS);
		ItemMeta compassMeta = compass.getItemMeta();
		compassMeta.setDisplayName(ChatColor.GREEN + "" + ChatColor.BOLD +  "Teleport to a Player " + ChatColor.GRAY + "(Right-Click)");
		compass.setItemMeta(compassMeta);

		NBTItem nbtItem = new NBTItem(compass);
		nbtItem.setBoolean(NBTTag.IS_COMPASS.getRef(), true);
		player.getInventory().setItem(0, nbtItem.getItem());
	}

	public static void giveRequeue(Player player) {
		ItemStack paper = new ItemStack(Material.PAPER);
		ItemMeta paperMeta = paper.getItemMeta();
		paperMeta.setDisplayName(ChatColor.GOLD + "" + ChatColor.BOLD  + "Play Again " + ChatColor.GRAY + "(Right-Click)");
		paper.setItemMeta(paperMeta);

		NBTItem nbtItem = new NBTItem(paper);
		nbtItem.setBoolean(NBTTag.IS_PLAY_AGAIN.getRef(), true);
		player.getInventory().setItem(4, nbtItem.getItem());
	}

	public static void giveLobby(Player player) {
		ItemStack bed = new ItemStack(Material.BED);
		ItemMeta bedMeta = bed.getItemMeta();
		bedMeta.setDisplayName(ChatColor.RED + "" + ChatColor.BOLD  + "Back to Lobby " + ChatColor.GRAY + "(Right-Click)");
		bed.setItemMeta(bedMeta);

		NBTItem nbtItem = new NBTItem(bed);
		nbtItem.setBoolean(NBTTag.IS_TO_LOBBY.getRef(), true);
		player.getInventory().setItem(8, nbtItem.getItem());
	}


	@EventHandler(priority = EventPriority.HIGH)
	public void onCompassInteract(PlayerInteractEvent event) {
		Player player = event.getPlayer();
		if(event.getAction() != Action.RIGHT_CLICK_AIR && event.getAction() != Action.RIGHT_CLICK_BLOCK) return;

		if(Misc.isAirOrNull(player.getItemInHand())) return;

		NBTItem nbtItem = new NBTItem(player.getItemInHand());
		if(!nbtItem.hasKey(NBTTag.IS_COMPASS.getRef())) return;

		SpectatorGUI spectatorGUI = new SpectatorGUI(player);
		spectatorGUI.open();
		event.setCancelled(true);
	}

	@EventHandler(priority = EventPriority.HIGH)
	public void onPaperInteract(PlayerInteractEvent event) {
		Player player = event.getPlayer();
		if(event.getAction() != Action.RIGHT_CLICK_AIR && event.getAction() != Action.RIGHT_CLICK_BLOCK) return;

		if(Misc.isAirOrNull(player.getItemInHand())) return;

		NBTItem nbtItem = new NBTItem(player.getItemInHand());
		if(!nbtItem.hasKey(NBTTag.IS_PLAY_AGAIN.getRef())) return;

		PluginMessageSender.sendQueue(player);
	}

	@EventHandler(priority = EventPriority.HIGH)
	public void onBedInteract(PlayerInteractEvent event) {
		Player player = event.getPlayer();
		if(event.getAction() != Action.RIGHT_CLICK_AIR && event.getAction() != Action.RIGHT_CLICK_BLOCK) return;

		if(Misc.isAirOrNull(player.getItemInHand())) return;

		NBTItem nbtItem = new NBTItem(player.getItemInHand());
		if(!nbtItem.hasKey(NBTTag.IS_TO_LOBBY.getRef())) return;

		PluginMessageSender.sendToLobby(player);
	}

	@EventHandler
	public void onHit(AttackEvent.Pre event) {
		if(!spectators.contains(event.attacker) && !spectators.contains(event.defender)) return;
		event.setCancelled(true);
	}

	@EventHandler
	public void onInventoryInteract(InventoryInteractEvent event) {
		if(!(event.getWhoClicked() instanceof Player)) return;
		if(!spectators.contains((Player) event.getWhoClicked())) return;
		event.setCancelled(true);
	}

	@EventHandler
	public void onClick(InventoryInteractEvent event) {
		if(!spectators.contains((Player) event.getWhoClicked())) return;
		event.setCancelled(true);
	}

	@EventHandler
	public void onPlace(BlockPlaceEvent event) {
		if(!spectators.contains(event.getPlayer())) return;
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

}
