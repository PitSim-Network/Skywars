package net.pitsim.skywars.placeholders;

import dev.kyro.arcticapi.hooks.APAPIPlaceholder;
import net.pitsim.skywars.game.GameClock;
import net.pitsim.skywars.game.QueueManager;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class GameTimePlaceholder implements APAPIPlaceholder {

	@Override
	public String getIdentifier() {
		return "gametime";
	}

	@Override
	public String getValue(Player player) {
		int minutes = GameClock.minutes;
		int seconds = GameClock.seconds;

		String title;
		if(minutes > 4) title = ChatColor.GOLD + "Refill: ";
		else title = ChatColor.GOLD + "Game end: ";

		if(minutes > 0) {
			return title + "&e" + minutes + "m " + seconds + "s";
		} else {
			return title + "&e" + seconds + "s";
		}
	}
}
