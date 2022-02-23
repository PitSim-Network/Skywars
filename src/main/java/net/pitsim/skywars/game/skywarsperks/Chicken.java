package net.pitsim.skywars.game.skywarsperks;

import dev.kyro.arcticapi.misc.AUtil;
import net.pitsim.skywars.controllers.objects.SkywarsPerk;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Chicken extends SkywarsPerk {

	public Chicken() {
		super("Chicken", "chicken",
				Arrays.asList(500, 1000, 2500, 4000, 5000, 6000, 7500, 10000, 15000, 25000));
	}


	@Override
	public Material getMaterial() {
		return Material.FEATHER;
	}

	@Override
	public List<String> getEquipLore(Player player) {
		List<String> lore = new ArrayList<>();
		if(SkywarsPerk.getPerkTier(player, refName) != 0) lore.add(ChatColor.translateAlternateColorCodes('&', "&7Tier: &a" + AUtil.toRoman(SkywarsPerk.getPerkTier(player, refName))));
		if(SkywarsPerk.getPerkTier(player, refName) != 0) lore.add(ChatColor.translateAlternateColorCodes('&', "&7Current: &f" + 5 * SkywarsPerk.getPerkTier(player, refName) + "%"));
		lore.add("");
		lore.add(ChatColor.translateAlternateColorCodes('&', "&7Each tier:"));
		lore.add(ChatColor.translateAlternateColorCodes('&', "&f5% &7chance to spawn with"));
		lore.add(ChatColor.translateAlternateColorCodes('&', "&7a &3Funky Feather &7on game"));
		lore.add(ChatColor.translateAlternateColorCodes('&', "&7start."));
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
