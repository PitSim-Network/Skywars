package net.pitsim.skywars.game;

import dev.kyro.arcticapi.misc.AOutput;
import net.pitsim.skywars.enums.MysticType;
import net.pitsim.skywars.game.objects.GameMap;
import net.pitsim.skywars.game.objects.SkywarsChest;
import net.pitsim.skywars.game.skywarsperks.RefillReady;
import net.pitsim.skywars.misc.Misc;
import net.pitsim.skywars.misc.Sounds;
import net.pitsim.skywars.misc.YummyBread;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class ChestManager {
	public static ItemStack compass;

	static {
		compass = new ItemStack(Material.COMPASS);
		ItemMeta meta = compass.getItemMeta();
		List<String> compassLore = new ArrayList<>();
		meta.setDisplayName(ChatColor.GREEN + "Player Tracker");
		compassLore.add(ChatColor.GRAY + "Displays the location of");
		compassLore.add(ChatColor.GRAY + "the nearest player.");
		meta.setLore(compassLore);
		compass.setItemMeta(meta);
	}

	public static void onGameStart() {
		CompassManager.onGameStart();

		distributeMystics();
		distributeSpawnEssentials();
		distributeProt();
		distributeFeathers();
		distributeBlocks();
		distributeCompasses();
	}

	public static void refillChests() {
		distributeMystics();
		distributeSpawnEssentials();
		distributeProt();
		distributeFeathers();
		distributeBlocks();
		distributeCompasses();
		RefillReady.onRefill();

		for(Player player : Bukkit.getOnlinePlayers()) Sounds.CHEST_REFILL.play(player);
		AOutput.broadcast("&aChests refilled!");
	}

	public static void distributeMystics() {
		GoldManager.openedChests.clear();
		GameMap map = MapManager.map;

		for(SkywarsChest chest : map.getChests()) {
			Location location = chest.location;
			Block block = location.getBlock();
			Chest chestBlock = (Chest) block.getState();
			for(int i = 0; i < chestBlock.getInventory().getSize(); i++) {
				chestBlock.getInventory().setItem(i, new ItemStack(Material.AIR));
			}
		}

		for(SkywarsChest chest : map.getChests()) {

			Location location = chest.location;
			int tier = chest.tier;

			Block block = location.getBlock();
			Chest chestBlock = (Chest) block.getState();

			if(Misc.isFull(chestBlock)) continue;

			for(int i = 0; i < 6; i++) {
				if(Misc.isFull(chestBlock)) continue;
				int randSlot = getRandomSlot(chestBlock);

				if(i == 0 || i == 1) {
					chestBlock.getInventory().setItem(randSlot, MysticFactory.createItem(MysticType.SWORD, tier));
				}
				if(i == 2 || i == 3) {
					chestBlock.getInventory().setItem(randSlot, MysticFactory.createItem(MysticType.BOW, tier));
				}
				if(i == 4 || i == 5) {
					chestBlock.getInventory().setItem(randSlot, MysticFactory.createItem(MysticType.PANTS, tier));
				}
			}
		}
	}

	public static void distributeSpawnEssentials() {
		for(int i = 0; i < 12; i++) {
			List<SkywarsChest> chests = SkywarsChest.getIslandChests(i + 1);

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

			distributeItems(chests, armor);
		}
	}

	public static void distributeItems(List<SkywarsChest> chests, List<ItemStack> items) {
		for(int i = 0; i < items.size(); i++) {
			Random randChest = new Random();
			SkywarsChest pickedChest = chests.get(randChest.nextInt(chests.size()));
			Chest chestBlock = (Chest) pickedChest.location.getBlock().getState();

			if(Misc.isFull(chestBlock)) continue;
			int randSlot = getRandomSlot(chestBlock);

			ItemStack itemStack = items.get(i);
			chestBlock.getInventory().setItem(randSlot, itemStack);
		}
	}

	public static void distributeProt() {
		List<SkywarsChest> chests = new ArrayList<>();
		chests.addAll(SkywarsChest.getIslandChests(-1));
		chests.addAll(SkywarsChest.getIslandChests(-2));

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

		distributeItems(chests, prot);
	}

	public static void distributeBlocks() {
		List<SkywarsChest> chests = new ArrayList<>(SkywarsChest.getIslandChests(-1));

		for(SkywarsChest blockChest : chests) {
			Chest chestBlock = (Chest) blockChest.location.getBlock().getState();

			if(Misc.isFull(chestBlock)) continue;
			int randSlot = getRandomSlot(chestBlock);

			double blockRand = Math.random();
			if(blockRand <= 0.5) {
				chestBlock.getInventory().setItem(randSlot, new ItemStack(Material.STONE, 64));
			} else {
				chestBlock.getInventory().setItem(randSlot, new ItemStack(Material.WOOD, 64));
			}
		}
	}

	public static void distributeFeathers() {
		List<SkywarsChest> chests = new ArrayList<>(SkywarsChest.getIslandChests(-2));

		List<ItemStack> feathers = new ArrayList<>();
		feathers.add(FunkyFeather.getFeather(1));
		feathers.add(FunkyFeather.getFeather(1));
		feathers.add(FunkyFeather.getFeather(1));
		feathers.add(FunkyFeather.getFeather(1));
		feathers.add(FunkyFeather.getFeather(1));

		distributeItems(chests, feathers);
	}

	public static void distributeCompasses() {
		for(SkywarsChest chest : SkywarsChest.chests) {
			if(chest.tier != 3) continue;
			if(Math.random() < 0.5) continue;

			Block block = chest.location.getBlock();
			Chest chestBlock = (Chest) block.getState();

			if(Misc.isFull(chestBlock)) continue;
			int randSlot = getRandomSlot(chestBlock);

			chestBlock.getInventory().setItem(randSlot, compass);
		}
	}

	public static int getRandomSlot(Chest chest) {
		List<Integer> emptySlots = new ArrayList<>();
		for(int i = 0; i < 27; i++) {
			if(!Misc.isAirOrNull(chest.getInventory().getItem(i))) continue;
			emptySlots.add(i);
		}
		if(emptySlots.isEmpty()) throw new RuntimeException();
		Collections.shuffle(emptySlots);
		return emptySlots.get(0);
	}
}
