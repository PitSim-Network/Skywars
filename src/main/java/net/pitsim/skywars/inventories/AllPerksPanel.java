package net.pitsim.skywars.inventories;

import dev.kyro.arcticapi.gui.AGUI;
import dev.kyro.arcticapi.gui.AGUIPanel;
import dev.kyro.arcticapi.misc.AOutput;
import net.pitsim.skywars.controllers.objects.PitPlayer;
import net.pitsim.skywars.controllers.objects.SkywarsPerk;
import net.pitsim.skywars.misc.Sounds;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;

public class AllPerksPanel extends AGUIPanel {
	PitPlayer pitPlayer = PitPlayer.getPitPlayer(player);
	public PerkEquipGUI perkEquipGUI;

	public Map<String, Integer> perkInvSlots = new HashMap<>();

	public AllPerksPanel(AGUI gui) {
		super(gui);
		perkEquipGUI = (PerkEquipGUI) gui;
		inventoryBuilder.createBorder(Material.STAINED_GLASS_PANE, 8);

	}

	public List<Integer> excludeSlots = Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 17, 18, 26, 27, 35, 36, 44, 45);

	@Override
	public String getName() {
		return "Choose a Perk";
	}

	@Override
	public int getRows() {
		return 5;
	}

	@Override
	public void onClick(InventoryClickEvent event) {
		int slot = event.getSlot();
		if(event.getClickedInventory().getHolder() == this) {
			int perkSlot = perkEquipGUI.perkSlot;

			if(slot == 40) {
				openPreviousGUI();
				return;
			}

			if(slot == 41) {
				pitPlayer.equippedPerks.perks[perkSlot - 1] = SkywarsPerk.getPerk("no_perk");
				Sounds.SUCCESS.play(player);
				pitPlayer.equippedPerks.save();
				openPreviousGUI();
				pitPlayer.updateMaxHealth();
				player.setHealth(player.getMaxHealth());
			}

			for(Map.Entry<String, Integer> stringIntegerEntry : perkInvSlots.entrySet()) {
				if(!(stringIntegerEntry.getValue() == slot)) continue;

				SkywarsPerk perk = SkywarsPerk.getPerk(stringIntegerEntry.getKey());
				if(perk == null) {
					player.closeInventory();
					return;
				}
				if(SkywarsPerk.getPerkTier(player, perk.refName) == 0) {
					AOutput.error(player, "&cYou have not unlocked this perk yet!");
					Sounds.ERROR.play(player);
					return;
				}
				if(SkywarsPerk.hasPerkEquipped(player, perk.refName)) {
					AOutput.error(player, "&cThis perk is already equipped!");
					Sounds.ERROR.play(player);
					return;
				}

				pitPlayer.equippedPerks.perks[perkSlot - 1] = perk;
				Sounds.SUCCESS.play(player);
				pitPlayer.equippedPerks.save();
				openPreviousGUI();
				pitPlayer.updateMaxHealth();
				player.setHealth(player.getMaxHealth());

			}

		}
	}

	@Override
	public void onOpen(InventoryOpenEvent event) {
		int slotCount = 0;

		for(SkywarsPerk perk : SkywarsPerk.perks) {
			if(perk.refName.equals("no_perk")) {
				ItemStack noPerkItem = new ItemStack(Material.DIAMOND_BLOCK);
				ItemMeta meta = noPerkItem.getItemMeta();
				meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&cNo Perk"));
				List<String> lore = new ArrayList<>();
				lore.add(ChatColor.GRAY + "Are you hardcore enough that");
				lore.add(ChatColor.GRAY + "you don't need any perk for");
				lore.add(ChatColor.GRAY + "this slot?");
				lore.add("");
				lore.add(ChatColor.YELLOW + "Click to remove perk!");
				meta.setLore(lore);
				noPerkItem.setItemMeta(meta);
				getInventory().setItem(41, noPerkItem);
			}

			if(perk.refName.equals("no_perk")) continue;

			ItemStack perkItem = new ItemStack(perk.getMaterial());
			if(SkywarsPerk.getPerkTier(player, perk.refName) == 0) perkItem.setType(Material.BEDROCK);
			ItemMeta perkMeta = perkItem.getItemMeta();
			List<String> perkLore = new ArrayList<>(perk.getEquipLore(player));
			perkLore.add("");

			if(SkywarsPerk.getPerkTier(player, perk.refName) == 0) {
				perkMeta.setDisplayName(ChatColor.RED + perk.name);
				perkLore.add(ChatColor.RED + "Unlocked in Perk Shop");
			} else if(SkywarsPerk.hasPerkEquipped(player, perk.refName)) {
				perkMeta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1, false);
				perkMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
				perkMeta.setDisplayName(ChatColor.GREEN + perk.name);
				perkLore.add(ChatColor.GREEN + "Already Selected!");
			} else {
				perkMeta.setDisplayName(ChatColor.YELLOW + perk.name);
				perkLore.add(ChatColor.YELLOW + "Click to Select Perk!");
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

		ItemStack back = new ItemStack(Material.ARROW);
		ItemMeta meta = back.getItemMeta();
		meta.setDisplayName(ChatColor.GREEN + "Go Back");
		List<String> lore = new ArrayList<>();
		lore.add(ChatColor.GRAY + "To Perks");
		meta.setLore(lore);
		back.setItemMeta(meta);

		getInventory().setItem(40, back);

	}

	@Override
	public void onClose(InventoryCloseEvent event) { }
}
