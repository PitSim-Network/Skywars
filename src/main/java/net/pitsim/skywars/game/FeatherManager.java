package net.pitsim.skywars.game;

import de.myzelyam.api.vanish.VanishAPI;
import de.tr7zw.nbtapi.NBTItem;
import dev.kyro.arcticapi.misc.AOutput;
import me.clip.placeholderapi.PlaceholderAPI;
import net.pitsim.skywars.PitSim;
import net.pitsim.skywars.controllers.DamageManager;
import net.pitsim.skywars.controllers.objects.PitPlayer;
import net.pitsim.skywars.enums.NBTTag;
import net.pitsim.skywars.misc.Misc;
import net.pitsim.skywars.misc.Sounds;
import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;

public class FeatherManager implements Listener {
	public static Map<Player, List<Location>> previousLocationMap = new HashMap<>();
	public static List<Player> featherCooldownList = new ArrayList<>();

	static {
		new BukkitRunnable() {
			@Override
			public void run() {
				if(GameManager.status != GameStatus.ACTIVE) return;
				for(Player onlinePlayer : Bukkit.getOnlinePlayers()) {
					previousLocationMap.putIfAbsent(onlinePlayer, new ArrayList<>());
					previousLocationMap.get(onlinePlayer).add(0, onlinePlayer.getLocation());
				}
			}
		}.runTaskTimerAsynchronously(PitSim.INSTANCE, 0L, 1L);
	}

	public static Location rewindAndRemove(Player player) {
		if(!previousLocationMap.containsKey(player)) return player.getLocation();

		List<Location> previousLocations = previousLocationMap.get(player);
		Location location = null;
		System.out.println(previousLocations.size());
		for(int i = 0; i < 40; i++) {
			if(previousLocations.isEmpty()) break;
			location = previousLocations.remove(0);
		}
		return location;
	}

	@EventHandler
	public void onClick(PlayerInteractEvent event) {
		Player player = event.getPlayer();
		if(event.getAction() != Action.RIGHT_CLICK_BLOCK && event.getAction() != Action.RIGHT_CLICK_AIR) return;

		ItemStack itemStack = player.getItemInHand();
		if(Misc.isAirOrNull(itemStack)) return;
		NBTItem nbtItem = new NBTItem(itemStack);
		if(!nbtItem.hasKey(NBTTag.IS_FEATHER.getRef())) return;
		event.setCancelled(true);

		if(featherCooldownList.contains(player)) {
			Sounds.NO.play(player);
			return;
		}
		featherCooldownList.add(player);
		new BukkitRunnable() {
			@Override
			public void run() {
				featherCooldownList.remove(player);
			}
		}.runTaskLater(PitSim.INSTANCE, 5L);

		AOutput.send(player, "&3&lFUNKY FEATHER! &7Teleported back to a previous location.");
		if(itemStack.getAmount() > 1) itemStack.setAmount(itemStack.getAmount() - 1);
		else player.getInventory().setItem(player.getInventory().getHeldItemSlot(), null);

		Location location = rewindAndRemove(player);
		if(location == null) location = player.getLocation();

		Sounds.FUNKY_FEATHER_TELEPORT.play(location);
		player.getWorld().playEffect(location.clone().add(0, 1, 0), Effect.ENDER_SIGNAL, 1);
		player.teleport(location);
		Sounds.FUNKY_FEATHER.play(player);
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onMove(PlayerMoveEvent event) {
		Player player = event.getPlayer();

		if(player.getLocation().getY() < 20) {
			if(SpectatorManager.spectators.contains(player)) {
				player.teleport(new Location(MapManager.getWorld(), 0, 100, 0));
				player.setFlying(true);
				return;
			}

			if(VanishAPI.isInvisible(player)) {
				player.teleport(new Location(MapManager.getWorld(), 0, 100, 0));
				return;
			}

			if(GameManager.status == GameStatus.QUEUE) {
				player.teleport(MapManager.map.getSpawnLocations().get(QueueManager.playerCages.get(player)));
				return;
			}

//			TODO: Add a location manager please
//			if(GameManager.status == GameStatus.ENDING) {
//				player.teleport(lastLocation.get(player));
//				return;
//			}

			String deadName = "%luckperms_prefix%" + player.getDisplayName();
			PitPlayer pitPlayer = PitPlayer.getPitPlayer(player);
			if(pitPlayer.lastHitUUID == null)
				AOutput.broadcast(PlaceholderAPI.setPlaceholders(player, deadName) + " &efell into the void.");
			DamageManager.death(player);
//			return;
//			if(!lastLocation.containsKey(player)) {
//				DamageManager.death(player);
//				return;
//			}
//			player.teleport(lastLocation.get(player));
		}
	}
}
