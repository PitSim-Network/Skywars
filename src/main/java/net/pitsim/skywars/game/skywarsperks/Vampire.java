package net.pitsim.skywars.game.skywarsperks;

import dev.kyro.arcticapi.misc.AOutput;
import dev.kyro.arcticapi.misc.AUtil;
import net.pitsim.skywars.controllers.objects.PitPlayer;
import net.pitsim.skywars.controllers.objects.SkywarsPerk;
import net.pitsim.skywars.events.AttackEvent;
import net.pitsim.skywars.misc.Sounds;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Vampire extends SkywarsPerk {

	public Vampire() {
		super("Vampire", "vampire",
				Arrays.asList(500, 1500, 3000, 4000, 5000, 6000, 7500, 10000, 15000, 25000));
	}


	@Override
	public Material getMaterial() {
		return Material.FERMENTED_SPIDER_EYE;
	}

	@Override
	public List<String> getEquipLore(Player player) {
		List<String> lore = new ArrayList<>();
		if(SkywarsPerk.getPerkTier(player, refName) != 0) lore.add(ChatColor.translateAlternateColorCodes('&', "&7Tier: &a" + AUtil.toRoman(SkywarsPerk.getPerkTier(player, refName))));
		if(SkywarsPerk.getPerkTier(player, refName) != 0) lore.add(ChatColor.translateAlternateColorCodes('&', "&7Current: &f" + 3 * (SkywarsPerk.getPerkTier(player, refName)) + "%"));
		lore.add("");
		lore.add(ChatColor.translateAlternateColorCodes('&', "&7Each tier:"));
		lore.add(ChatColor.translateAlternateColorCodes('&', "&f3% &7chance to heal &c0.5\u2764"));
		lore.add(ChatColor.translateAlternateColorCodes('&', "&7on hit."));
		return lore;
	}

	@EventHandler
	public void onAttack(AttackEvent.Apply attackEvent) {
		if(!SkywarsPerk.hasPerkEquipped(attackEvent.attacker, refName)) return;
		int tier = SkywarsPerk.getPerkTier(attackEvent.attacker, refName);
		if(tier == 0) return;

		double chance = tier * 0.03;
		double rand = Math.random();
		if(rand > chance) return;

		Sounds.SURVIVOR_HEAL.play(attackEvent.attacker);
		PitPlayer pitAttacker = PitPlayer.getPitPlayer(attackEvent.attacker);

		int healing = 1;
		pitAttacker.heal(healing);
		AOutput.send(attackEvent.attacker, "&c&lVAMPIRE &7+&c0.5\u2764&7.");
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
