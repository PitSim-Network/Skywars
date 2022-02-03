package net.pitsim.skywars.placeholders;

import dev.kyro.arcticapi.hooks.APAPIPlaceholder;
import me.clip.placeholderapi.PlaceholderAPI;
import net.pitsim.skywars.controllers.objects.PitPlayer;
import net.pitsim.skywars.game.GameManager;
import net.pitsim.skywars.game.QueueManager;
import org.bukkit.entity.Player;

public class QueuePlayersPlaceholder implements APAPIPlaceholder {

	@Override
	public String getIdentifier() {
		return "queueplayers";
	}

	@Override
	public String getValue(Player player) {

		int current = GameManager.alivePlayers.size();
		int max = QueueManager.maxPlayers;

		return "&e" + current + "&7/&e" + max;

	}
}
