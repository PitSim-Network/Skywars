package net.pitsim.skywars.placeholders;

import dev.kyro.arcticapi.hooks.APAPIPlaceholder;
import net.pitsim.skywars.PitSim;
import org.bukkit.entity.Player;

public class ServerPlaceholder implements APAPIPlaceholder {

	@Override
	public String getIdentifier() {
		return "server";
	}

	@Override
	public String getValue(Player player) {
		String id = PitSim.INSTANCE.getConfig().getString("server-ID");
		return "&8SW-" + id;
	}
}
