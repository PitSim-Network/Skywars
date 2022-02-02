package net.pitsim.skywars.game.objects;

import org.bukkit.Location;

import java.io.File;
import java.util.Map;

public abstract class GameMap {

	public String name;
	public String refName;
	public int cageHeight;

	public GameMap(String name, String refName, int cageHeight) {
		this.name = name;
		this.refName = refName;
		this.cageHeight = cageHeight;
	}

	public abstract File getSchematicFile();

	public abstract Map<Integer, Location> getSpawnLocations();

	public abstract Map<Integer, Location> getChests();
}
