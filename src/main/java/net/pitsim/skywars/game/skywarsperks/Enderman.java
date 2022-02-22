package net.pitsim.skywars.game.skywarsperks;

import dev.kyro.arcticapi.misc.AUtil;
import net.pitsim.skywars.controllers.objects.SkywarsPerk;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Enderman extends SkywarsPerk {

	public Enderman() {
		super("Enderman", "enderman",
				Arrays.asList(5000, 10000, 25000));
	}


	@Override
	public Material getMaterial() {
		return Material.ENDER_PEARL;
	}

	@Override
	public List<String> getEquipLore(Player player) {
		List<String> lore = new ArrayList<>();
		if(SkywarsPerk.getPerkTier(player, refName) != 0) lore.add(ChatColor.translateAlternateColorCodes('&', "&7Tier: &a" + AUtil.toRoman(SkywarsPerk.getPerkTier(player, refName))));
		int tier = SkywarsPerk.getPerkTier(player, refName);
		if(tier == 1) tier = 20;
		else if(tier == 2) tier = 45;
		else tier = 90;
		if(SkywarsPerk.getPerkTier(player, refName) != 0) lore.add(ChatColor.translateAlternateColorCodes('&', "&7Current: " + tier + "s"));
		lore.add("");
		lore.add(ChatColor.translateAlternateColorCodes('&', "&7Each tier:"));
		lore.add(ChatColor.translateAlternateColorCodes('&', "&7Significantly reduces the"));
		lore.add(ChatColor.translateAlternateColorCodes('&', "&7cooldown &dRARE! &9Telebow"));
		lore.add(ChatColor.translateAlternateColorCodes('&', "&7on kill."));
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
