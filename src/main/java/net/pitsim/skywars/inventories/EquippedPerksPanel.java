package net.pitsim.skywars.inventories;

import dev.kyro.arcticapi.gui.AGUI;
import dev.kyro.arcticapi.gui.AGUIPanel;
import net.pitsim.skywars.controllers.objects.PitPlayer;
import net.pitsim.skywars.controllers.objects.SkywarsPerk;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class EquippedPerksPanel extends AGUIPanel {
	PitPlayer pitPlayer = PitPlayer.getPitPlayer(player);
	public PerkEquipGUI perkEquipGUI;

	public List<Player> headList = new ArrayList<>();

	public EquippedPerksPanel(AGUI gui) {
		super(gui);
		perkEquipGUI = (PerkEquipGUI) gui;
		inventoryBuilder.createBorder(Material.STAINED_GLASS_PANE, 8);
	}

	@Override
	public String getName() {
		return "Choose a Perk Slot";
	}

	@Override
	public int getRows() {
		return 3;
	}

	@Override
	public void onClick(InventoryClickEvent event) {
		int slot = event.getSlot();
		if(event.getClickedInventory().getHolder() == this) {
			if(slot == 10) {
				perkEquipGUI.perkSlot = 1;
				openPanel(perkEquipGUI.allPerksPanel);
			}
			if(slot == 12) {
				perkEquipGUI.perkSlot = 2;
				openPanel(perkEquipGUI.allPerksPanel);
			}
			if(slot == 14) {
				perkEquipGUI.perkSlot = 3;
				openPanel(perkEquipGUI.allPerksPanel);
			}
			if(slot == 16) {
				perkEquipGUI.perkSlot = 4;
				openPanel(perkEquipGUI.allPerksPanel);
			}
		}
	}

	@Override
	public void onOpen(InventoryOpenEvent event) {


		int perkSlot =  1;
		//10

		for(SkywarsPerk perk : pitPlayer.equippedPerks.perks) {
			ItemStack perkItem = new ItemStack(perk.getMaterial());
			ItemMeta perkMeta = perkItem.getItemMeta();
			perkMeta.setDisplayName(ChatColor.YELLOW + "Perk Slot #" + perkSlot);
			List<String> perkLore = new ArrayList<>();
			perkLore.add(ChatColor.translateAlternateColorCodes('&', "&7Selected: &a" + perk.name));
			perkLore.add("");
			perkLore.addAll(perk.getEquipLore(player));
			perkLore.add("");
			perkLore.add(ChatColor.YELLOW + "Click to change Perk");
			perkMeta.setLore(perkLore);
			perkItem.setItemMeta(perkMeta);

			getInventory().setItem((perkSlot * 2) + 8, perkItem);
			perkSlot++;
		}

	}

	@Override
	public void onClose(InventoryCloseEvent event) { }
}
