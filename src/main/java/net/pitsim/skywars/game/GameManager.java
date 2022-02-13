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
			Sounds.GAME_START.play(player);
		}

		MapManager.onGameStart();
	}

	public static void endGame() {
		status = GameStatus.ENDING;
		Player winner = alivePlayers.get(0);
		Misc.sendTitle(winner, "&6&lVICTORY!", 100);
		Misc.sendSubTitle(winner, "&7You won the game!", 100);
		Sounds.LEVEL_UP.play(winner);
		GameClock.countdown.cancel();

		Player killer1 = null;
		Player killer2 = null;
		Player killer3 = null;
		int kills1;
		int kills2;
		int kills3;

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
		kills1 = highestValue;
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
		kills2 = highestValue;
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
		kills3 = highestValue;
		KillManager.kills.remove(highestKey);

		String winnerName = "%luckperms_prefix%" + winner.getDisplayName();
		String killer1Name = null;
		String killer2Name = null;
		String killer3Name = null;

		if(killer1 != null) killer1Name = "%luckperms_prefix%" + killer1.getDisplayName();
		if(killer2 != null) killer2Name = "%luckperms_prefix%" + killer2.getDisplayName();
		if(killer3 != null) killer3Name = "%luckperms_prefix%" + killer3.getDisplayName();

		AOutput.broadcast("&8&m--------------------------");
		AOutput.broadcast("&6&lGAME ENDED");
		AOutput.broadcast("");
		AOutput.broadcast("&e&lWinner: &r" + PlaceholderAPI.setPlaceholders(winner, winnerName));
		AOutput.broadcast("");
		if(killer1Name != null) AOutput.broadcast("&fFirst Killer: &r" + PlaceholderAPI.setPlaceholders(killer1, killer1Name) + " &e" + kills1 + " kills");
		if(killer2Name != null) AOutput.broadcast("&6Second Killer: &r" + PlaceholderAPI.setPlaceholders(killer2, killer2Name) + " &e" + kills2 + " kills");
		if(killer3Name != null) AOutput.broadcast("&cThird Killer: &r" + PlaceholderAPI.setPlaceholders(killer3, killer3Name) + " &e" + kills2 + " kills");
		AOutput.broadcast("&8&m--------------------------");

		new BukkitRunnable() {
			@Override
			public void run() {
				PluginMessageSender.sendToLobby();
			}
		}.runTaskLater(PitSim.INSTANCE, 10 * 20L);
	}

	public static void endTieGame() {
		status = GameStatus.ENDING;
		for (Player alivePlayer : GameManager.alivePlayers) {
			Sounds.LEVEL_UP.play(alivePlayer);
		}
		AOutput.broadcast("&8&m--------------------------");
		AOutput.broadcast("&6&lTIE GAME");
		AOutput.broadcast("");
		StringBuilder tieString = new StringBuilder();
		tieString.append(PlaceholderAPI.setPlaceholders(GameManager.alivePlayers.get(0), "%luckperms_prefix%" + GameManager.alivePlayers.get(0).getDisplayName()));
		for (Player alivePlayer : GameManager.alivePlayers) {
			if(alivePlayer == GameManager.alivePlayers.get(0)) continue;
			tieString.append(PlaceholderAPI.setPlaceholders(alivePlayer, " &e, %luckperms_prefix%" + alivePlayer.getDisplayName()));
		}
		AOutput.broadcast("&e&lWinners: &r" + tieString);
		AOutput.broadcast("&8&m--------------------------");

		new BukkitRunnable() {
			@Override
			public void run() {
				PluginMessageSender.sendEnd();
				PluginMessageSender.sendToLobby();
			}
		}.runTaskLater(PitSim.INSTANCE, 10 * 20L);
	}



}
