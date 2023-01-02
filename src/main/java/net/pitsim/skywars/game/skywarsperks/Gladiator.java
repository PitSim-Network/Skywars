package net.pitsim.skywars.game.skywarsperks;

import dev.kyro.arcticapi.misc.AUtil;
import net.pitsim.skywars.controllers.objects.SkywarsPerk;
import net.pitsim.skywars.events.AttackEvent;
import net.pitsim.skywars.misc.Misc;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Gladiator extends SkywarsPerk {

	public Gladiator() {
		super("Gladiator", "gladiator",
				Arrays.asList(500, 750, 1000, 5000, 10000));
	}

	@Override
	public Material getMaterial() {
		return Material.BONE;
	}

	@Override
	public List<String> getEquipLore(Player player) {
		List<String> lore = new ArrayList<>();
		if(SkywarsPerk.getPerkTier(player, refName) != 0) lore.add(ChatColor.translateAlternateColorCodes('&', "&7Tier: &a" + AUtil.toRoman(SkywarsPerk.getPerkTier(player, refName))));
		if(SkywarsPerk.getPerkTier(player, refName) != 0) lore.add(ChatColor.translateAlternateColorCodes('&', "&7Current: &9-" + SkywarsPerk.getPerkTier(player, refName) + "%"));
		lore.add("");
		lore.add(ChatColor.translateAlternateColorCodes('&', "&7Each tier:"));
		lore.add(ChatColor.translateAlternateColorCodes('&', "&7Receive &9-1% Damage &7per"));
		lore.add(ChatColor.translateAlternateColorCodes('&', "&7player within &f12 Blocks&7."));
		return lore;
	}

	@EventHandler
	public void onHit(AttackEvent.Apply attackEvent) {
		int tier = SkywarsPerk.getPerkTier(attackEvent.defender, refName);
		if(tier == 0 || !SkywarsPerk.hasPerkEquipped(attackEvent.defender, refName)) return;

		int nearbyPlayers = 0;
		for(Entity nearbyEntity : attackEvent.defender.getNearbyEntities(12, 12, 12)) {
			if(!(nearbyEntity instanceof Player)) continue;
			nearbyPlayers++;
		}

		int reduction = tier * nearbyPlayers;
		attackEvent.multipliers.add(Misc.getReductionMultiplier(reduction));
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
