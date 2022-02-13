package net.pitsim.skywars.game;

import net.pitsim.skywars.commands.FreshCommand;
import net.pitsim.skywars.controllers.EnchantManager;
import net.pitsim.skywars.controllers.objects.PitEnchant;
import net.pitsim.skywars.enums.MysticType;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public class MysticFactory {

	public static ItemStack createItem(MysticType type, int chestTier) {

		ItemStack fresh = null;

		if(type == MysticType.SWORD) fresh = FreshCommand.getFreshItem("sword");
		else if(type == MysticType.BOW) fresh = FreshCommand.getFreshItem("bow");
		else {
			double rand = Math.random();
			if(rand <= 0.2) fresh = FreshCommand.getFreshItem("blue");
			else if(rand <= 0.4) fresh = FreshCommand.getFreshItem("yellow");
			else if(rand <= 0.6) fresh = FreshCommand.getFreshItem("red");
			else if(rand <= 0.8) fresh = FreshCommand.getFreshItem("green");
			else if(rand <= 1.0) fresh = FreshCommand.getFreshItem("orange");
			else fresh = FreshCommand.getFreshItem("blue");
		}

		if(chestTier == 1) return createTier1Item(fresh);
		else if(chestTier == 2) return createTier2Item(fresh);
		else if(chestTier == 3) return createTier3Item(fresh);
		else return fresh;
	}

	public static ItemStack createTier1Item(ItemStack mystic) {
		double enchantRand = Math.random();
		int enchants = 0;

		if(enchantRand <= 0.25) enchants = 3;
		 else if(enchantRand <= 0.8) enchants = 2;
		 else if(enchantRand <= 1.0) enchants = 1;


		double tokensRand = Math.random();
		int tokens = 0;

		if(enchants == 1) {
			if(tokensRand <= 0.75) tokens = 2;
			else if(tokensRand <= 1.0) tokens = 3;
		} else if(enchants == 2) {
			if(tokensRand <= 0.05) tokens = 6;
			else if(tokensRand <= 0.1) tokens = 5;
			else if(tokens <= 0.6) tokens = 4;
			else if(tokens <= 1.0) tokens = 3;
		} else if(enchants == 3) {
			if(tokensRand <= 0.1) tokens = 7;
			else if(tokensRand <= 0.3) tokens = 6;
			else if(tokensRand <= 1.0) tokens = 5;
		}

		List<PitEnchant> itemEnchants = new ArrayList<>();
		String type = EnchantManager.getMysticType(mystic);

		for(int i = 0; i < enchants; i++) {
			List<PitEnchant> applicableEnchants = new ArrayList<>();

			double rarityRand = Math.random();
			String rarity = null;

			if(rarityRand <= 0.1) {
				rarity = "RARE";
			} else if(rarityRand <= 0.5) {
				rarity = "UNCOMMON";
			} else if(rarityRand <= 1.0) {
				rarity = "COMMON";
			}

			assert rarity != null;
			assert type != null;

			if(rarity.equals("UNCOMMON")) {
				if(type.equals("Sword")) applicableEnchants.addAll(EnchantManager.swordUncommon);
				if(type.equals("Pants")) applicableEnchants.addAll(EnchantManager.pantsUncommon);
				if(type.equals("Bow")) applicableEnchants.addAll(EnchantManager.bowUncommon);
				applicableEnchants.addAll(EnchantManager.allUncommon);
			} else if(rarity.equals("COMMON")){
				if(type.equals("Sword")) applicableEnchants.addAll(EnchantManager.swordCommon);
				if(type.equals("Pants")) applicableEnchants.addAll(EnchantManager.pantsCommon);
				if(type.equals("Bow")) applicableEnchants.addAll(EnchantManager.bowCommon);
				applicableEnchants.addAll(EnchantManager.allCommon);
			} else {
				if(type.equals("Sword")) applicableEnchants.addAll(EnchantManager.swordRare);
				if(type.equals("Pants")) applicableEnchants.addAll(EnchantManager.pantsRare);
				if(type.equals("Bow")) applicableEnchants.addAll(EnchantManager.bowRare);
				applicableEnchants.addAll(EnchantManager.allRare);
			}

			Random applicableRand = new Random();
			PitEnchant randEnchant = applicableEnchants.get(applicableRand.nextInt(applicableEnchants.size()));
			if(itemEnchants.contains(randEnchant)) {
				enchants++;
				continue;
			}
			itemEnchants.add(randEnchant);

		}

		return applyEnchants(mystic, itemEnchants, tokens);

	}

	public static ItemStack createTier2Item(ItemStack mystic) {
		double enchantRand = Math.random();
		int enchants = 0;

		if(enchantRand <= 0.25) enchants = 3;
		else if(enchantRand <= 0.5) enchants = 1;
		else if(enchantRand <= 1.0) enchants = 2;


		double tokensRand = Math.random();
		int tokens = 0;

		if(enchants == 1) {
			tokens = 3;
		} else if(enchants == 2) {
			if(tokensRand <= 0.2) tokens = 5;
			else if(tokensRand <= 0.5) tokens = 3;
			else if(tokens <= 1.0) tokens = 4;
		} else if(enchants == 3) {
			if(tokensRand <= 0.1) tokens = 7;
			else if(tokensRand <= 0.35) tokens = 6;
			else if(tokens <= 1.0) tokens = 5;
		}

		List<PitEnchant> itemEnchants = new ArrayList<>();
		String type = EnchantManager.getMysticType(mystic);

		for(int i = 0; i < enchants; i++) {
			List<PitEnchant> applicableEnchants = new ArrayList<>();

			double rarityRand = Math.random();
			String rarity = null;

			if(rarityRand <= 0.2) {
				rarity = "RARE";
			} else if(rarityRand <= 0.5) {
				rarity = "COMMON";
			} else if(rarityRand <= 1.0) {
				rarity = "UNCOMMON";
			}

			assert rarity != null;
			assert type != null;

			if(rarity.equals("UNCOMMON")) {
				if(type.equals("Sword")) applicableEnchants.addAll(EnchantManager.swordUncommon);
				if(type.equals("Pants")) applicableEnchants.addAll(EnchantManager.pantsUncommon);
				if(type.equals("Bow")) applicableEnchants.addAll(EnchantManager.bowUncommon);
				applicableEnchants.addAll(EnchantManager.allUncommon);
			} else if(rarity.equals("COMMON")){
				if(type.equals("Sword")) applicableEnchants.addAll(EnchantManager.swordCommon);
				if(type.equals("Pants")) applicableEnchants.addAll(EnchantManager.pantsCommon);
				if(type.equals("Bow")) applicableEnchants.addAll(EnchantManager.bowCommon);
				applicableEnchants.addAll(EnchantManager.allCommon);
			} else {
				if(type.equals("Sword")) applicableEnchants.addAll(EnchantManager.swordRare);
				if(type.equals("Pants")) applicableEnchants.addAll(EnchantManager.pantsRare);
				if(type.equals("Bow")) applicableEnchants.addAll(EnchantManager.bowRare);
				applicableEnchants.addAll(EnchantManager.allRare);
			}

			Random applicableRand = new Random();
			PitEnchant randEnchant = applicableEnchants.get(applicableRand.nextInt(applicableEnchants.size()));
			if(itemEnchants.contains(randEnchant)) {
				enchants++;
				continue;
			}
			itemEnchants.add(randEnchant);

		}

		return applyEnchants(mystic, itemEnchants, tokens);

	}

	public static ItemStack createTier3Item(ItemStack mystic) {
		double enchantRand = Math.random();
		int enchants = 0;

		if(enchantRand <= 0.35) enchants = 2;
		else if(enchantRand <= 1.0) enchants = 3;

		double tokensRand = Math.random();
		int tokens = 0;

		 if(enchants == 2) {
			if(tokensRand <= 0.2) tokens = 3;
			else if(tokensRand <= 0.5) tokens = 5;
			else if(tokens <= 1.0) tokens = 4;
		} else if(enchants == 3) {
			if(tokensRand <= 0.15) tokens = 7;
			else if(tokensRand <= 0.5) tokens = 6;
			else if(tokens <= 1.0) tokens = 5;
		}

		List<PitEnchant> itemEnchants = new ArrayList<>();
		String type = EnchantManager.getMysticType(mystic);

		for(int i = 0; i < enchants; i++) {
			List<PitEnchant> applicableEnchants = new ArrayList<>();

			double rarityRand = Math.random();
			String rarity = null;

			if(rarityRand <= 0.2) {
				rarity = "COMMON";
			} else if(rarityRand <= 0.55) {
				rarity = "RARE";
			} else if(rarityRand <= 1.0) {
				rarity = "UNCOMMON";
			}

			assert rarity != null;
			assert type != null;

			if(rarity.equals("UNCOMMON")) {
				if(type.equals("Sword")) applicableEnchants.addAll(EnchantManager.swordUncommon);
				if(type.equals("Pants")) applicableEnchants.addAll(EnchantManager.pantsUncommon);
				if(type.equals("Bow")) applicableEnchants.addAll(EnchantManager.bowUncommon);
				applicableEnchants.addAll(EnchantManager.allUncommon);
			} else if(rarity.equals("COMMON")){
				if(type.equals("Sword")) applicableEnchants.addAll(EnchantManager.swordCommon);
				if(type.equals("Pants")) applicableEnchants.addAll(EnchantManager.pantsCommon);
				if(type.equals("Bow")) applicableEnchants.addAll(EnchantManager.bowCommon);
				applicableEnchants.addAll(EnchantManager.allCommon);
			} else {
				if(type.equals("Sword")) applicableEnchants.addAll(EnchantManager.swordRare);
				if(type.equals("Pants")) applicableEnchants.addAll(EnchantManager.pantsRare);
				if(type.equals("Bow")) applicableEnchants.addAll(EnchantManager.bowRare);
				applicableEnchants.addAll(EnchantManager.allRare);
			}

			Random applicableRand = new Random();
			PitEnchant randEnchant = applicableEnchants.get(applicableRand.nextInt(applicableEnchants.size()));
			if(itemEnchants.contains(randEnchant)) {
				enchants++;
				continue;
			}
			itemEnchants.add(randEnchant);

		}

		return applyEnchants(mystic, itemEnchants, tokens);

	}

	public static ItemStack applyEnchants(ItemStack mystic, List<PitEnchant> enchants, int tokens) {
		ItemStack toReturn = null;
		try {
			if(enchants.size() == 1) {
				toReturn = EnchantManager.addEnchant(mystic, enchants.get(0), tokens, false);
			} else {
				ItemStack tempMystic = mystic.clone();
				List<Integer> tokenFormat = new ArrayList<>(tokenFormat(enchants.size(), tokens));

				for(PitEnchant enchant : enchants) {
					Random rand = new Random();
					int randToken = tokenFormat.get(rand.nextInt(tokenFormat.size()));

					tempMystic = EnchantManager.addEnchant(tempMystic.clone(), enchant, randToken, false);
					tokenFormat.remove((Integer) randToken);
				}
				toReturn = tempMystic;

			}

		} catch(Exception e) { e.printStackTrace(); }
		return toReturn;
	}

	public static List<Integer> tokenFormat(int enchants, int tokens) {
		if(enchants == 1) {
			if(tokens == 2) return Collections.singletonList(2);
			else if(tokens == 3) return Collections.singletonList(3);
		}
		if(enchants == 2) {
			if(tokens == 3) {
				return Arrays.asList(1, 2);
			} else if(tokens == 4) {
				double tokensRand = Math.random();
				if(tokensRand <= 0.5) return Arrays.asList(2, 2);
				else return Arrays.asList(1, 3);
			} else if(tokens == 5) {
				return Arrays.asList(3, 2);
			} else if(tokens == 6) {
				return Arrays.asList(3, 3);
			}
		} else if(enchants == 3) {
			if(tokens == 5) {
				double tokensRand = Math.random();
				if(tokensRand <= 0.5) return Arrays.asList(1, 1, 3);
				else return Arrays.asList(2, 1, 2);
			} else if(tokens == 6) {
				double tokensRand = Math.random();
				if(tokensRand <= 0.5) return Arrays.asList(2, 2, 2);
				else return Arrays.asList(1, 2, 3);
			} else if(tokens == 7) {
				double tokensRand = Math.random();
				if(tokensRand <= 0.5) return Arrays.asList(3, 3, 1);
				else return Arrays.asList(3, 2, 2);
			}
		}
		return Collections.singletonList(0);
	}
}
