package net.pitsim.skywars.game;

import com.sk89q.worldedit.CuboidClipboard;
import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.MaxChangedBlocksException;
import com.sk89q.worldedit.bukkit.BukkitWorld;
import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import com.sk89q.worldedit.schematic.MCEditSchematicFormat;
import com.sk89q.worldedit.world.DataException;
import net.pitsim.skywars.game.gamemaps.Murilun;
import net.pitsim.skywars.game.objects.GameMap;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

import java.io.File;
import java.io.IOException;
import java.util.Map;

public class MapManager {
	public static Location queueSpawn = new Location(Bukkit.getWorld("game"), 0, 100, 0);

	public static GameMap map;

	public static World getWorld() {
		return Bukkit.getWorld("game");
	}

	public static void onPluginStart() {
		map = new Murilun();

		File clear = new File("plugins/Skywars/schematics/skywarsClear.schematic");
		loadSchematic(clear, new Location(getWorld(), 0, 65, 0));
		loadSchematic(map.getSchematicFile(), new Location(getWorld(), 0, 76, 0));
		File cage = new File("plugins/Skywars/schematics/cage.schematic");

		for(Map.Entry<Integer, Location> entry : map.getSpawnLocations().entrySet()) {
			int position = entry.getKey();
			Location location = entry.getValue();

			loadSchematic(cage, location);
		}


	}

	public static void onGameStart() {
		File cageClear = new File("plugins/Skywars/schematics/cageClear.schematic");
		for(Map.Entry<Integer, Location> entry : map.getSpawnLocations().entrySet()) {
			int position = entry.getKey();
			Location location = entry.getValue();

			loadSchematic(cageClear, location);
		}
	}

	public static void loadSchematic(File file, Location location) {
		WorldEditPlugin worldEditPlugin = (WorldEditPlugin) Bukkit.getPluginManager().getPlugin("WorldEdit");
		EditSession session = worldEditPlugin.getWorldEdit().getEditSessionFactory().getEditSession(new BukkitWorld(location.getWorld()), -1);
		try {
			CuboidClipboard clipboard = MCEditSchematicFormat.getFormat(file).load(file);
			clipboard.paste(session, new com.sk89q.worldedit.Vector(location.getX(), location.getY(), location.getZ()), false);
		} catch(MaxChangedBlocksException | DataException | IOException e) {
			e.printStackTrace();
		}
	}
}
