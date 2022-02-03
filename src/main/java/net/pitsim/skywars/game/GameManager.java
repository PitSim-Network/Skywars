package net.pitsim.skywars.game;

import be.maximvdw.featherboard.api.FeatherBoardAPI;
import dev.kyro.arcticapi.misc.AOutput;
import me.clip.placeholderapi.PlaceholderAPI;
import net.pitsim.skywars.PitSim;
import net.pitsim.skywars.misc.Misc;
import net.pitsim.skywars.misc.Sounds;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GameManager {
	public static GameStatus status;
	public static List<Player> alivePlayers = new ArrayList<>();

	public static void init() {
		status = GameStatus.QUEUE;
	}

	public static void startGame() {
		status = GameStatus.ACTIVE;
		PluginMessageSender.sendStart();
		GameClock.countdown();
		for(Player player : Bukkit.getOnlinePlayers()) {
			FeatherBoardAPI.showScoreboard(player, "game");
		}

		MapManager.onGameStart();
	}

	public static void endGame() {
		Player winner = alivePlayers.get(0);
		Misc.sendTitle(winner, "&6&lVICTORY!", 100);
		Misc.sendSubTitle(winner, "&7You won the game!", 100);
		Sounds.LEVEL_UP.play(winner);

		Player killer1 = null;
		Player killer2 = null;
		Player killer3 = null;

		int highestValue = 0;
		Player highestKey = null;

		for (Map.Entry<Player, Integer> entry : KillManager.kills.entrySet()) {
			Player player = entry.getKey();
			int kills = entry.getValue();

			if(highestKey == null) highestKey = player;
			if(highestValue == 0) highestValue = kills;

			if(kills > highestValue) {
				highestKey = player;
				highestValue = kills;
			}
		}
		killer1 = highestKey;
		KillManager.kills.remove(highestKey);
		highestKey = null;
		highestValue = 0;
		for (Map.Entry<Player, Integer> entry : KillManager.kills.entrySet()) {
			Player player = entry.getKey();
			int kills = entry.getValue();

			if(highestKey == null) highestKey = player;
			if(highestValue == 0) highestValue = kills;

			if(kills > highestValue) {
				highestKey = player;
				highestValue = kills;
			}
		}
		killer2 = highestKey;
		KillManager.kills.remove(highestKey);
		highestKey = null;
		highestValue = 0;
		for (Map.Entry<Player, Integer> entry : KillManager.kills.entrySet()) {
			Player player = entry.getKey();
			int kills = entry.getValue();

			if(highestKey == null) highestKey = player;
			if(highestValue == 0) highestValue = kills;

			if(kills > highestValue) {
				highestKey = player;
				highestValue = kills;
			}
		}
		killer3 = highestKey;
		KillManager.kills.remove(highestKey);

		String winnerName = "%luckperms_prefix%" + winner.getDisplayName();
		String killer1Name = null;
		String killer2Name = null;
		String killer3Name = null;

		if(killer1 != null) killer1Name = "%luckperms_prefix%" + killer1.getDisplayName();
		if(killer2 != null) killer2Name = "%luckperms_prefix%" + killer2.getDisplayName();
		if(killer3 != null) killer3Name = "%luckperms_prefix%" + killer3.getDisplayName();

		AOutput.broadcast("&8&m--------------------------");
		AOutput.broadcast("&e&lWinner: " + PlaceholderAPI.setPlaceholders(winner, winnerName));
		AOutput.broadcast("");
		if(killer1Name != null) AOutput.broadcast("&lFirst Killer: " + PlaceholderAPI.setPlaceholders(killer1, killer1Name));
		if(killer2Name != null) AOutput.broadcast("&6Second Killer: " + PlaceholderAPI.setPlaceholders(killer2, killer2Name));
		if(killer3Name != null) AOutput.broadcast("&cThird Killer: " + PlaceholderAPI.setPlaceholders(killer3, killer3Name));
		AOutput.broadcast("&8&m--------------------------");

		new BukkitRunnable() {
			@Override
			public void run() {
				PluginMessageSender.sendToLobby();
			}
		}.runTaskLater(PitSim.INSTANCE, 10 * 20L);
	}

	public static void endTieGame() {

	}



}
