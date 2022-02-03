package net.pitsim.skywars.placeholders;

import dev.kyro.arcticapi.hooks.APAPIPlaceholder;
import net.pitsim.skywars.game.GameClock;
import net.pitsim.skywars.game.GameManager;
import org.bukkit.entity.Player;

public class PlayersLeftPlaceholder implements APAPIPlaceholder {

	@Override
	public String getIdentifier() {
		return "players";
	}

	@Override
	public String getValue(Player player) {
		return GameManager.alivePlayers.size() + "";
	}
}
