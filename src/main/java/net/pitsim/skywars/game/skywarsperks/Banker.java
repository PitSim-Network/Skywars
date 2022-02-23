package net.pitsim.skywars.game.skywarsperks;

import dev.kyro.arcticapi.misc.AUtil;
import net.pitsim.skywars.controllers.objects.SkywarsPerk;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Banker extends SkywarsPerk {

	public Banker() {
		super("Banker", "banker",
				Arrays.asList(1500, 3000, 5000, 10000, 15000));
	}


	@Override
	public Material getMaterial() {
		return Material.GOLD_INGOT;
	}

	@Override
	public List<String> getEquipLore(Player player) {
		List<String> lore = new ArrayList<>();
		if(SkywarsPerk.getPerkTier(player, refName) != 0) lore.add(ChatColor.translateAlternateColorCodes('&', "&7Tier: &a" + AUtil.toRoman(SkywarsPerk.getPerkTier(player, refName))));
		if(SkywarsPerk.getPerkTier(player, refName) != 0) lore.add(ChatColor.translateAlternateColorCodes('&', "&7Current: &f" + SkywarsPerk.getPerkTier(player, refName) + (SkywarsPerk.getPerkTier(player, refName) == 1 ? " Chest" : " Chests")));
		lore.add("");
		lore.add(ChatColor.translateAlternateColorCodes('&', "&7Each tier:"));
		lore.add(ChatColor.translateAlternateColorCodes('&', "&7Earn &f2x &6Gold &7from the"));
		lore.add(ChatColor.translateAlternateColorCodes('&', "&7first &echests &7you open each"));
		lore.add(ChatColor.translateAlternateColorCodes('&', "&7game. (&f+1 &eChest &7per tier)"));
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
