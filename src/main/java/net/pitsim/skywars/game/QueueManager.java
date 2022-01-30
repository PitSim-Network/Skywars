package net.pitsim.skywars.game;

import dev.kyro.arcticapi.misc.AOutput;
import me.clip.placeholderapi.PlaceholderAPI;
import net.pitsim.skywars.PitSim;
import net.pitsim.skywars.misc.Sounds;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.Arrays;
import java.util.List;

public class QueueManager implements Listener {
	public static BukkitTask countdown;

	public static int maxPlayers = 16;
	public static int minPlayers = 3;
	public static int quickStartPlayers = 10;
	public static int timerStartMinutes = 1;
	public static int quickTimerStartSeconds = 10;
	public static List<Integer> countdownAnnouncements = Arrays.asList(60, 30, 20, 10, 5, 4, 3, 2, 1);

	@EventHandler
	public void onJoin(PlayerJoinEvent event) {
		if(GameManager.status != GameStatus.QUEUE) return;
		Player player = event.getPlayer();
		player.getInventory().clear();
		GameManager.players.add(player);

		String name = "%luckperms_prefix%" + player.getDisplayName();
		AOutput.broadcast(PlaceholderAPI.setPlaceholders(player, name) + " &ehas joined &7(&e" +
				GameManager.players.size() + "&7/&e" + maxPlayers + "&7)");

		player.teleport(MapManager.queueSpawn);
		countdown();
	}

	@EventHandler
	public void onLeave(PlayerQuitEvent event) {
		if(GameManager.status != GameStatus.QUEUE) return;
		Player player = event.getPlayer();
		player.getInventory().clear();
		GameManager.players.remove(player);

		String name = "%luckperms_prefix%" + player.getDisplayName();
		AOutput.broadcast(PlaceholderAPI.setPlaceholders(player, name) + " &ehas left &7(&e" +
				GameManager.players.size() + "&7/&e" + maxPlayers + "&7)");

		countdown();
	}

	private int minutes;
	private int seconds;
	private boolean isQuickStart = false;

	public void countdown() {
		if(countdown == null) {
			minutes = timerStartMinutes;
			seconds = 0;
			countdown = new BukkitRunnable() {
				@Override
				public void run() {
					if(minutes > 1) {
						if(seconds == 0) AOutput.broadcast("&eThe game is starting in &a" + minutes + " &eminutes!");
					} else {
						if(countdownAnnouncements.contains(seconds)) AOutput.broadcast("&eThe game is starting in &a" + seconds + " &eseconds!");
					}
					if(seconds == 0) {
						if(minutes != 0) {
							minutes--;
							seconds = 60;
						} else GameManager.startGame();
					}
					seconds--;
				}
			}.runTaskTimer(PitSim.INSTANCE, 20L, 20L);
		} else {
			if(GameManager.players.size() >= quickStartPlayers && !isQuickStart) {
				minutes = 0;
				seconds = quickTimerStartSeconds;
				isQuickStart = true;
			}
			if(GameManager.players.size() < minPlayers) {
				AOutput.broadcast("&cGame start canceled! Too few players!");
				countdown.cancel();
				countdown = null;
			}
		}
	}
}
