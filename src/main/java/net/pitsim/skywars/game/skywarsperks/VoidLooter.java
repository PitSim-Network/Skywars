package net.pitsim.skywars.game.skywarsperks;

import de.tr7zw.nbtapi.NBTItem;
import dev.kyro.arcticapi.misc.AOutput;
import dev.kyro.arcticapi.misc.AUtil;
import net.pitsim.skywars.controllers.objects.SkywarsPerk;
import net.pitsim.skywars.enums.NBTTag;
import net.pitsim.skywars.events.KillEvent;
import net.pitsim.skywars.game.MapManager;
import net.pitsim.skywars.game.objects.SkywarsChest;
import net.pitsim.skywars.misc.Misc;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

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

	@EventHandler
	public void onKill(KillEvent event) {
		Player player = event.killer;
		Player dead = event.dead;

		if(!(dead.getLocation().getY() < 20)) return;

		if(!SkywarsPerk.hasPerkEquipped(player, refName)) return;
		int tier = SkywarsPerk.getPerkTier(player, refName);
		if(tier == 0) return;

		List<ItemStack> deadItems = new ArrayList<>();
		for (ItemStack itemStack : dead.getInventory()) {
			if(Misc.isAirOrNull(itemStack)) continue;
			NBTItem nbtItem = new NBTItem(itemStack);
			if(nbtItem.hasKey(NBTTag.ITEM_UUID.getRef())) deadItems.add(itemStack.clone());
		}
		int recoveredItems = 0;

		for (int i = 0; i < tier; i++) {
			if(deadItems.size() == 0) break;
			Random randItem = new Random();
			ItemStack pickedItem = deadItems.get(randItem.nextInt(deadItems.size()));
			MapManager.getWorld().dropItemNaturally(player.getLocation(), pickedItem);
			deadItems.remove(pickedItem);
			recoveredItems++;
		}

		AOutput.send(player, "&d&lVOID LOOTER &7Recovered &f" + recoveredItems + " &7items.");

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
