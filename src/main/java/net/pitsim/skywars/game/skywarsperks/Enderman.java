package net.pitsim.skywars.game.skywarsperks;

import dev.kyro.arcticapi.misc.AOutput;
import dev.kyro.arcticapi.misc.AUtil;
import net.pitsim.skywars.controllers.Cooldown;
import net.pitsim.skywars.controllers.objects.SkywarsPerk;
import net.pitsim.skywars.enchants.Telebow;
import net.pitsim.skywars.events.KillEvent;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;

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
		if(SkywarsPerk.getPerkTier(player, refName) != 0) lore.add(ChatColor.translateAlternateColorCodes('&', "&7Current: &f-" + tier + "s"));
		lore.add("");
		lore.add(ChatColor.translateAlternateColorCodes('&', "&7Each tier:"));
		lore.add(ChatColor.translateAlternateColorCodes('&', "&7Significantly reduces the"));
		lore.add(ChatColor.translateAlternateColorCodes('&', "&7cooldown of &dRARE! &9Telebow"));
		lore.add(ChatColor.translateAlternateColorCodes('&', "&7on kill."));
		return lore;
	}

	@EventHandler(priority = EventPriority.LOW)
	public void onKill(KillEvent event) {
		Player player = event.killer;
		if(!SkywarsPerk.hasPerkEquipped(player, refName)) return;
		int tier = SkywarsPerk.getPerkTier(player, refName);
		if(tier == 0) return;

		int reduction = 20;
		if(tier == 2) reduction = 45;
		else if(tier == 3) reduction = 90;

		if(!Telebow.cooldowns.containsKey(player.getUniqueId())) return;
		if(!Telebow.cooldowns.get(player.getUniqueId()).isOnCooldown()) return;

		Cooldown cooldown = Telebow.cooldowns.get(player.getUniqueId());
		cooldown.reduceCooldown(reduction * 20);
		Telebow.modifiedCdTimes.put(player.getUniqueId(), cooldown.getTicksLeft());
		AOutput.send(player, "&5&lENDERMAN &dRARE! &9Telebow &7cooldown &f-" + reduction + "&7s.");
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
