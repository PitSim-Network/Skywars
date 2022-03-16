package net.pitsim.skywars.game.objects;

import org.bukkit.Location;

import java.util.ArrayList;
import java.util.List;

public class SkywarsChest {

	public static List<SkywarsChest> chests = new ArrayList<>();

	public Location location;
	public int tier;
	public int islandNum;

	public SkywarsChest(Location location, int tier, int islandNum) {
		this.location = location;
		this.islandNum = islandNum;
		this.tier = tier;
		chests.add(this);
	}

	public static List<SkywarsChest> getChests(int islandNum) {
		List<SkywarsChest> isChests = new ArrayList<>();
		for(SkywarsChest chest : chests) {
			if(chest.islandNum == islandNum) isChests.add(chest);
		}
		return isChests;
	}

	public static SkywarsChest getChest(Location location) {
		for (SkywarsChest chest : chests) {
			if(location.getBlockX() == chest.location.getBlockX() && location.getBlockY() == chest.location.getBlockY()) return chest;
		}
		return null;
	}
}
