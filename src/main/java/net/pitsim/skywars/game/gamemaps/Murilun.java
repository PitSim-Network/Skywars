package net.pitsim.skywars.game.gamemaps;

import net.pitsim.skywars.game.MapManager;
import net.pitsim.skywars.game.objects.SkywarsChest;
import net.pitsim.skywars.game.objects.GameMap;
import org.bukkit.Location;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Murilun extends GameMap {
	public Murilun() {
		super("Murilun", "murilun", 73);
	}

	@Override
	public File getSchematicFile() {
		return new File("plugins/Skywars/schematics/murilun.schematic");
	}

	@Override
	public Map<Integer, Location> getSpawnLocations() {
		Map<Integer, Location> spawnLocations = new HashMap<>();
//North
		spawnLocations.put(1, new Location(MapManager.getWorld(), -18.5, cageHeight, -49.5, -90, 0));
		spawnLocations.put(2, new Location(MapManager.getWorld(), -0.5, cageHeight, -73.5, 0, 0));
		spawnLocations.put(3, new Location(MapManager.getWorld(), 19.5, cageHeight, -51.5, 90, 0));
//East
		spawnLocations.put(4, new Location(MapManager.getWorld(), 50.5, cageHeight, -18.5, 0, 0));
		spawnLocations.put(5, new Location(MapManager.getWorld(), 74.5, cageHeight, -0.5, 90, 0));
		spawnLocations.put(6, new Location(MapManager.getWorld(), 52.5, cageHeight, 19.5, 180, 0));
//South
		spawnLocations.put(7, new Location(MapManager.getWorld(), 19.5, cageHeight, 50.5, 90, 0));
		spawnLocations.put(8, new Location(MapManager.getWorld(), 1.5, cageHeight, 74.5, 180, 0));
		spawnLocations.put(9, new Location(MapManager.getWorld(), -18.5, cageHeight, 52.5, -90, 0));
//West
		spawnLocations.put(10, new Location(MapManager.getWorld(), -49.5, cageHeight, 19.5, 180, 0));
		spawnLocations.put(11, new Location(MapManager.getWorld(), -73.5, cageHeight, 1.5, -90, 0));
		spawnLocations.put(12, new Location(MapManager.getWorld(), -51.5, cageHeight, -18.5, 0, 0));

		return spawnLocations;
	}

	@Override
	public List<SkywarsChest> getChests() {
		Map<Location, Integer> chests = new HashMap<>();

		new SkywarsChest(new Location(MapManager.getWorld(), -29, 68, -50), 1, 1);
		new SkywarsChest(new Location(MapManager.getWorld(), -27, 72, -49), 1, 1);
		new SkywarsChest(new Location(MapManager.getWorld(), -28, 63, -49), 1, 1);

		new SkywarsChest(new Location(MapManager.getWorld(), -1, 68, -84), 1, 2);
		new SkywarsChest(new Location(MapManager.getWorld(), -2, 72, -82), 1, 2);
		new SkywarsChest(new Location(MapManager.getWorld(), -2, 63, -83), 1, 2);

		new SkywarsChest(new Location(MapManager.getWorld(), 29, 68, -52), 1, 3);
		new SkywarsChest(new Location(MapManager.getWorld(), 27, 72, -53), 1, 3);
		new SkywarsChest(new Location(MapManager.getWorld(), 28, 63, -53), 1, 3);

		new SkywarsChest(new Location(MapManager.getWorld(), 1, 67, -55), 2, -1);
		new SkywarsChest(new Location(MapManager.getWorld(), 1, 60, -49), 2, -1);
		new SkywarsChest(new Location(MapManager.getWorld(), 3, 61, -40), 2, -1);



		new SkywarsChest(new Location(MapManager.getWorld(), 50, 68, -29), 1, 4);
		new SkywarsChest(new Location(MapManager.getWorld(), 49, 72, -27), 1, 4);
		new SkywarsChest(new Location(MapManager.getWorld(), 49, 63, -28), 1, 4);

		new SkywarsChest(new Location(MapManager.getWorld(), 84, 68, -1), 1, 5);
		new SkywarsChest(new Location(MapManager.getWorld(), 82, 72, -2), 1, 5);
		new SkywarsChest(new Location(MapManager.getWorld(), 83, 63, -2), 1, 5);

		new SkywarsChest(new Location(MapManager.getWorld(), 52, 68, 29), 1, 6);
		new SkywarsChest(new Location(MapManager.getWorld(), 53, 72, 27), 1, 6);
		new SkywarsChest(new Location(MapManager.getWorld(), 53, 63, 28), 1, 6);

		new SkywarsChest(new Location(MapManager.getWorld(), 55, 67, 1), 2, -1);
		new SkywarsChest(new Location(MapManager.getWorld(), 49, 60, 1), 2, -1);
		new SkywarsChest(new Location(MapManager.getWorld(), 40, 61, 3), 2, -1);



		new SkywarsChest(new Location(MapManager.getWorld(), 29, 68, 50), 1, 7);
		new SkywarsChest(new Location(MapManager.getWorld(), 27, 72, 49), 1, 7);
		new SkywarsChest(new Location(MapManager.getWorld(), 28, 63, 49), 1, 7);

		new SkywarsChest(new Location(MapManager.getWorld(), 1, 68, 84), 1, 8);
		new SkywarsChest(new Location(MapManager.getWorld(), 2, 72, 82), 1, 8);
		new SkywarsChest(new Location(MapManager.getWorld(), 2, 63, 83), 1, 8);

		new SkywarsChest(new Location(MapManager.getWorld(), -29, 68, 52), 1, 9);
		new SkywarsChest(new Location(MapManager.getWorld(), -27, 72, 53), 1, 9);
		new SkywarsChest(new Location(MapManager.getWorld(), -28, 63, 53), 1, 9);

		new SkywarsChest(new Location(MapManager.getWorld(), -1, 67, 55), 2,  -1);
		new SkywarsChest(new Location(MapManager.getWorld(), -1, 60, 49), 2, -1);
		new SkywarsChest(new Location(MapManager.getWorld(), -3, 61, 40), 2, -1);



		new SkywarsChest(new Location(MapManager.getWorld(), -50, 68, 29), 1, 10);
		new SkywarsChest(new Location(MapManager.getWorld(), -49, 72, 27), 1, 10);
		new SkywarsChest(new Location(MapManager.getWorld(), -49, 63, 28), 1, 10);

		new SkywarsChest(new Location(MapManager.getWorld(), -84, 68, 1), 1, 11);
		new SkywarsChest(new Location(MapManager.getWorld(), -82, 72, 2), 1, 11);
		new SkywarsChest(new Location(MapManager.getWorld(), -83, 63, 2), 1, 11);

		new SkywarsChest(new Location(MapManager.getWorld(), -52, 68, -29), 1, 12);
		new SkywarsChest(new Location(MapManager.getWorld(), -53, 72, -27), 1, 12);
		new SkywarsChest(new Location(MapManager.getWorld(), -53, 63, -28), 1, 12);

		new SkywarsChest(new Location(MapManager.getWorld(), -55, 67, -1), 2, -1);
		new SkywarsChest(new Location(MapManager.getWorld(), -49, 60, -1), 2, -1);
		new SkywarsChest(new Location(MapManager.getWorld(), -40, 61, -3), 2, -1);



		new SkywarsChest(new Location(MapManager.getWorld(), 6, 63, -11), 3, -2);
		new SkywarsChest(new Location(MapManager.getWorld(), -2, 63, 11), 3, -2);
		new SkywarsChest(new Location(MapManager.getWorld(), 3, 67, -3), 3, -2);
		new SkywarsChest(new Location(MapManager.getWorld(), -3, 67, 3), 3, -2);


		return SkywarsChest.chests;
	}
}
