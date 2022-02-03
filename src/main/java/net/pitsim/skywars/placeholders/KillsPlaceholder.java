package net.pitsim.skywars.placeholders;

import dev.kyro.arcticapi.hooks.APAPIPlaceholder;
import net.pitsim.skywars.game.GameManager;
import net.pitsim.skywars.game.KillManager;
import org.bukkit.entity.Player;

public class KillsPlaceholder implements APAPIPlaceholder {

	@Override
	public String getIdentifier() {
		return "kills";
	}

	@Override
	public String getValue(Player player) {
		return KillManager.kills.getOrDefault(player, 0) + "";
	}
}
