package net.pitsim.skywars.game.gamemaps;

import net.pitsim.skywars.game.MapManager;
import net.pitsim.skywars.game.objects.GameMap;
import net.pitsim.skywars.game.objects.SkywarsChest;
import org.bukkit.Location;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Oasis extends GameMap {
	public Oasis() {
		super("Oasis", "oasis", 80, 99);
	}

	@Override
	public File getSchematicFile() {
		return new File("plugins/Skywars/schematics/oasis.schematic");
	}

	@Override
	public Map<Integer, Location> getSpawnLocations() {
		Map<Integer, Location> spawnLocations = new HashMap<>();

		//North
		spawnLocations.put(1, new Location(MapManager.getWorld(), 0.5, cageHeight, -94.5, 0, 0));
		spawnLocations.put(2, new Location(MapManager.getWorld(), 26.5, cageHeight, -61.5, 90, 0));
		spawnLocations.put(3, new Location(MapManager.getWorld(), 62.5, cageHeight, -25.5, 0, 0));
//East
		spawnLocations.put(4, new Location(MapManager.getWorld(), 95.5, cageHeight, 0.5, 90, 0));
		spawnLocations.put(5, new Location(MapManager.getWorld(), 62.5, cageHeight, 26.5, 180, 0));
		spawnLocations.put(6, new Location(MapManager.getWorld(), 26.5, cageHeight, 62.5, 90, 0));
//South
		spawnLocations.put(7, new Location(MapManager.getWorld(), 0.5, cageHeight, 95.5, 180, 0));
		spawnLocations.put(8, new Location(MapManager.getWorld(), -25.5, cageHeight, 62.5, -90, 0));
		spawnLocations.put(9, new Location(MapManager.getWorld(), -61.5, cageHeight, 26.5, -180, 0));
//West
		spawnLocations.put(10, new Location(MapManager.getWorld(), -94.5, cageHeight, 0.5, -90, 0));
		spawnLocations.put(11, new Location(MapManager.getWorld(), -61.5, cageHeight, -25.5, 0, 0));
		spawnLocations.put(12, new Location(MapManager.getWorld(), -25.5, cageHeight, -61.5, -90, 0));

		return spawnLocations;
	}

	@Override
	public List<SkywarsChest> getChests() {
		new SkywarsChest(new Location(MapManager.getWorld(), 3, 70, -90), 1, 0);
		new SkywarsChest(new Location(MapManager.getWorld(), -2, 72, -100), 1, 0);
		new SkywarsChest(new Location(MapManager.getWorld(), 7, 65, -98), 1, 0);

		new SkywarsChest(new Location(MapManager.getWorld(), 21, 70, -59), 1, 1);
		new SkywarsChest(new Location(MapManager.getWorld(), 31, 72, -64), 1, 1);
		new SkywarsChest(new Location(MapManager.getWorld(), 29, 65, -55), 1, 1);

		new SkywarsChest(new Location(MapManager.getWorld(), 59, 70, -21), 1, 2);
		new SkywarsChest(new Location(MapManager.getWorld(), 64, 72, -31), 1, 2);
		new SkywarsChest(new Location(MapManager.getWorld(), 55, 65, -29), 1, 2);

		new SkywarsChest(new Location(MapManager.getWorld(), 90, 70, 3), 1, 3);
		new SkywarsChest(new Location(MapManager.getWorld(), 100, 72, -2), 1, 3);
		new SkywarsChest(new Location(MapManager.getWorld(), 98, 65, 7), 1, 3);

		new SkywarsChest(new Location(MapManager.getWorld(), 59, 70 ,21), 1, 4);
		new SkywarsChest(new Location(MapManager.getWorld(), 64, 72, 31), 1, 4);
		new SkywarsChest(new Location(MapManager.getWorld(), 55, 65, 29), 1, 4);

		new SkywarsChest(new Location(MapManager.getWorld(), 21, 70, 59), 1, 5);
		new SkywarsChest(new Location(MapManager.getWorld(), 31, 72, 64), 1, 5);
		new SkywarsChest(new Location(MapManager.getWorld(), 29, 65, 55), 1, 5);

		new SkywarsChest(new Location(MapManager.getWorld(), -3, 70, 90), 1, 6);
		new SkywarsChest(new Location(MapManager.getWorld(), 2, 72, 100), 1, 6);
		new SkywarsChest(new Location(MapManager.getWorld(), -7, 65, 98), 1, 6);

		new SkywarsChest(new Location(MapManager.getWorld(), -21, 70, 59), 1, 7);
		new SkywarsChest(new Location(MapManager.getWorld(), -31, 72, 64), 1, 7);
		new SkywarsChest(new Location(MapManager.getWorld(), -29, 65, 55), 1, 7);

		new SkywarsChest(new Location(MapManager.getWorld(), -59, 70, 21), 1, 8);
		new SkywarsChest(new Location(MapManager.getWorld(), -64, 72, 31), 1, 8);
		new SkywarsChest(new Location(MapManager.getWorld(), -55, 65, 29), 1, 8);

		new SkywarsChest(new Location(MapManager.getWorld(), -90, 70, -3), 1, 9);
		new SkywarsChest(new Location(MapManager.getWorld(), -100, 72, 2), 1, 9);
		new SkywarsChest(new Location(MapManager.getWorld(), -98, 65, -7), 1, 9);

		new SkywarsChest(new Location(MapManager.getWorld(), -59, 70, -21), 1, 10);
		new SkywarsChest(new Location(MapManager.getWorld(), -64, 72, -31), 1, 10);
		new SkywarsChest(new Location(MapManager.getWorld(), -55, 65, -29), 1, 10);

		new SkywarsChest(new Location(MapManager.getWorld(), -21, 70, -59), 1, 11);
		new SkywarsChest(new Location(MapManager.getWorld(), -31, 72, -64), 1, 11);
		new SkywarsChest(new Location(MapManager.getWorld(), -29, 65, -55), 1, 11);

		new SkywarsChest(new Location(MapManager.getWorld(), 2, 70, -62), 2, -1);
		new SkywarsChest(new Location(MapManager.getWorld(), 0, 70, -64), 2, -1);
		new SkywarsChest(new Location(MapManager.getWorld(), -2, 70, -62), 2, -1);
		new SkywarsChest(new Location(MapManager.getWorld(), 5, 70, -31), 2, -1);
		new SkywarsChest(new Location(MapManager.getWorld(), -5, 70, -31), 2, -1);

		new SkywarsChest(new Location(MapManager.getWorld(), 62, 70, 2), 2, -1);
		new SkywarsChest(new Location(MapManager.getWorld(), 64, 70, 0), 2, -1);
		new SkywarsChest(new Location(MapManager.getWorld(), 62, 70, -2), 2, -1);
		new SkywarsChest(new Location(MapManager.getWorld(), 31, 70, 5), 2, -1);
		new SkywarsChest(new Location(MapManager.getWorld(), 31, 70, -5), 2, -1);

		new SkywarsChest(new Location(MapManager.getWorld(), -2, 70, 62), 2, -1);
		new SkywarsChest(new Location(MapManager.getWorld(), 0, 70, 64), 2, -1);
		new SkywarsChest(new Location(MapManager.getWorld(), 2, 70, 62), 2, -1);
		new SkywarsChest(new Location(MapManager.getWorld(), -5, 70, 31), 2, -1);
		new SkywarsChest(new Location(MapManager.getWorld(), 5, 70, 31), 2, -1);

		new SkywarsChest(new Location(MapManager.getWorld(), -62, 70, -2), 2, -1);
		new SkywarsChest(new Location(MapManager.getWorld(), -64, 70, 0), 2, -1);
		new SkywarsChest(new Location(MapManager.getWorld(), -62, 70, 2), 2, -1);
		new SkywarsChest(new Location(MapManager.getWorld(), -31, 70, -5), 2, -1);
		new SkywarsChest(new Location(MapManager.getWorld(), -31, 70, 5), 2, -1);


		new SkywarsChest(new Location(MapManager.getWorld(), 0, 71, -15), 3, -2);
		new SkywarsChest(new Location(MapManager.getWorld(), 15, 71, 0), 3, -2);
		new SkywarsChest(new Location(MapManager.getWorld(), 0, 71, 15), 3, -2);
		new SkywarsChest(new Location(MapManager.getWorld(), -15, 71, 0), 3, -2);

		return SkywarsChest.chests;
	}
}
