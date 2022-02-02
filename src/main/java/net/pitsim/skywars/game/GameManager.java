package net.pitsim.skywars.game;

import dev.kyro.arcticapi.misc.AOutput;
import me.clip.placeholderapi.PlaceholderAPI;
import net.pitsim.skywars.PitSim;
import net.pitsim.skywars.misc.Misc;
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

		MapManager.onGameStart();
	}

	public static void endGame() {
		Player winner = alivePlayers.get(0);
		Misc.sendTitle(winner, "&6&lYOU WON!", 100);

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
		highestKey = null;
		highestValue = 0;

		String winnerName = "%luckperms_prefix%" + winner.getDisplayName();
		String killer1Name = "%luckperms_prefix%" + killer1.getDisplayName();
		String killer2Name = "%luckperms_prefix%" + killer2.getDisplayName();
		String killer3Name = "%luckperms_prefix%" + killer3.getDisplayName();


		AOutput.broadcast("&7&m--------------------");
		AOutput.broadcast("&fWinner: " + PlaceholderAPI.setPlaceholders(winner, winnerName));
		AOutput.broadcast("");
		AOutput.broadcast("&fFirst Killer: " + PlaceholderAPI.setPlaceholders(killer1, killer1Name));
		AOutput.broadcast("&fSecond Killer: " + PlaceholderAPI.setPlaceholders(killer2, killer2Name));
		AOutput.broadcast("&fThird Killer: " + PlaceholderAPI.setPlaceholders(killer3, killer3Name));
		AOutput.broadcast("&7&m--------------------");

		new BukkitRunnable() {
			@Override
			public void run() {
				PluginMessageSender.sendToLobby();
			}
		}.runTaskLater(PitSim.INSTANCE, 10 * 20L);
	}



}
