package net.pitsim.skywars.placeholders;

import dev.kyro.arcticapi.hooks.APAPIPlaceholder;
import net.pitsim.skywars.controllers.AFKManager;
import net.pitsim.skywars.controllers.objects.PitPlayer;
import org.bukkit.entity.Player;

public class SuffixPlaceholder implements APAPIPlaceholder {

	@Override
	public String getIdentifier() {
		return "suffix";
	}

	@Override
	public String getValue(Player player) {

		PitPlayer pitPlayer = PitPlayer.getPitPlayer(player);
		if(AFKManager.AFKPlayers.contains(player)) return "&8 [AFK]";
		else return "";
	}
}
