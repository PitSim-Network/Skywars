package net.pitsim.skywars.misc;

import net.pitsim.skywars.controllers.objects.Scoreboard;
import net.pitsim.skywars.game.*;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class GameScoreboard extends Scoreboard {

	public static GameScoreboard INSTANCE;

	@Override
	public String getLine1(Player player) {
		int minutes = GameClock.minutes;
		int seconds = GameClock.seconds;

		if(GameManager.status == GameStatus.ENDING) return "&c&lGAME ENDED";

		String title;
		if(!GameClock.refill) title = ChatColor.GOLD + "Refill: ";
		else title = ChatColor.GOLD + "Game end: ";

		if(minutes > 0) {
			return title + "&e" + minutes + "m " + seconds + "s";
		} else {
			return title + "&e" + seconds + "s";
		}
	}

	@Override
	public String getLine2(Player player) {
		return "";
	}

	@Override
	public String getLine3(Player player) {
		return "&6Players left: &e" + GameManager.alivePlayers.size() + "";
	}

	@Override
	public String getLine4(Player player) {
		return "";
	}

	@Override
	public String getLine5(Player player) {
		return "&6Kills: &e" + KillManager.kills.getOrDefault(player, 0) + "";
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
