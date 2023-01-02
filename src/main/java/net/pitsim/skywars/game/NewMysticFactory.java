package net.pitsim.skywars.game;

import net.pitsim.skywars.commands.FreshCommand;
import net.pitsim.skywars.controllers.EnchantManager;
import net.pitsim.skywars.controllers.objects.PitEnchant;
import net.pitsim.skywars.enums.EnchantRarity;
import net.pitsim.skywars.enums.MysticType;
import net.pitsim.skywars.enums.PantColor;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.Random;

public class NewMysticFactory {
	public static ItemStack getFresh(MysticType type) {
		if(type == MysticType.SWORD) return FreshCommand.getFreshItem(MysticType.SWORD, null);
		else if(type == MysticType.BOW) return FreshCommand.getFreshItem(MysticType.BOW, null);
		else if(type == MysticType.PANTS) return FreshCommand.getFreshItem(MysticType.PANTS, PantColor.getNormalRandom());

		throw new RuntimeException();
	}

	public static EnchantRarity getWeightedRarity(double commonChance, double uncommonChance, double rareChance) {
		int commonWeight = (int) (commonChance * 10);
		int uncommonWeight = (int) (uncommonChance * 10);
		int rareWeight = (int) (rareChance * 10);

		if(commonWeight + uncommonWeight + rareWeight != 1000) throw new RuntimeException("Enchant chances must add to 100");

		int randomInt = new Random().nextInt(1001);
		if(randomInt < commonWeight) return EnchantRarity.COMMON;
		randomInt -= commonWeight;
		return randomInt < uncommonWeight ? EnchantRarity.UNCOMMON : EnchantRarity.RARE;
	}

	public static PitEnchant getRandomEnchant(MysticType mysticType) {
		List<PitEnchant> applicableEnchants = EnchantManager.enchantMap.get(mysticType);
		return applicableEnchants.get(new Random().nextInt(applicableEnchants.size()));
	}
}
