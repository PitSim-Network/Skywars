package net.pitsim.skywars.game;

import de.myzelyam.api.vanish.PlayerHideEvent;
import de.myzelyam.api.vanish.PlayerShowEvent;
import de.myzelyam.api.vanish.VanishAPI;
import dev.kyro.arcticapi.misc.AOutput;
import me.clip.placeholderapi.PlaceholderAPI;
import net.pitsim.skywars.PitSim;
import net.pitsim.skywars.controllers.DamageManager;
import net.pitsim.skywars.controllers.objects.PitPlayer;
import net.pitsim.skywars.misc.Misc;
import net.pitsim.skywars.misc.Sounds;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class QueueManager implements Listener {
	public static BukkitTask countdown = null;

	public static boolean override = false;
	public static int maxPlayers = 12;
	public static int minPlayers = 3;
	public static int quickStartPlayers = 6;
	public static int timerStartMinutes = 2;
	public static int quickTimerStartSeconds = 30;
	public static List<Integer> countdownAnnouncements = Arrays.asList(60, 30, 20, 10, 5, 4, 3, 2, 1);


	public static Map<Player, Integer> playerCages = new HashMap<>();

	@EventHandler
	public void onJoin(PlayerJoinEvent event) {
		if(GameManager.status != GameStatus.QUEUE) return;
		Player player = event.getPlayer();
		player.getInventory().clear();
		Misc.clearArmor(player);
		ExperienceManager.setXPBar(player);
		if(VanishAPI.isInvisible(player)) {
			player.teleport(new Location(MapManager.getWorld(), 0, 100, 0));
			player.setAllowFlight(true);
			player.setFlying(true);
			AOutput.send(player, "&aYou are currently vanished. Un-vanish to join the game.");
			return;
		}
		KitPerkChangeManager.givePerkChange(player);
		GameManager.alivePlayers.add(player);

		String name = "%luckperms_prefix%" + player.getDisplayName();
		AOutput.broadcast(PlaceholderAPI.setPlaceholders(player, name) + " &ehas joined &7(&e" +
				GameManager.alivePlayers.size() + "&7/&e" + maxPlayers + "&7)");

		assignCage(player);
		if(!playerCages.containsKey(player)) {
			player.kickPlayer("&cThis game is currently full!");
			return;
		}

		Location spawnLocation = MapManager.map.getSpawnLocations().get(playerCages.get(player));
		player.teleport(spawnLocation);
		new BukkitRunnable() {
			@Override
			public void run() {
				player.teleport(spawnLocation);
			}
		}.runTaskLater(PitSim.INSTANCE, 1L);

		countdown();
	}

	@EventHandler
	public void onLeave(PlayerQuitEvent event) {
		if(GameManager.status != GameStatus.QUEUE) return;
		Player player = event.getPlayer();
		player.getInventory().clear();
		if(VanishAPI.isInvisible(player)) return;
		GameManager.alivePlayers.remove(player);

		String name = "%luckperms_prefix%" + player.getDisplayName();
		AOutput.broadcast(PlaceholderAPI.setPlaceholders(player, name) + " &ehas left &7(&e" +
				GameManager.alivePlayers.size() + "&7/&e" + maxPlayers + "&7)");

		playerCages.remove(player);

		countdown();
	}

	private static int minutes;
	private static int seconds;
	private static boolean isQuickStart = false;

	public static void setSeconds(int set) {
		seconds = set;
	}

	public static void setMinutes(int set) {
		minutes = set;
	}

	public static void countdown() {
		if(countdown == null) {
			if(!override && GameManager.alivePlayers.size() < minPlayers) return;
			minutes = timerStartMinutes;
			seconds = 0;
			countdown = new BukkitRunnable() {
				@Override
				public void run() {
					if(minutes > 0) {
						if(seconds == 0) {
							AOutput.broadcast("&eThe game is starting in &a" + minutes + " &eminutes!");
							for(Player onlinePlayer : Bukkit.getOnlinePlayers()) {
								Sounds.HELMET_TICK.play(onlinePlayer);
							}
						}
					} else {
						if(countdownAnnouncements.contains(seconds)) {
							AOutput.broadcast("&eThe game is starting in &a" + seconds + " &eseconds!");
							for(Player onlinePlayer : Bukkit.getOnlinePlayers()) {
								Sounds.HELMET_TICK.play(onlinePlayer);
							}
						}

					}
					if(seconds == 0) {
						if(minutes != 0) {
							minutes--;
							seconds = 60;
						} else {
							GameManager.startGame();
							this.cancel();
							countdown = null;
						}
					}
					seconds--;
				}
			}.runTaskTimer(PitSim.INSTANCE, 20L, 20L);
		} else {
			if(GameManager.alivePlayers.size() >= quickStartPlayers && !isQuickStart) {
				minutes = 0;
				seconds = quickTimerStartSeconds;
				isQuickStart = true;
			}
			if(GameManager.alivePlayers.size() < minPlayers) {
				AOutput.broadcast("&cGame start canceled! Too few players!");
				for(Player onlinePlayer : Bukkit.getOnlinePlayers()) {
					Sounds.CTF_FLAG_STOLEN.play(onlinePlayer);
				}
				countdown.cancel();
				countdown = null;
			}
		}
	}

	public static void assignCage(Player player) {
		for(Map.Entry<Integer, Location> entry : MapManager.map.getSpawnLocations().entrySet()) {
			int num = entry.getKey();
			int cage = (num % 4) * 3 + num / 4;

			if(playerCages.containsValue(cage)) continue;

			playerCages.put(player, cage);
			return;
		}
	}

	public static int getMinutes() {
		return minutes;
	}

	public static int getSeconds() {
		return seconds;
	}

	@EventHandler
	public void onWrongWorldJoin(PlayerJoinEvent event) {
		new BukkitRunnable() {
			@Override
			public void run() {

				if(VanishAPI.isInvisible(event.getPlayer())) {
					event.getPlayer().teleport(new Location(MapManager.getWorld(), 0, 100, 0));
					return;
				}

				if(event.getPlayer().getWorld() != MapManager.getWorld()) {
					int cage = playerCages.getOrDefault(event.getPlayer(), 0);
					if(cage == 0) {
						event.getPlayer().kickPlayer(ChatColor.RED + "There was an error while loading your client.");
						return;
					}
					event.getPlayer().teleport(MapManager.map.getSpawnLocations().get(cage));
				}
			}
		}.runTaskLater(PitSim.INSTANCE, 10L);
	}

	@EventHandler
	public void onUnvanish(PlayerShowEvent event) {
		Player player = event.getPlayer();

		if(GameManager.status == GameStatus.QUEUE) {
			player.getInventory().clear();
			Misc.clearArmor(player);
			player.setGameMode(GameMode.SURVIVAL);
			player.setFlying(false);
			player.setAllowFlight(false);
			ExperienceManager.setXPBar(player);
			GameManager.alivePlayers.add(player);
			KitPerkChangeManager.givePerkChange(player);

			String name = "%luckperms_prefix%" + player.getDisplayName();
			AOutput.broadcast(PlaceholderAPI.setPlaceholders(player, name) + " &ehas joined &7(&e" +
					GameManager.alivePlayers.size() + "&7/&e" + maxPlayers + "&7)");


			assignCage(player);
			if(!playerCages.containsKey(player)) {
				player.kickPlayer("&cThis game is currently full!");
				return;
			}
			Location spawnLocation = MapManager.map.getSpawnLocations().get(playerCages.get(player));

			new BukkitRunnable() {
				@Override
				public void run() {
					player.teleport(spawnLocation);
				}
			}.runTaskLater(PitSim.INSTANCE, 1L);

			countdown();
		} else {
			SpectatorManager.setSpectator(player);
		}
	}

	@EventHandler
	public void onVanish(PlayerHideEvent event) {
		Player player = event.getPlayer();

		if(GameManager.status == GameStatus.QUEUE) {
			player.getInventory().clear();
			GameManager.alivePlayers.remove(player);

			String name = "%luckperms_prefix%" + player.getDisplayName();
			AOutput.broadcast(PlaceholderAPI.setPlaceholders(player, name) + " &ehas left &7(&e" +
					GameManager.alivePlayers.size() + "&7/&e" + maxPlayers + "&7)");

			playerCages.remove(player);
			event.getPlayer().teleport(new Location(MapManager.getWorld(), 0, 100, 0));
			player.setAllowFlight(true);
			player.setFlying(true);

			countdown();
		} else if(GameManager.status == GameStatus.ACTIVE) {
			if(!GameManager.alivePlayers.contains(player)) return;

			String playerName = "%luckperms_prefix%" + player.getDisplayName();
			PitPlayer pitPlayer = PitPlayer.getPitPlayer(player);
			if(pitPlayer.lastHitUUID == null)
				AOutput.broadcast(PlaceholderAPI.setPlaceholders(player, playerName + " &edisconnected."));
			DamageManager.death(player);
			SpectatorManager.spectators.remove(player);
		}
	}
}
