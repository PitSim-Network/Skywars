package net.pitsim.skywars.game.objects;

import org.bukkit.Location;

import java.io.File;
import java.util.List;
import java.util.Map;

public abstract class GameMap {
	public String name;
	public String refName;
	public int cageHeight;
	public int pasteHeight;

	private final List<SkywarsChest> chests;

	public GameMap(String name, String refName, int cageHeight, int pasteHeight) {
		this.name = name;
		this.refName = refName;
		this.cageHeight = cageHeight;
		this.pasteHeight = pasteHeight;

		this.chests = initializeChests();
	}

	public abstract File getSchematicFile();

	public abstract Map<Integer, Location> getSpawnLocations();

	public abstract List<SkywarsChest> initializeChests();

	public List<SkywarsChest> getChests() {
		return chests;
	}
}
