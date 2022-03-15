package net.pitsim.skywars.inventories;

import dev.kyro.arcticapi.gui.AGUI;
import dev.kyro.arcticapi.gui.AGUIPanel;
import dev.kyro.arcticapi.misc.AOutput;
import net.pitsim.skywars.controllers.objects.PitPlayer;
import net.pitsim.skywars.controllers.objects.SkywarsPerk;
import net.pitsim.skywars.misc.Sounds;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.text.DecimalFormat;
import java.util.*;

public class PerkPurchasePanel extends AGUIPanel {
	PitPlayer pitPlayer = PitPlayer.getPitPlayer(player);
	public PerkPurchaseGUI perkPurchaseGUI;

	public Map<String, Integer> perkInvSlots = new HashMap<>();

	public PerkPurchasePanel(AGUI gui) {
		super(gui);
		perkPurchaseGUI = (PerkPurchaseGUI) gui;
		inventoryBuilder.createBorder(Material.STAINED_GLASS_PANE, 8);

	}

	public List<Integer> excludeSlots = Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 17, 18, 26, 27, 35, 36, 44, 45);

	@Override
	public String getName() {
		return "Unlock or Upgrade a Perk";
	}

	@Override
	public int getRows() {
		return 5;
	}

	@Override
	public void onClick(InventoryClickEvent event) {
		int slot = event.getSlot();

		if(event.getClickedInventory().getHolder() == this) {

			if(slot == 40) {
				openPreviousGUI();
				return;
			}

			for(Map.Entry<String, Integer> stringIntegerEntry : perkInvSlots.entrySet()) {
				if(!(stringIntegerEntry.getValue() == slot)) continue;
				SkywarsPerk perk = SkywarsPerk.getPerk(stringIntegerEntry.getKey());

				if(perk == null) {
					player.closeInventory();
					return;
				}
				if(SkywarsPerk.getPerkTier(player, perk.refName) >= perk.cost.size()) {
					AOutput.error(player, "&cYou have already unlocked the maximum tier for this perk!");
					Sounds.ERROR.play(player);
					return;
				}
				int cost = perk.cost.get(SkywarsPerk.getPerkTier(player, perk.refName));
				if(pitPlayer.stats.coins < cost) {
					AOutput.error(player, "&cYou do not have enough coins to unlock this!");
					Sounds.ERROR.play(player);
					return;
				}

				perkPurchaseGUI.clickedPerk = perk;
				openPanel(perkPurchaseGUI.purchaseConfirmPanel);

			}

		}
	}

	@Override
	public void onOpen(InventoryOpenEvent event) {
		int slotCount = 0;

		for(SkywarsPerk perk : SkywarsPerk.perks) {
			if(perk.refName.equals("no_perk")) continue;

			ItemStack perkItem = new ItemStack(perk.getMaterial());
			ItemMeta perkMeta = perkItem.getItemMeta();
			List<String> perkLore = new ArrayList<>(perk.getEquipLore(player));
			perkLore.add("");

			DecimalFormat format = new DecimalFormat("###,###,###");

			if(SkywarsPerk.getPerkTier(player, perk.refName) >= perk.cost.size()) {
				perkMeta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1, false);
				perkMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
				perkMeta.setDisplayName(ChatColor.GREEN + perk.name);
				perkLore.add(ChatColor.GREEN + "Max tier Unlocked!");
			} else {
				int cost = perk.cost.get(SkywarsPerk.getPerkTier(player, perk.refName));
				perkLore.add(ChatColor.translateAlternateColorCodes('&', "&7Cost: &6" + format.format(cost) + " Coins"));
				perkLore.add(ChatColor.translateAlternateColorCodes('&', "&7You have: &6" + format.format(pitPlayer.stats.coins) + " Coins"));
				perkLore.add("");
				if(cost > pitPlayer.stats.coins) {
					perkMeta.setDisplayName(ChatColor.RED + perk.name);
					perkLore.add(ChatColor.RED + "Not enough coins!");
				} else {
					perkMeta.setDisplayName(ChatColor.YELLOW + perk.name);
					if(SkywarsPerk.getPerkTier(player, perk.refName) == 0) {
						perkLore.add(ChatColor.YELLOW + "Click to Unlock Perk!");
					} else perkLore.add(ChatColor.YELLOW + "Click to Upgrade Perk!");
				}
			}

			perkMeta.setLore(perkLore);
			perkItem.setItemMeta(perkMeta);

			while(excludeSlots.contains(slotCount)) {
				slotCount++;
			}
			getInventory().setItem(slotCount, perkItem);
			perkInvSlots.put(perk.refName, slotCount);
			slotCount++;
		}

	}

	@Override
	public void onClose(InventoryCloseEvent event) { }
}
