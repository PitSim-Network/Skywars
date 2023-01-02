package net.pitsim.skywars.controllers;

import be.maximvdw.featherboard.api.FeatherBoardAPI;
import de.tr7zw.nbtapi.NBTItem;
import dev.kyro.arcticapi.misc.AOutput;
import me.clip.placeholderapi.PlaceholderAPI;
import net.pitsim.skywars.PitSim;
import net.pitsim.skywars.controllers.objects.PitPlayer;
import net.pitsim.skywars.enums.NBTTag;
import net.pitsim.skywars.game.GameManager;
import net.pitsim.skywars.game.GameStatus;
import net.pitsim.skywars.game.SpectatorManager;
import net.pitsim.skywars.misc.Misc;
import net.pitsim.skywars.misc.Sounds;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.*;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

//import net.kyori.adventure.audience.Audience;

public class PlayerManager implements Listener {
	public static List<UUID> pantsSwapCooldown = new ArrayList<>();

	static {
		new BukkitRunnable() {
			@Override
			public void run() {
				for(Player onlinePlayer : Bukkit.getOnlinePlayers())
					((CraftPlayer) onlinePlayer).getHandle().getDataWatcher().watch(9, (byte) 0);
			}
		}.runTaskTimer(PitSim.INSTANCE, 0L, 20L);
	}

	@EventHandler
	public void onJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		player.setFoodLevel(19);
	}

	@EventHandler
	public void onFoodLevelChange(FoodLevelChangeEvent event) {
		event.setCancelled(true);
	}

	@EventHandler
	public static void onClick(PlayerInteractEvent event) {
		Player player = event.getPlayer();

		if(event.getAction() != Action.RIGHT_CLICK_AIR && event.getAction() != Action.RIGHT_CLICK_BLOCK) return;
		if(Misc.isAirOrNull(player.getItemInHand())) return;

		int firstArrow = -1;
		boolean multipleStacks = false;
		boolean hasSpace = false;
		if(player.getItemInHand().getType() == Material.BOW) {

			NBTItem nbtItem = new NBTItem(player.getItemInHand());
			if(nbtItem.hasKey(NBTTag.ITEM_UUID.getRef()) && !player.getItemInHand().getItemMeta().hasEnchant(Enchantment.WATER_WORKER)) {
				ItemStack modified = player.getItemInHand();
				modified.addUnsafeEnchantment(Enchantment.WATER_WORKER, 1);
				ItemMeta itemMeta = modified.getItemMeta();
				itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
				modified.setItemMeta(itemMeta);
				player.setItemInHand(modified);
			}

			for(int i = 0; i < 36; i++) {
				ItemStack itemStack = player.getInventory().getItem(i);
				if(Misc.isAirOrNull(itemStack)) {
					hasSpace = true;
					continue;
				}
				if(itemStack.getType() != Material.ARROW) continue;
				if(firstArrow == -1) firstArrow = i;
				else {
					multipleStacks = true;
					break;
				}
			}
			if(!multipleStacks) {
				if(firstArrow == -1) {
					if(hasSpace) {
						player.getInventory().addItem(new ItemStack(Material.ARROW, 32));
					} else {
						AOutput.error(player, "Please make room in your inventory for arrows");
					}
				} else {
					player.getInventory().setItem(firstArrow, new ItemStack(Material.ARROW, 32));
				}
			}
		}

		if(player.getItemInHand().getType().toString().contains("LEGGINGS")) {
			if(Misc.isAirOrNull(player.getInventory().getLeggings())) return;

			if(pantsSwapCooldown.contains(player.getUniqueId())) {

				Sounds.NO.play(player);
				return;
			}

			ItemStack held = player.getItemInHand();
			player.setItemInHand(player.getInventory().getLeggings());
			player.getInventory().setLeggings(held);

			pantsSwapCooldown.add(player.getUniqueId());
			new BukkitRunnable() {
				@Override
				public void run() {
					pantsSwapCooldown.remove(player.getUniqueId());
				}
			}.runTaskLater(PitSim.INSTANCE, 40L);
			Sounds.ARMOR_SWAP.play(player);
		}
	}

	@EventHandler
	public void onRespawn(PlayerRespawnEvent event) {
		new BukkitRunnable() {
			@Override
			public void run() {
				event.getPlayer().teleport(Bukkit.getWorld("game").getSpawnLocation());
			}
		}.runTaskLater(PitSim.INSTANCE, 10L);

	}

	@EventHandler
	public void onDamage(EntityDamageEvent event) {
		if(!(event.getEntity() instanceof Player)) return;
		if(event.getCause() == EntityDamageEvent.DamageCause.SUFFOCATION && SpectatorManager.spectators.contains((Player) event.getEntity())) {
			event.setCancelled(true);
		}
	}

	@EventHandler
	public void onItemDamage(PlayerItemDamageEvent event) {
		event.setCancelled(true);
	}

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		PitPlayer pitPlayer = PitPlayer.getPitPlayer(player);

		player.setSaturation(Float.MAX_VALUE);

		FeatherBoardAPI.resetDefaultScoreboard(event.getPlayer());
		FeatherBoardAPI.showScoreboard(event.getPlayer(), "queue");
	}

	@EventHandler
	public void onCraft(InventoryClickEvent event) {
		if(event.getSlot() == 80 || event.getSlot() == 81 || event.getSlot() == 82 || event.getSlot() == 83)
			event.setCancelled(true);
	}

	@EventHandler
	public void onJoin(AsyncPlayerPreLoginEvent event) {
		Player player = Bukkit.getServer().getPlayerExact(event.getName());
		if(player == null) return;
		if(player.isOnline()) {
			event.disallow(AsyncPlayerPreLoginEvent.Result.KICK_OTHER, ChatColor.RED + "You are already online! \nIf you believe this is an error, try re-logging in a few seconds.");
		}
	}

	@EventHandler
	public void onQuit(PlayerQuitEvent event) {
		Player player = event.getPlayer();
		PitPlayer pitPlayer = PitPlayer.getPitPlayer(player);
		pitPlayer.saveSQLData();
		if(!(GameManager.status == GameStatus.ACTIVE)) return;
		if(!GameManager.alivePlayers.contains(player)) return;

		String playerName = "%luckperms_prefix%" + player.getDisplayName();
		if(pitPlayer.lastHitUUID == null)
			AOutput.broadcast(PlaceholderAPI.setPlaceholders(player, playerName + " &edisconnected."));
		DamageManager.death(player);
	}

	public static List<Player> toggledPlayers = new ArrayList<>();

	@EventHandler
	public void onBreak(BlockBreakEvent event) {
		if(event.getBlock().getType() == Material.CHEST) event.setCancelled(true);
		if(GameManager.status == GameStatus.QUEUE || SpectatorManager.spectators.contains(event.getPlayer())) {
			event.setCancelled(true);
		}
	}

	@EventHandler
	public void onBreak(BlockPlaceEvent event) {
		if(GameManager.status == GameStatus.QUEUE || SpectatorManager.spectators.contains(event.getPlayer())) {
			event.setCancelled(true);
		}
	}
}
