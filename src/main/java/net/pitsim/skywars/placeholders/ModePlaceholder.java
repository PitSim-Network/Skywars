package net.pitsim.skywars.placeholders;

import dev.kyro.arcticapi.hooks.APAPIPlaceholder;
import net.pitsim.skywars.game.MapManager;
import org.bukkit.entity.Player;

public class ModePlaceholder implements APAPIPlaceholder {

	@Override
	public String getIdentifier() {
		return "mode";
	}

	@Override
	public String getValue(Player player) {
		return "&dMystic Skywars";
	}
}
