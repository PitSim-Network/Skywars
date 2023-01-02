package net.pitsim.skywars.game.gamemaps;

import net.pitsim.skywars.game.MapManager;
import net.pitsim.skywars.game.objects.GameMap;
import net.pitsim.skywars.game.objects.SkywarsChest;
import org.bukkit.Location;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Frostbite extends GameMap {
	public Frostbite() {
		super("Frostbite", "frostbite", 75, 99);
	}

	@Override
	public File getSchematicFile() {
		return new File("plugins/Skywars/schematics/frostbite.schematic");
	}

	@Override
	public Map<Integer, Location> getSpawnLocations() {
		Map<Integer, Location> spawnLocations = new HashMap<>();

		//North
		spawnLocations.put(1, new Location(MapManager.getWorld(), -57.5, cageHeight, -55.5, 0, 0));
		spawnLocations.put(2, new Location(MapManager.getWorld(), -19.5, cageHeight, -77.5, -45, 0));
		spawnLocations.put(3, new Location(MapManager.getWorld(), 19.5, cageHeight, -77.5, 45, 0));
//East
		spawnLocations.put(4, new Location(MapManager.getWorld(), 58.5, cageHeight, -54.5, 0, 0));
		spawnLocations.put(5, new Location(MapManager.getWorld(), 77.5, cageHeight, -22.5, 90, 0));
		spawnLocations.put(6, new Location(MapManager.getWorld(), 77.5, cageHeight, 22.5, 90, 0));
//South
		spawnLocations.put(7, new Location(MapManager.getWorld(), 58.5, cageHeight, 56.5, 180, 0));
		spawnLocations.put(8, new Location(MapManager.getWorld(), 19.5, cageHeight, 79.5, 135, 0));
		spawnLocations.put(9, new Location(MapManager.getWorld(), -19.5, cageHeight, 78.5, -135, 0));
//West
		spawnLocations.put(10, new Location(MapManager.getWorld(), -57.5, cageHeight, 56.5, 180, 0));
		spawnLocations.put(11, new Location(MapManager.getWorld(), -76.5, cageHeight, 23.5, -90, 0));
		spawnLocations.put(12, new Location(MapManager.getWorld(), -76.5, cageHeight, -22.5, 0, 0));

		return spawnLocations;
	}

	@Override
	public List<SkywarsChest> initializeChests() {
		new SkywarsChest(new Location(MapManager.getWorld(), -61, 68, -54), 1, 0);
		new SkywarsChest(new Location(MapManager.getWorld(), -56, 70, -64), 1, 0);
		new SkywarsChest(new Location(MapManager.getWorld(), -64, 69, -67), 1, 0);

		new SkywarsChest(new Location(MapManager.getWorld(), -17, 68, -80), 1, 1);
		new SkywarsChest(new Location(MapManager.getWorld(), -27, 70, -80), 1, 1);
		new SkywarsChest(new Location(MapManager.getWorld(), -26, 69, -88), 1, 1);

		new SkywarsChest(new Location(MapManager.getWorld(), 16, 68, -80), 1, 2);
		new SkywarsChest(new Location(MapManager.getWorld(), 27, 70, -80), 1, 2);
		new SkywarsChest(new Location(MapManager.getWorld(), 26, 69, -88), 1, 2);

		new SkywarsChest(new Location(MapManager.getWorld(), 61, 68, -55), 1, 3);
		new SkywarsChest(new Location(MapManager.getWorld(), 56, 70, -64), 1, 3);
		new SkywarsChest(new Location(MapManager.getWorld(), 62, 70, -65), 1, 3);

		new SkywarsChest(new Location(MapManager.getWorld(), 77, 68, -26), 1, 4);
		new SkywarsChest(new Location(MapManager.getWorld(), 83, 70, -17), 1, 4);
		new SkywarsChest(new Location(MapManager.getWorld(), 89, 69, -21), 1, 4);

		new SkywarsChest(new Location(MapManager.getWorld(), 78, 68, 25), 1, 5);
		new SkywarsChest(new Location(MapManager.getWorld(), 83, 70, 17), 1, 5);
		new SkywarsChest(new Location(MapManager.getWorld(), 89, 69, 21), 1, 5);

		new SkywarsChest(new Location(MapManager.getWorld(), 61, 68, 54), 1, 6);
		new SkywarsChest(new Location(MapManager.getWorld(), 56, 70, 64), 1, 6);
		new SkywarsChest(new Location(MapManager.getWorld(), 63, 69, 67), 1, 6);

		new SkywarsChest(new Location(MapManager.getWorld(), 17, 68, 80), 1, 7);
		new SkywarsChest(new Location(MapManager.getWorld(), 27, 70, 80), 1, 7);
		new SkywarsChest(new Location(MapManager.getWorld(), 26, 69, 88), 1, 7);

		new SkywarsChest(new Location(MapManager.getWorld(), -16, 68, 80), 1, 8);
		new SkywarsChest(new Location(MapManager.getWorld(), -27, 70, 80), 1, 8);
		new SkywarsChest(new Location(MapManager.getWorld(), -26, 69, 88), 1, 8);

		new SkywarsChest(new Location(MapManager.getWorld(), -61, 68, 55), 1, 9);
		new SkywarsChest(new Location(MapManager.getWorld(), -56, 70, 64), 1, 9);
		new SkywarsChest(new Location(MapManager.getWorld(), -62, 70, 65), 1, 9);

		new SkywarsChest(new Location(MapManager.getWorld(), -77, 68, 26), 1, 10);
		new SkywarsChest(new Location(MapManager.getWorld(), -83, 70, 17), 1, 10);
		new SkywarsChest(new Location(MapManager.getWorld(), 26, 69, 88), 1, 10);

		new SkywarsChest(new Location(MapManager.getWorld(), -78, 68, -25), 1, 11);
		new SkywarsChest(new Location(MapManager.getWorld(), -84, 70, -17), 1, 11);
		new SkywarsChest(new Location(MapManager.getWorld(), -89, 69, -21), 1, 11);

		new SkywarsChest(new Location(MapManager.getWorld(), 0, 68, -62), 2, -1);
		new SkywarsChest(new Location(MapManager.getWorld(), 0, 68, -52), 2, -1);

		new SkywarsChest(new Location(MapManager.getWorld(), 54, 68, -31), 2, -1);
		new SkywarsChest(new Location(MapManager.getWorld(), 45, 68, -26), 2, -1);

		new SkywarsChest(new Location(MapManager.getWorld(), 54, 68, 31), 2, -1);
		new SkywarsChest(new Location(MapManager.getWorld(), 45, 68, 26), 2, -1);

		new SkywarsChest(new Location(MapManager.getWorld(), 0, 68, 62), 2, -1);
		new SkywarsChest(new Location(MapManager.getWorld(), 0, 68, 52), 2, -1);

		new SkywarsChest(new Location(MapManager.getWorld(), -54, 68, 31), 2, -1);
		new SkywarsChest(new Location(MapManager.getWorld(), -45, 68, 26), 2, -1);

		new SkywarsChest(new Location(MapManager.getWorld(), -54, 68, -31), 2, -1);
		new SkywarsChest(new Location(MapManager.getWorld(), -45, 68, -26), 2, -1);

		new SkywarsChest(new Location(MapManager.getWorld(), 0, 70, -17), 3, -2);
		new SkywarsChest(new Location(MapManager.getWorld(), 15, 70, -9), 3, -2);
		new SkywarsChest(new Location(MapManager.getWorld(), 15, 70, 9), 3, -2);
		new SkywarsChest(new Location(MapManager.getWorld(), 0, 70, 17), 3, -2);
		new SkywarsChest(new Location(MapManager.getWorld(), -15, 70, 9), 3, -2);
		new SkywarsChest(new Location(MapManager.getWorld(), -15, 70, -9), 3, -2);

		return SkywarsChest.chests;
	}
}
