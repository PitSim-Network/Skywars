package net.pitsim.skywars.game.skywarsperks;

import dev.kyro.arcticapi.misc.AUtil;
import net.pitsim.skywars.controllers.objects.SkywarsPerk;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

	@Override
	public List<String> getUpgradeLore(Player player) {
		return null;
	}

	@Override
	public SkywarsPerk getInstance() {
		return this;
	}
}
