package net.pitsim.skywars.game.skywarsperks;

import dev.kyro.arcticapi.misc.AUtil;
import net.pitsim.skywars.controllers.objects.SkywarsPerk;
import net.pitsim.skywars.events.AttackEvent;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Aegis extends SkywarsPerk {

	public Aegis() {
		super("Aegis", "aegis",
				Arrays.asList(1000, 2000, 5000, 7000, 10000, 15000, 20000, 25000, 30000));
	}


	@Override
	public Material getMaterial() {
		return Material.EMERALD;
	}

	@Override
	public List<String> getEquipLore(Player player) {
		List<String> lore = new ArrayList<>();
		if(SkywarsPerk.getPerkTier(player, refName) != 0) lore.add(ChatColor.translateAlternateColorCodes('&', "&7Tier: &a" + AUtil.toRoman(SkywarsPerk.getPerkTier(player, refName))));
		if(SkywarsPerk.getPerkTier(player, refName) != 0) lore.add(ChatColor.translateAlternateColorCodes('&', "&7Current: &f" + (60 - (5 * (SkywarsPerk.getPerkTier(player, refName) - 1))) + "s Cooldown"));
		lore.add("");
		lore.add(ChatColor.translateAlternateColorCodes('&', "&7Each tier:"));
		lore.add(ChatColor.translateAlternateColorCodes('&', "&7Gain a &dmagical shield &7that"));
		lore.add(ChatColor.translateAlternateColorCodes('&', "&7deflects hits with a &f60s"));
		lore.add(ChatColor.translateAlternateColorCodes('&', "&7cooldown. (&f-5s &7per tier)"));
		return lore;
	}

	@EventHandler
	public void onHit(AttackEvent.Pre event) {
//		Player player = event.defender;
//		if(!SkywarsPerk.hasPerkEquipped(player, refName)) return;
//		int tier = SkywarsPerk.getPerkTier(player, refName);
//		if(tier == 0) return;
//
//		int seconds = 65 - (5 * tier);
//
//		Cooldown cooldown = PitEnchant.getCooldown(player, seconds * 20);
//		if(!cooldown.isOnCooldown()) {
//			event.setCancelled(true);
//			cooldown.reset();
//			AOutput.send(event.attacker, PlaceholderAPI.setPlaceholders(player, "&3&lAEGIS &7%luckperms_prefix%" + player.getDisplayName() + " &7blocked your hit."));
//			Sounds.AEGIS.play(player.getLocation());
//			player.getWorld().playEffect(player.getLocation(), Effect.EXPLOSION_LARGE, Effect.EXPLOSION_LARGE.getData(), 100);
//		}
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
