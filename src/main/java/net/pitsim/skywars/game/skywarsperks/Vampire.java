package net.pitsim.skywars.game.skywarsperks;

import dev.kyro.arcticapi.misc.AUtil;
import net.pitsim.skywars.controllers.objects.SkywarsPerk;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Vampire extends SkywarsPerk {

	public Vampire() {
		super("Vampire", "vampire",
				Arrays.asList(1000, 2000, 3000, 4000, 5000, 6000, 7000, 8000, 9000, 10000));
	}


	@Override
	public Material getMaterial() {
		return Material.FERMENTED_SPIDER_EYE;
	}

	@Override
	public List<String> getEquipLore(Player player) {
		List<String> lore = new ArrayList<>();
		if(SkywarsPerk.getPerkTier(player, refName) != 0) lore.add(ChatColor.translateAlternateColorCodes('&', "&7Tier: &a" + AUtil.toRoman(SkywarsPerk.getPerkTier(player, refName))));
		lore.add("");
		lore.add(ChatColor.translateAlternateColorCodes('&', "&f" + 2 * (SkywarsPerk.getPerkTier(player, refName))
				+ "% &7chance to heal &c0.5\u2764"));
		lore.add(ChatColor.translateAlternateColorCodes('&', "&7on hit."));
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
