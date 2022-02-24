package net.pitsim.skywars.game.skywarsperks;

import dev.kyro.arcticapi.misc.AUtil;
import net.pitsim.skywars.controllers.objects.PitPlayer;
import net.pitsim.skywars.controllers.objects.SkywarsPerk;
import net.pitsim.skywars.events.AttackEvent;
import net.pitsim.skywars.game.KillManager;
import net.pitsim.skywars.misc.Sounds;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class KillChain extends SkywarsPerk {

	public KillChain() {
		super("Kill Chain", "kill_chain",
				Arrays.asList(2500, 5000, 10000, 25000));
	}


	@Override
	public Material getMaterial() {
		return Material.REDSTONE;
	}

	@Override
	public List<String> getEquipLore(Player player) {
		List<String> lore = new ArrayList<>();
		if(SkywarsPerk.getPerkTier(player, refName) != 0) lore.add(ChatColor.translateAlternateColorCodes('&', "&7Tier: &a" + AUtil.toRoman(SkywarsPerk.getPerkTier(player, refName))));
		if(SkywarsPerk.getPerkTier(player, refName) != 0) lore.add(ChatColor.translateAlternateColorCodes('&', "&7Current: &c+" + SkywarsPerk.getPerkTier(player, refName) + "%"));
		lore.add("");
		lore.add(ChatColor.translateAlternateColorCodes('&', "&7Each tier:"));
		lore.add(ChatColor.translateAlternateColorCodes('&', "&7Deal &c+1% Damage &7per"));
		lore.add(ChatColor.translateAlternateColorCodes('&', "&7kill in a game."));
		return lore;
	}

	@EventHandler
	public void onAttack(AttackEvent.Apply attackEvent) {
		if(!SkywarsPerk.hasPerkEquipped(attackEvent.attacker, refName)) return;
		int tier = SkywarsPerk.getPerkTier(attackEvent.attacker, refName);
		if(tier == 0) return;

		double damageIncrease = KillManager.kills.get(attackEvent.attacker) * (SkywarsPerk.getPerkTier(attackEvent.attacker, refName));
		attackEvent.increasePercent += damageIncrease;
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
