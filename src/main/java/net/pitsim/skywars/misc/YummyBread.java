package net.pitsim.skywars.misc;

import de.tr7zw.nbtapi.NBTItem;
import dev.kyro.arcticapi.builders.AItemStackBuilder;
import dev.kyro.arcticapi.builders.ALoreBuilder;
import net.pitsim.skywars.PitSim;
import net.pitsim.skywars.controllers.objects.PitPlayer;
import net.pitsim.skywars.enums.NBTTag;
import net.pitsim.skywars.events.HealEvent;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.Map;

public class YummyBread implements Listener {

	Map<Player, Integer> breadStacks = new HashMap<>();

	@EventHandler
	public void onEat(PlayerItemConsumeEvent event) {
		Player player = event.getPlayer();
		event.getPlayer().setFoodLevel(19);

		if(Misc.isAirOrNull(event.getItem())) return;
		NBTItem nbtItem = new NBTItem(event.getItem());
		if(nbtItem.hasKey(NBTTag.IS_VERY_YUMMY_BREAD.getRef())) {
			PitPlayer pitPlayer = PitPlayer.getPitPlayer(player);
			pitPlayer.heal(4, HealEvent.HealType.ABSORPTION, 20);
			Misc.applyPotionEffect(player, PotionEffectType.REGENERATION, 5 * 20, 2, false, false);
		}
	}

	@EventHandler
	public void onEat(PlayerInteractEvent event) {
		Player player = event.getPlayer();
		if(Misc.isAirOrNull(event.getItem())) return;
		NBTItem nbtItem = new NBTItem(event.getItem());
		player.setFoodLevel(19);
		if(!nbtItem.hasKey(NBTTag.IS_YUMMY_BREAD.getRef())) return;

		if(breadStacks.containsKey(player)) {
			breadStacks.put(player, breadStacks.get(player) + 1);
		} else breadStacks.put(player, 1);
		Sounds.YUMMY_BREAD.play(player);
		if(event.getItem().getAmount() == 1) {
			player.getInventory().remove(event.getItem());
		} else event.getItem().setAmount(event.getItem().getAmount() - 1);

		new BukkitRunnable() {
			@Override
			public void run() {
				if(breadStacks.containsKey(player)) {
					if(breadStacks.get(player) - 1 <= 0) breadStacks.remove(player);
					else breadStacks.put(player, breadStacks.get(player) - 1);
				}
			}
		}.runTaskLater(PitSim.INSTANCE, 20 * 20L);
		player.updateInventory();
	}


	public static ItemStack getYummyBread(int amount) {
		AItemStackBuilder veryBuilder = new AItemStackBuilder(Material.BREAD, amount);
		veryBuilder.setName("&6Very yummy bread");
		ALoreBuilder veryLoreBuilder = new ALoreBuilder("&7Heals &c" + Misc.getHearts(12), "&7Grants &6" + Misc.getHearts(4));
		veryBuilder.setLore(veryLoreBuilder);

		NBTItem nbtItem = new NBTItem(veryBuilder.getItemStack());
		nbtItem.setBoolean(NBTTag.IS_VERY_YUMMY_BREAD.getRef(), true);

		return nbtItem.getItem();
	}

//	public static ItemStack getYummyBread (int amount) {
//		AItemStackBuilder veryBuilder = new AItemStackBuilder(Material.BREAD, amount);
//		veryBuilder.setName("&6Yummy bread");
//		ALoreBuilder veryLoreBuilder = new ALoreBuilder("&7Deal &c+10% &7damage to bots", "&7for 20 seconds. (Stacking)");
//		veryBuilder.setLore(veryLoreBuilder);
//
//		NBTItem nbtItem = new NBTItem(veryBuilder.getItemStack());
//		nbtItem.setBoolean(NBTTag.IS_YUMMY_BREAD.getRef(), true);
//
//		return nbtItem.getItem();
//	}
}
