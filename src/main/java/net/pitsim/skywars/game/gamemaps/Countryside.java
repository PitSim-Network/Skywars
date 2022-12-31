package net.pitsim.skywars.game.gamemaps;

import net.pitsim.skywars.game.MapManager;
import net.pitsim.skywars.game.objects.GameMap;
import net.pitsim.skywars.game.objects.SkywarsChest;
import org.bukkit.Location;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Countryside extends GameMap {
	public Countryside() {
		super("Countryside", "countryside", 78, 110);
	}

	@Override
	public File getSchematicFile() {
		return new File("plugins/Skywars/schematics/countryside.schematic");
	}

	@Override
	public Map<Integer, Location> getSpawnLocations() {
		Map<Integer, Location> spawnLocations = new HashMap<>();

		//North
		spawnLocations.put(1, new Location(MapManager.getWorld(), 0.5, cageHeight, -70.5, 0, 0));
		spawnLocations.put(2, new Location(MapManager.getWorld(), 36.5, cageHeight, -60.5, 0, 0));
		spawnLocations.put(3, new Location(MapManager.getWorld(), 61.5, cageHeight, -35.5, 90, 0));
//East
		spawnLocations.put(4, new Location(MapManager.getWorld(), 71.5, cageHeight, 0.5, 90, 0));
		spawnLocations.put(5, new Location(MapManager.getWorld(), 61.5, cageHeight, 36.5, 90, 0));
		spawnLocations.put(6, new Location(MapManager.getWorld(), 36.5, cageHeight, 61.5, 180, 0));
//South
		spawnLocations.put(7, new Location(MapManager.getWorld(), 0.5, cageHeight, 71.5, 180, 0));
		spawnLocations.put(8, new Location(MapManager.getWorld(), -35.5, cageHeight, 61.5, 180, 0));
		spawnLocations.put(9, new Location(MapManager.getWorld(), -60.5, cageHeight, 36.5, -90, 0));
//West
		spawnLocations.put(10, new Location(MapManager.getWorld(), -70.5, cageHeight, 0.5, -90, 0));
		spawnLocations.put(11, new Location(MapManager.getWorld(), -60.5, cageHeight, -35.5, -90, 0));
		spawnLocations.put(12, new Location(MapManager.getWorld(), -35.5, cageHeight, -60.5, 0, 0));

		return spawnLocations;
	}

	@Override
	public List<SkywarsChest> getChests() {
		new SkywarsChest(new Location(MapManager.getWorld(), 6, 66, -68), 1, 0);
		new SkywarsChest(new Location(MapManager.getWorld(), -6, 66, -82), 1, 0);
		new SkywarsChest(new Location(MapManager.getWorld(), 3, 66, -84), 1, 0);

		new SkywarsChest(new Location(MapManager.getWorld(), 39, 66, -56), 1, 1);
		new SkywarsChest(new Location(MapManager.getWorld(), 35, 66, -73), 1, 1);
		new SkywarsChest(new Location(MapManager.getWorld(), 45, 66, -71), 1, 1);

		new SkywarsChest(new Location(MapManager.getWorld(), 62, 66, -28), 1, 2);
		new SkywarsChest(new Location(MapManager.getWorld(), 67, 66, -46), 1, 2);
		new SkywarsChest(new Location(MapManager.getWorld(), 74, 66, -39), 1, 2);

		new SkywarsChest(new Location(MapManager.getWorld(), 68, 66, 6), 1, 3);
		new SkywarsChest(new Location(MapManager.getWorld(), 81, 66, -7), 1, 3);
		new SkywarsChest(new Location(MapManager.getWorld(), 84, 66, 3), 1, 3);

		new SkywarsChest(new Location(MapManager.getWorld(), 56, 66, 39), 1, 4);
		new SkywarsChest(new Location(MapManager.getWorld(), 73, 66, 35), 1, 4);
		new SkywarsChest(new Location(MapManager.getWorld(), 71, 66, 45), 1, 4);

		new SkywarsChest(new Location(MapManager.getWorld(), 29, 66, 61), 1, 5);
		new SkywarsChest(new Location(MapManager.getWorld(), 46, 66, 67), 1, 5);
		new SkywarsChest(new Location(MapManager.getWorld(), 39, 66, 74), 1, 5);

		new SkywarsChest(new Location(MapManager.getWorld(), -6, 66, 68), 1, 6);
		new SkywarsChest(new Location(MapManager.getWorld(), 7, 66, 81), 1, 6);
		new SkywarsChest(new Location(MapManager.getWorld(), -3, 66, 84), 1, 6);

		new SkywarsChest(new Location(MapManager.getWorld(), -39, 66, 56), 1, 7);
		new SkywarsChest(new Location(MapManager.getWorld(), -35, 66, 73), 1, 7);
		new SkywarsChest(new Location(MapManager.getWorld(), -45, 66, 71), 1, 7);

		new SkywarsChest(new Location(MapManager.getWorld(), -62, 66, 28), 1, 8);
		new SkywarsChest(new Location(MapManager.getWorld(), -67, 66, 46), 1, 8);
		new SkywarsChest(new Location(MapManager.getWorld(), -74, 66, 39), 1, 8);

		new SkywarsChest(new Location(MapManager.getWorld(), -68, 66, -6), 1, 9);
		new SkywarsChest(new Location(MapManager.getWorld(), -81, 66, 7), 1, 9);
		new SkywarsChest(new Location(MapManager.getWorld(), -84, 66, -3), 1, 9);

		new SkywarsChest(new Location(MapManager.getWorld(), -56, 66, -39), 1, 10);
		new SkywarsChest(new Location(MapManager.getWorld(), -73, 66, -35), 1, 10);
		new SkywarsChest(new Location(MapManager.getWorld(), -71, 66, -45), 1, 10);

		new SkywarsChest(new Location(MapManager.getWorld(), -28, 66, -62), 1, 11);
		new SkywarsChest(new Location(MapManager.getWorld(), -46, 66, -67), 1, 11);
		new SkywarsChest(new Location(MapManager.getWorld(), -39, 66, -74), 1, 11);

		new SkywarsChest(new Location(MapManager.getWorld(), 0, 66, -48), 2, -1);
		new SkywarsChest(new Location(MapManager.getWorld(), 37, 66, -33), 2, -1);
		new SkywarsChest(new Location(MapManager.getWorld(), 48, 66, 0), 2, -1);
		new SkywarsChest(new Location(MapManager.getWorld(), 34, 66, 33), 2, -1);
		new SkywarsChest(new Location(MapManager.getWorld(), 0, 66, 48), 2, -1);
		new SkywarsChest(new Location(MapManager.getWorld(), -37, 66, 33), 2, -1);
		new SkywarsChest(new Location(MapManager.getWorld(), -48, 66, 0), 2, -1);
		new SkywarsChest(new Location(MapManager.getWorld(), -39, 66, -31), 2, -1);

		new SkywarsChest(new Location(MapManager.getWorld(), 0, 68, -21), 3, -2);
		new SkywarsChest(new Location(MapManager.getWorld(), 19, 68, 0), 3, -2);
		new SkywarsChest(new Location(MapManager.getWorld(), 0, 68, 20), 3, -2);
		new SkywarsChest(new Location(MapManager.getWorld(), -18, 68, 0), 3, -2);

		return SkywarsChest.chests;
	}
}
