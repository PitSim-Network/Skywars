package net.pitsim.skywars.game;

import net.pitsim.skywars.controllers.objects.PitEnchant;
import net.pitsim.skywars.enums.MysticType;
import net.pitsim.skywars.game.objects.GameMap;
import net.pitsim.skywars.game.objects.SkywarsChest;
import net.pitsim.skywars.misc.Misc;
import net.pitsim.skywars.misc.Sounds;
import net.pitsim.skywars.misc.YummyBread;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ChestManager {

	public static void onGameStart() {
		GameMap map = MapManager.map;


		for(SkywarsChest chest : map.getChests()) {

			Location location = chest.location;
			int tier = chest.tier;

			Block block = location.getBlock();
			Chest chestBlock = (Chest) block.getState();

			for(int i = 0; i < 6; i++) {
				int randSlot = getRandomNumber();

				for(int j = 0; j < 1; j++) {
					if(!Misc.isAirOrNull(chestBlock.getInventory().getContents()[randSlot])) {
						j--;
						randSlot = getRandomNumber();
					}
				}

				if(i == 0 || i == 1) {
					chestBlock.getInventory().setItem(randSlot, MysticFactory.createItem(MysticType.SWORD, tier));
				} if(i == 2 || i == 3) {
					chestBlock.getInventory().setItem(randSlot, MysticFactory.createItem(MysticType.BOW, tier));
				} if(i == 4 || i == 5) {
					chestBlock.getInventory().setItem(randSlot, MysticFactory.createItem(MysticType.PANTS, tier));
				}

			}


		}
		distributeArmor();
		distributeProt();
		distributeFeathers();
		distributeBlocks();
	}

	public static void distributeArmor() {
		for(int i = 0; i < 12; i++) {
			List<SkywarsChest> chests = SkywarsChest.getChests(i + 1);

			List<ItemStack> armor = new ArrayList<>();

			double helmRand = Math.random();
			if(helmRand <= 0.5) armor.add(new ItemStack(Material.IRON_HELMET));
			else armor.add(new ItemStack(Material.DIAMOND_HELMET));

			double chestRand = Math.random();
			if(chestRand <= 0.5) armor.add(new ItemStack(Material.IRON_CHESTPLATE));
			else armor.add(new ItemStack(Material.DIAMOND_CHESTPLATE));

			double bootRand = Math.random();
			if(bootRand <= 0.5) armor.add(new ItemStack(Material.IRON_BOOTS));
			else armor.add(new ItemStack(Material.DIAMOND_BOOTS));

			armor.add(new ItemStack(Material.WOOD, 16));
			armor.add(new ItemStack(Material.STONE, 16));

			armor.add(FunkyFeather.getFeather(1));

			double toolRand = Math.random();
			if(toolRand <= 0.5) armor.add(new ItemStack(Material.DIAMOND_PICKAXE));
			else armor.add(new ItemStack(Material.DIAMOND_AXE));

			for(int j = 0; j < 6; j++) {
				Random randChest = new Random();
				SkywarsChest pickedChest = chests.get(randChest.nextInt(chests.size()));
				Chest chestBlock = (Chest) pickedChest.location.getBlock().getState();

				int randSlot = getRandomNumber();
				for(int k = 0; k < 1; k++) {
					if(!Misc.isAirOrNull(chestBlock.getInventory().getContents()[randSlot])) {
						k--;
						randSlot = getRandomNumber();
					}
				}
				chestBlock.getInventory().setItem(randSlot, armor.get(0));
				armor.remove(0);
			}
		}
	}

	public static void distributeProt() {
		List<SkywarsChest> protChests = new ArrayList<>();
		protChests.addAll(SkywarsChest.getChests(-1));
		protChests.addAll(SkywarsChest.getChests(-2));
		
		List<ItemStack> prot = new ArrayList<>();
		ItemStack helmet = new ItemStack(Material.DIAMOND_HELMET);
		helmet.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
		prot.add(helmet.clone());
		prot.add(helmet.clone());

		ItemStack chestplate = new ItemStack(Material.DIAMOND_CHESTPLATE);
		chestplate.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
		prot.add(chestplate.clone());
		prot.add(chestplate.clone());

		ItemStack boots = new ItemStack(Material.DIAMOND_BOOTS);
		boots.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
		prot.add(boots.clone());
		prot.add(boots.clone());

		prot.add(YummyBread.getYummyBread(3));
		prot.add(YummyBread.getYummyBread(3));
		prot.add(YummyBread.getYummyBread(3));
		prot.add(YummyBread.getYummyBread(3));
		prot.add(YummyBread.getYummyBread(5));
		prot.add(YummyBread.getYummyBread(5));
		prot.add(YummyBread.getYummyBread(5));
		prot.add(YummyBread.getYummyBread(5));

		for(int j = 0; j < 14; j++) {
			Random randChest = new Random();
			SkywarsChest pickedChest = protChests.get(randChest.nextInt(protChests.size()));
			Chest chestBlock = (Chest) pickedChest.location.getBlock().getState();

			int randSlot = getRandomNumber();
			for(int k = 0; k < 1; k++) {
				if(!Misc.isAirOrNull(chestBlock.getInventory().getContents()[randSlot])) {
					k--;
					randSlot = getRandomNumber();
				}
			}
			chestBlock.getInventory().setItem(randSlot, prot.get(0));
			prot.remove(0);
		}
	}

	public static void distributeBlocks() {
		List<SkywarsChest> blockChests = new ArrayList<>(SkywarsChest.getChests(-1));

		for(SkywarsChest blockChest : blockChests) {
			Chest chestBlock = (Chest) blockChest.location.getBlock().getState();
			int randSlot = getRandomNumber();
			for(int k = 0; k < 1; k++) {
				if(!Misc.isAirOrNull(chestBlock.getInventory().getContents()[randSlot])) {
					k--;
					randSlot = getRandomNumber();
				}
			}
			double blockTypeRand = Math.random();
			if(blockTypeRand <= 0.5) {
				chestBlock.getInventory().setItem(randSlot, new ItemStack(Material.STONE, 64));
			} else chestBlock.getInventory().setItem(randSlot, new ItemStack(Material.WOOD, 64));

		}
	}

	public static void distributeFeathers() {
		List<SkywarsChest> featherChests = new ArrayList<>(SkywarsChest.getChests(-2));

		List<ItemStack> feathers = new ArrayList<>();
		feathers.add(FunkyFeather.getFeather(1));
		feathers.add(FunkyFeather.getFeather(1));
		feathers.add(FunkyFeather.getFeather(1));
		feathers.add(FunkyFeather.getFeather(1));
		feathers.add(FunkyFeather.getFeather(1));

		for(int j = 0; j < 5; j++) {
			Random randChest = new Random();
			SkywarsChest pickedChest = featherChests.get(randChest.nextInt(featherChests.size()));
			Chest chestBlock = (Chest) pickedChest.location.getBlock().getState();

			int randSlot = getRandomNumber();
			for(int k = 0; k < 1; k++) {
				if(!Misc.isAirOrNull(chestBlock.getInventory().getContents()[randSlot])) {
					k--;
					randSlot = getRandomNumber();
				}
			}
			chestBlock.getInventory().setItem(randSlot, feathers.get(0));
			feathers.remove(0);
		}
	}

	public static void refillChests() {
		for (Player player : Bukkit.getOnlinePlayers()) {
			Sounds.CHEST_REFILL.play(player);
		}
		GameMap map = MapManager.map;

		for (SkywarsChest chest : map.getChests()) {
			Location location = chest.location;
			Block block = location.getBlock();
			Chest chestBlock = (Chest) block.getState();
			chestBlock.getInventory().clear();
		}

		for(SkywarsChest chest : map.getChests()) {

			Location location = chest.location;
			int tier = chest.tier;

			Block block = location.getBlock();
			Chest chestBlock = (Chest) block.getState();

			for(int i = 0; i < 6; i++) {
				int randSlot = getRandomNumber();

				for(int j = 0; j < 1; j++) {
					if(!Misc.isAirOrNull(chestBlock.getInventory().getContents()[randSlot])) {
						j--;
						randSlot = getRandomNumber();
					}
				}

				if(i == 0 || i == 1) {
					chestBlock.getInventory().setItem(randSlot, MysticFactory.createItem(MysticType.SWORD, tier));
				} if(i == 2 || i == 3) {
					chestBlock.getInventory().setItem(randSlot, MysticFactory.createItem(MysticType.BOW, tier));
				} if(i == 4 || i == 5) {
					chestBlock.getInventory().setItem(randSlot, MysticFactory.createItem(MysticType.PANTS, tier));
				}

			}


		}
		distributeArmor();
		distributeProt();
		distributeFeathers();
		distributeBlocks();
	}


	public static int getRandomNumber() {
		int min = 0;
		int max = 26;
		return (int) ((Math.random() * (max - min)) + min);
	}
}
