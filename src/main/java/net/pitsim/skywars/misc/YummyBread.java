package net.pitsim.skywars.misc;

import de.tr7zw.nbtapi.NBTItem;
import dev.kyro.arcticapi.builders.AItemStackBuilder;
import dev.kyro.arcticapi.builders.ALoreBuilder;
import dev.kyro.arcticapi.misc.AUtil;
import net.pitsim.skywars.controllers.objects.PitPlayer;
import net.pitsim.skywars.enums.NBTTag;
import net.pitsim.skywars.events.HealEvent;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;

public class YummyBread implements Listener {

	@EventHandler
	public void onEat(PlayerItemConsumeEvent event) {
		Player player = event.getPlayer();
		event.getPlayer().setFoodLevel(19);

		if(Misc.isAirOrNull(event.getItem())) return;
		NBTItem nbtItem = new NBTItem(event.getItem());
		if(nbtItem.hasKey(NBTTag.IS_YUMMY_BREAD.getRef())) {
			PitPlayer pitPlayer = PitPlayer.getPitPlayer(player);
			pitPlayer.heal(getAbsorption(), HealEvent.HealType.ABSORPTION, 20);
			Misc.applyPotionEffect(player, PotionEffectType.REGENERATION, getSeconds() * 20, getRegenerationAmplifier(), false, false);
		}
	}

	public static ItemStack getYummyBread(int amount) {
		AItemStackBuilder veryBuilder = new AItemStackBuilder(Material.BREAD, amount);
		veryBuilder.setName("&6Very yummy bread");
		ALoreBuilder veryLoreBuilder = new ALoreBuilder(
				"&7Gives &cRegeneration " + AUtil.toRoman(getRegenerationAmplifier() + 1) + " &7(" + getSeconds() + "s)",
				"&7Grants &6" + Misc.getHearts(getAbsorption()));
		veryBuilder.setLore(veryLoreBuilder);

		NBTItem nbtItem = new NBTItem(veryBuilder.getItemStack());
		nbtItem.setBoolean(NBTTag.IS_YUMMY_BREAD.getRef(), true);

		return nbtItem.getItem();
	}

	public static int getAbsorption() {
		return 4;
	}

	public static int getRegenerationAmplifier() {
		return 1;
	}

	public static int getSeconds() {
		return 5;
	}
}
