package net.pitsim.skywars.game.skywarsperks;

import dev.kyro.arcticapi.misc.AOutput;
import dev.kyro.arcticapi.misc.AUtil;
import net.pitsim.skywars.controllers.objects.SkywarsPerk;
import net.pitsim.skywars.game.ChestManager;
import net.pitsim.skywars.game.GameManager;
import net.pitsim.skywars.game.objects.SkywarsChest;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Chest;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class RefillReady extends SkywarsPerk {

	public RefillReady() {
		super("Refill-Ready", "refill_ready",
				Arrays.asList(1000, 2500, 5000, 10000, 25000));
	}


	@Override
	public Material getMaterial() {
		return Material.GOLD_SWORD;
	}

	@Override
	public List<String> getEquipLore(Player player) {
		List<String> lore = new ArrayList<>();
		if(SkywarsPerk.getPerkTier(player, refName) != 0) lore.add(ChatColor.translateAlternateColorCodes('&', "&7Tier: &a" + AUtil.toRoman(SkywarsPerk.getPerkTier(player, refName))));
		if(SkywarsPerk.getPerkTier(player, refName) != 0) lore.add(ChatColor.translateAlternateColorCodes('&', "&7Current: &f" + SkywarsPerk.getPerkTier(player, refName) + (SkywarsPerk.getPerkTier(player, refName) == 1 ? " Item" : " Items")));
		lore.add("");
		lore.add(ChatColor.translateAlternateColorCodes('&', "&7Each tier:"));
		lore.add(ChatColor.translateAlternateColorCodes('&', "&f1 &erandom item &7from a middle"));
		lore.add(ChatColor.translateAlternateColorCodes('&', "&echest&7, is placed into your"));
		lore.add(ChatColor.translateAlternateColorCodes('&', "&7inventory at Chest Refill."));
		return lore;
	}

	public static void onRefill() {
		for (Player player : Bukkit.getOnlinePlayers()) {
			if(!SkywarsPerk.hasPerkEquipped(player, "refill_ready")) continue;
			int tier = SkywarsPerk.getPerkTier(player, "refill_ready");
			if(tier == 0) continue;
			if(!GameManager.alivePlayers.contains(player)) continue;

			List<SkywarsChest> chests = SkywarsChest.getIslandChests(-2);
			for (int i = 0; i < tier; i++) {
				Random randChest = new Random();
				SkywarsChest pickedChest = chests.get(randChest.nextInt(chests.size()));
				Chest chestBlock = (Chest) pickedChest.location.getBlock().getState();

				int randSlot = ChestManager.getRandomEmptySlot(chestBlock);
				if(randSlot == -1) continue;

				AUtil.giveItemSafely(player, chestBlock.getInventory().getItem(randSlot));
				chestBlock.getInventory().setItem(randSlot, new ItemStack(Material.AIR));
			}
			AOutput.send(player, "&e&lREFILL READY! &7Gained &f" + tier + " &7items from middle chests.");
		}
	}

	@Override
	public List<String> getUpgradeLore(Player player) {
		return null;
	}

	@Override
	public SkywarsPerk getInstance() {
		return this;
	}
}
