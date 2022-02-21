package net.pitsim.skywars.inventories;

import dev.kyro.arcticapi.gui.AGUI;
import org.bukkit.entity.Player;

public class PerkEquipGUI extends AGUI {

	public EquippedPerksPanel equippedPerksPanel;
	public AllPerksPanel allPerksPanel;

	public int perkSlot = 1;

	public PerkEquipGUI(Player player) {
		super(player);

		equippedPerksPanel = new EquippedPerksPanel(this);
		allPerksPanel = new AllPerksPanel(this);
		setHomePanel(equippedPerksPanel);

	}

}
