package net.pitsim.skywars.game.skywarsperks;

import dev.kyro.arcticapi.misc.AOutput;
import dev.kyro.arcticapi.misc.AUtil;
import me.clip.placeholderapi.PlaceholderAPI;
import net.pitsim.skywars.PitSim;
import net.pitsim.skywars.controllers.objects.SkywarsPerk;
import net.pitsim.skywars.misc.Misc;
import net.pitsim.skywars.misc.Sounds;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Chest;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;

public class Locksmith extends SkywarsPerk {

	public static Map<Chest, Player> chestMap = new HashMap<>();

	public Locksmith() {
		super("Locksmith", "locksmith",
				Arrays.asList(500, 1000, 1500));
	}


	@Override
	public Material getMaterial() {
		return Material.CHEST;
	}

	@Override
	public List<String> getEquipLore(Player player) {
		List<String> lore = new ArrayList<>();
		if(SkywarsPerk.getPerkTier(player, refName) != 0) lore.add(ChatColor.translateAlternateColorCodes('&', "&7Tier: &a" + AUtil.toRoman(SkywarsPerk.getPerkTier(player, refName))));
		if(SkywarsPerk.getPerkTier(player, refName) != 0) lore.add(ChatColor.translateAlternateColorCodes('&', "&7Current: &f" + 5 * (SkywarsPerk.getPerkTier(player, refName)) + "s"));
		lore.add("");
		lore.add(ChatColor.translateAlternateColorCodes('&', "&7Each tier:"));
		lore.add(ChatColor.translateAlternateColorCodes('&', "&eChests &7you open are locked"));
		lore.add(ChatColor.translateAlternateColorCodes('&', "&7for other players for &f5s&7."));
		return lore;
	}

	@EventHandler
	public void onChestOpen(PlayerInteractEvent event) {
		Player player = event.getPlayer();
		if(!SkywarsPerk.hasPerkEquipped(player, refName)) return;
		int tier = SkywarsPerk.getPerkTier(player, refName);
		if(tier == 0) return;

		if(event.getClickedBlock() == null) return;
		if(event.getAction() != Action.RIGHT_CLICK_BLOCK) return;
		if(event.getClickedBlock().getType() != Material.CHEST) return;

		Chest chest = (Chest) event.getClickedBlock();
		if(chestMap.containsKey(chest)) return;
		chestMap.put(chest, player);

		long duration = 20 * (tier * 5L);

		new BukkitRunnable() {
			@Override
			public void run() {
				chestMap.remove(chest);
			}
		}.runTaskLater(PitSim.INSTANCE, duration);
	}

	@EventHandler
	public void onPlayerChestOpen(PlayerInteractEvent event) {
		Player player = event.getPlayer();

		if(event.getClickedBlock() == null) return;
		if(event.getAction() != Action.RIGHT_CLICK_BLOCK) return;
		if(event.getClickedBlock().getType() != Material.CHEST) return;

		Chest chest = (Chest) event.getClickedBlock();
		if(!(chestMap.containsKey(chest))) return;
		if(player == chestMap.get(chest)) return;
		Player chestPlayer = chestMap.get(chest);
		event.setCancelled(true);
		Sounds.ERROR.play(player);
		AOutput.send(player, "&c&lLOCKSMITH &7This chest has been locked by " + PlaceholderAPI.setPlaceholders(chestPlayer, "%luckperms_prefix%" + chestPlayer.getDisplayName()));
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
