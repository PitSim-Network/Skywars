package net.pitsim.skywars.placeholders;

import dev.kyro.arcticapi.hooks.APAPIPlaceholder;
import net.pitsim.skywars.game.GameManager;
import net.pitsim.skywars.game.QueueManager;
import org.bukkit.entity.Player;

public class QueueTimePlaceholder implements APAPIPlaceholder {

	@Override
	public String getIdentifier() {
		return "queuetime";
	}

	@Override
	public String getValue(Player player) {

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
}
