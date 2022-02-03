package net.pitsim.skywars.placeholders;

import dev.kyro.arcticapi.hooks.APAPIPlaceholder;
import net.pitsim.skywars.game.GameClock;
import net.pitsim.skywars.game.QueueManager;
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

		if(minutes > 0) {
			return "&e" + minutes + "m";
		} else {
			return "&e" + seconds + "s";
		}
	}
}
