package net.pitsim.skywars.placeholders;

import dev.kyro.arcticapi.hooks.APAPIPlaceholder;
import net.pitsim.skywars.controllers.objects.PitPlayer;
import net.pitsim.skywars.game.ExperienceManager;
import org.bukkit.entity.Player;

public class LevelPlaceholder implements APAPIPlaceholder {

	@Override
	public String getIdentifier() {
		return "level";
	}

	@Override
	public String getValue(Player player) {
		return ExperienceManager.formattedLevel(PitPlayer.getPitPlayer(player).stats.level);
	}
}