package net.pitsim.skywars.inventories;

import dev.kyro.arcticapi.gui.AGUI;
import org.bukkit.entity.Player;

public class SpectatorGUI extends AGUI {

	public SpectatorPanel spectatorPanel;

	public SpectatorGUI(Player player) {
		super(player);

		spectatorPanel = new SpectatorPanel(this);
		setHomePanel(spectatorPanel);

	}

}
