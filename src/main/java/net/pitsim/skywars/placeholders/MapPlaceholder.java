package net.pitsim.skywars.placeholders;

import dev.kyro.arcticapi.hooks.APAPIPlaceholder;
import net.pitsim.skywars.game.MapManager;
import net.pitsim.skywars.game.QueueManager;
import org.bukkit.entity.Player;

public class MapPlaceholder implements APAPIPlaceholder {

	@Override
	public String getIdentifier() {
		return "map";
	}

	@Override
	public String getValue(Player player) {
		return "&e" + MapManager.map.name;
	}
}
