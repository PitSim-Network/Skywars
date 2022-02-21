package net.pitsim.skywars.game.skywarsperks;

import net.pitsim.skywars.controllers.objects.SkywarsPerk;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class NoPerk extends SkywarsPerk {

	public NoPerk() {
		super("No Perk", "no_perk",
				Collections.emptyList());
	}


	@Override
	public Material getMaterial() {
		return Material.DIAMOND_BLOCK;
	}

	@Override
	public List<String> getEquipLore(Player player) {
		List<String> lore = new ArrayList<>();
		lore.add(ChatColor.translateAlternateColorCodes('&', "&7You do not have a Perk assigned"));
		lore.add(ChatColor.translateAlternateColorCodes('&', "&7to this slot."));
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
