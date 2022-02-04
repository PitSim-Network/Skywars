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
		super("Murilun", "murilun", 62);
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

		new SkywarsChest(new Location(MapManager.getWorld(), -29, 57, -50), 1, 1);
		new SkywarsChest(new Location(MapManager.getWorld(), -27, 61, -49), 1, 1);
		new SkywarsChest(new Location(MapManager.getWorld(), -28, 52, -49), 1, 1);

		new SkywarsChest(new Location(MapManager.getWorld(), -1, 57, -84), 1, 2);
		new SkywarsChest(new Location(MapManager.getWorld(), -2, 61, -82), 1, 2);
		new SkywarsChest(new Location(MapManager.getWorld(), -2, 52, -83), 1, 2);

		new SkywarsChest(new Location(MapManager.getWorld(), 29, 57, -52), 1, 3);
		new SkywarsChest(new Location(MapManager.getWorld(), 27, 61, -53), 1, 3);
		new SkywarsChest(new Location(MapManager.getWorld(), 28, 52, -53), 1, 3);

		new SkywarsChest(new Location(MapManager.getWorld(), 1, 56, -55), 2, -1);
		new SkywarsChest(new Location(MapManager.getWorld(), 1, 49, -49), 2, -1);
		new SkywarsChest(new Location(MapManager.getWorld(), 3, 50, -40), 2, -1);



		new SkywarsChest(new Location(MapManager.getWorld(), 50, 57, -29), 1, 4);
		new SkywarsChest(new Location(MapManager.getWorld(), 49, 61, -27), 1, 4);
		new SkywarsChest(new Location(MapManager.getWorld(), 49, 52, -28), 1, 4);

		new SkywarsChest(new Location(MapManager.getWorld(), 84, 57, -1), 1, 5);
		new SkywarsChest(new Location(MapManager.getWorld(), 82, 61, -2), 1, 5);
		new SkywarsChest(new Location(MapManager.getWorld(), 83, 52, -2), 1, 5);

		new SkywarsChest(new Location(MapManager.getWorld(), 52, 57, 29), 1, 6);
		new SkywarsChest(new Location(MapManager.getWorld(), 53, 61, 27), 1, 6);
		new SkywarsChest(new Location(MapManager.getWorld(), 53, 52, 28), 1, 6);

		new SkywarsChest(new Location(MapManager.getWorld(), 55, 56, 1), 2, -1);
		new SkywarsChest(new Location(MapManager.getWorld(), 49, 49, 1), 2, -1);
		new SkywarsChest(new Location(MapManager.getWorld(), 40, 50, 3), 2, -1);



		new SkywarsChest(new Location(MapManager.getWorld(), 29, 57, 50), 1, 7);
		new SkywarsChest(new Location(MapManager.getWorld(), 27, 61, 49), 1, 7);
		new SkywarsChest(new Location(MapManager.getWorld(), 28, 52, 49), 1, 7);

		new SkywarsChest(new Location(MapManager.getWorld(), 1, 57, 84), 1, 8);
		new SkywarsChest(new Location(MapManager.getWorld(), 2, 61, 82), 1, 8);
		new SkywarsChest(new Location(MapManager.getWorld(), 2, 52, 83), 1, 8);

		new SkywarsChest(new Location(MapManager.getWorld(), -29, 57, 52), 1, 9);
		new SkywarsChest(new Location(MapManager.getWorld(), -27, 61, 53), 1, 9);
		new SkywarsChest(new Location(MapManager.getWorld(), -28, 52, 53), 1, 9);

		new SkywarsChest(new Location(MapManager.getWorld(), -1, 56, 55), 2,  -1);
		new SkywarsChest(new Location(MapManager.getWorld(), -1, 49, 49), 2, -1);
		new SkywarsChest(new Location(MapManager.getWorld(), -3, 50, 40), 2, -1);



		new SkywarsChest(new Location(MapManager.getWorld(), -50, 57, 29), 1, 10);
		new SkywarsChest(new Location(MapManager.getWorld(), -49, 61, 27), 1, 10);
		new SkywarsChest(new Location(MapManager.getWorld(), -49, 52, 28), 1, 10);

		new SkywarsChest(new Location(MapManager.getWorld(), -84, 57, 1), 1, 11);
		new SkywarsChest(new Location(MapManager.getWorld(), -82, 61, 2), 1, 11);
		new SkywarsChest(new Location(MapManager.getWorld(), -83, 52, 2), 1, 11);

		new SkywarsChest(new Location(MapManager.getWorld(), -52, 57, -29), 1, 12);
		new SkywarsChest(new Location(MapManager.getWorld(), -53, 61, -27), 1, 12);
		new SkywarsChest(new Location(MapManager.getWorld(), -53, 52, -28), 1, 12);

		new SkywarsChest(new Location(MapManager.getWorld(), -55, 56, -1), 2, -1);
		new SkywarsChest(new Location(MapManager.getWorld(), -49, 49, -1), 2, -1);
		new SkywarsChest(new Location(MapManager.getWorld(), -40, 50, -3), 2, -1);



		new SkywarsChest(new Location(MapManager.getWorld(), 6, 52, -11), 3, -2);
		new SkywarsChest(new Location(MapManager.getWorld(), -2, 52, 11), 3, -2);
		new SkywarsChest(new Location(MapManager.getWorld(), 3, 56, -3), 3, -2);
		new SkywarsChest(new Location(MapManager.getWorld(), -3, 56, 3), 3, -2);


		return SkywarsChest.chests;
	}
}
