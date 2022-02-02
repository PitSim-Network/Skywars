package net.pitsim.skywars.game.gamemaps;

import net.pitsim.skywars.game.MapManager;
import net.pitsim.skywars.game.objects.GameMap;
import org.bukkit.Location;

import java.io.File;
import java.util.HashMap;
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
	public Map<Integer, Location> getChests() {
		return null;
	}
}
