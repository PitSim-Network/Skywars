package net.pitsim.skywars.placeholders;

import dev.kyro.arcticapi.hooks.APAPIPlaceholder;
import net.pitsim.skywars.controllers.ScoreboardManager;
import org.bukkit.entity.Player;

public class LinePlaceholder6 implements APAPIPlaceholder {

	@Override
	public String getIdentifier() {
		return "line6";
	}

	@Override
	public String getValue(Player player) {
		return ScoreboardManager.activeScoreboard.getLine6(player);
	}
}