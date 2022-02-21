package net.pitsim.skywars.placeholders;

import dev.kyro.arcticapi.hooks.APAPIPlaceholder;
import net.pitsim.skywars.controllers.ScoreboardManager;
import org.bukkit.entity.Player;

public class LinePlaceholder7 implements APAPIPlaceholder {

	@Override
	public String getIdentifier() {
		return "line7";
	}

	@Override
	public String getValue(Player player) {
		return ScoreboardManager.activeScoreboard.getLine7(player);
	}
}