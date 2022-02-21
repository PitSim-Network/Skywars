package net.pitsim.skywars.inventories;

import dev.kyro.arcticapi.gui.AGUI;
import net.pitsim.skywars.controllers.objects.SkywarsPerk;
import org.bukkit.entity.Player;

public class PerkPurchaseGUI extends AGUI {

	public PerkPurchasePanel perkPurchasePanel;
	public PerkPurchaseConfirmPanel purchaseConfirmPanel;
	public SkywarsPerk clickedPerk = null;

	public int perkSlot = 1;

	public PerkPurchaseGUI(Player player) {
		super(player);

		perkPurchasePanel = new PerkPurchasePanel(this);
		purchaseConfirmPanel = new PerkPurchaseConfirmPanel(this);
		setHomePanel(perkPurchasePanel);

	}

}
