package net.pitsim.skywars.game.skywarsperks;

import dev.kyro.arcticapi.misc.AUtil;
import net.pitsim.skywars.controllers.objects.SkywarsPerk;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class VoidLooter extends SkywarsPerk {

	public VoidLooter() {
		super("Void Looter", "void_looter",
				Arrays.asList(1000, 2000, 3000, 4000, 5000, 6000, 7000, 8000, 9000, 10000));
	}


	@Override
	public Material getMaterial() {
		return Material.ENDER_CHEST;
	}

	@Override
	public List<String> getEquipLore(Player player) {
		List<String> lore = new ArrayList<>();
		if(SkywarsPerk.getPerkTier(player, refName) != 0) lore.add(ChatColor.translateAlternateColorCodes('&', "&7Tier: &a" + AUtil.toRoman(SkywarsPerk.getPerkTier(player, refName))));
		if(SkywarsPerk.getPerkTier(player, refName) != 0) lore.add(ChatColor.translateAlternateColorCodes('&', "&7Current: &f" + (SkywarsPerk.getPerkTier(player, refName)) + (SkywarsPerk.getPerkTier(player, refName) == 1 ? " Item" : " Items")));
		lore.add("");
		lore.add(ChatColor.translateAlternateColorCodes('&', "&7Each tier:"));
		lore.add(ChatColor.translateAlternateColorCodes('&', "&7On void kill, &f1 random &dMystic"));
		lore.add(ChatColor.translateAlternateColorCodes('&', "&dItem &7from the victim's inventory"));
		lore.add(ChatColor.translateAlternateColorCodes('&', "&7is dropped at your feet."));
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
