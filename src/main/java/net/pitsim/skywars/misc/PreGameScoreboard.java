package net.pitsim.skywars.misc;

import me.clip.placeholderapi.PlaceholderAPI;
import net.pitsim.skywars.controllers.objects.Scoreboard;
import net.pitsim.skywars.game.GameManager;
import net.pitsim.skywars.game.MapManager;
import net.pitsim.skywars.game.QueueManager;
import org.bukkit.entity.Player;

public class PreGameScoreboard extends Scoreboard {

	public static PreGameScoreboard INSTANCE;

	@Override
	public String getLine1(Player player) {
		int current = GameManager.alivePlayers.size();
		int max = QueueManager.maxPlayers;

		return "&6Players: " + "&e" + current + "&7/&e" + max;
	}

	@Override
	public String getLine2(Player player) {
		return "";
	}

	@Override
	public String getLine3(Player player) {
		if(QueueManager.countdown == null) {
			return "&cWaiting for players";
		}

		int minutes = QueueManager.getMinutes();
		int seconds = QueueManager.getSeconds();
		String start = "&6Starting in: &e";

		if(seconds == 59) {
			minutes++;
			seconds = 0;
		} else seconds++;

		if(minutes == 0) {
			return start + seconds + "s";
		} else return start + minutes + "m " + seconds + "s";
	}

	@Override
	public String getLine4(Player player) {
		return "";
	}

	@Override
	public String getLine5(Player player) {
		return "&6Rank: " + PlaceholderAPI.setPlaceholders(player, "%luckperms_primary_group_name%");
	}

	@Override
	public String getLine6(Player player) {
		return "";
	}

	@Override
	public String getLine7(Player player) {
		return "&6Map: &e" + MapManager.map.name;
	}

	@Override
	public String getLine8(Player player) {
		return "&6Mode: &dMystic Skywars";
	}
}
