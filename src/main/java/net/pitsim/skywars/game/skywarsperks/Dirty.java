package net.pitsim.skywars.game.skywarsperks;

import dev.kyro.arcticapi.misc.AUtil;
import net.pitsim.skywars.controllers.objects.SkywarsPerk;
import net.pitsim.skywars.events.KillEvent;
import net.pitsim.skywars.misc.Misc;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Dirty extends SkywarsPerk {

	public Dirty() {
		super("Dirty", "dirty",
				Arrays.asList(500, 1000, 2500, 5000, 10000));
	}


	@Override
	public Material getMaterial() {
		return Material.DIRT;
	}

	@Override
	public List<String> getEquipLore(Player player) {
		List<String> lore = new ArrayList<>();
		if(SkywarsPerk.getPerkTier(player, refName) != 0) lore.add(ChatColor.translateAlternateColorCodes('&', "&7Tier: &a" + AUtil.toRoman(SkywarsPerk.getPerkTier(player, refName))));
		if(SkywarsPerk.getPerkTier(player, refName) != 0) lore.add(ChatColor.translateAlternateColorCodes('&', "&7Current: &f" + 0.5 * (SkywarsPerk.getPerkTier(player, refName)) + "s"));
		lore.add("");
		lore.add(ChatColor.translateAlternateColorCodes('&', "&7Each tier:"));
		lore.add(ChatColor.translateAlternateColorCodes('&', "&7Gain &f0.5s &7of &9Resistance II"));
		lore.add(ChatColor.translateAlternateColorCodes('&', "&7on kill."));
		return lore;
	}

	@EventHandler
	public void onKill(KillEvent killEvent) {
		if(!SkywarsPerk.hasPerkEquipped(killEvent.killer, refName)) return;
		int tier = SkywarsPerk.getPerkTier(killEvent.killer, refName);
		if(tier == 0) return;

		int duration = 10 * tier;

		Misc.applyPotionEffect(killEvent.killer, PotionEffectType.DAMAGE_RESISTANCE, duration, 1, false, false);
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
