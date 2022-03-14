package net.pitsim.skywars.game.skywarsperks;

import dev.kyro.arcticapi.misc.AOutput;
import dev.kyro.arcticapi.misc.AUtil;
import net.pitsim.skywars.controllers.objects.PitPlayer;
import net.pitsim.skywars.controllers.objects.SkywarsPerk;
import net.pitsim.skywars.events.AttackEvent;
import net.pitsim.skywars.events.KillEvent;
import net.pitsim.skywars.game.GameManager;
import net.pitsim.skywars.misc.Sounds;
import net.pitsim.skywars.misc.YummyBread;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ComfortFood extends SkywarsPerk {

	public ComfortFood() {
		super("Comfort Food", "comfort_food",
				Arrays.asList(500, 1000, 2500, 5000, 10000));
	}


	@Override
	public Material getMaterial() {
		return Material.BREAD;
	}

	@Override
	public List<String> getEquipLore(Player player) {
		List<String> lore = new ArrayList<>();
		if(SkywarsPerk.getPerkTier(player, refName) != 0) lore.add(ChatColor.translateAlternateColorCodes('&', "&7Tier: &a" + AUtil.toRoman(SkywarsPerk.getPerkTier(player, refName))));
		if(SkywarsPerk.getPerkTier(player, refName) != 0) lore.add(ChatColor.translateAlternateColorCodes('&', "&7Current: &f" + 5 * (SkywarsPerk.getPerkTier(player, refName)) + "%"));
		lore.add("");
		lore.add(ChatColor.translateAlternateColorCodes('&', "&7Each tier:"));
		lore.add(ChatColor.translateAlternateColorCodes('&', "&f5% &7chance of gaining a"));
		lore.add(ChatColor.translateAlternateColorCodes('&', "&6Yummy Bread &7when another"));
		lore.add(ChatColor.translateAlternateColorCodes('&', "&7player gets a kill."));
		return lore;
	}

	@EventHandler(priority = EventPriority.LOW)
	public void onKill(KillEvent event) {
		for (Player alivePlayer : GameManager.alivePlayers) {
			if(alivePlayer == event.killer) continue;

			if(!SkywarsPerk.hasPerkEquipped(alivePlayer, refName)) continue;
			int tier = SkywarsPerk.getPerkTier(alivePlayer, refName);
			if(tier == 0) continue;

			double chance = tier * 0.05;
			double rand = Math.random();
			if(rand > chance) continue;

			AUtil.giveItemSafely(alivePlayer, YummyBread.getYummyBread(1));
			AOutput.send(alivePlayer, "&6&lCOMFORT FOOD &7Gained a &6Yummy Bread&7.");
		}
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
