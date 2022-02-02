package net.pitsim.skywars.game;

import net.pitsim.skywars.PitSim;
import net.pitsim.skywars.misc.Misc;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;

public class GameManager {
	public static GameStatus status;
	public static List<Player> alivePlayers = new ArrayList<>();

	public static void init() {
		status = GameStatus.QUEUE;
	}

	public static void startGame() {
		status = GameStatus.ACTIVE;
		PluginMessageSender.sendStart();

		MapManager.onGameStart();
	}

	public static void endGame() {
		Misc.sendTitle(alivePlayers.get(0), "&6&lYOU WON!", 100);

		new BukkitRunnable() {
			@Override
			public void run() {
				PluginMessageSender.sendToLobby();
			}
		}.runTaskLater(PitSim.INSTANCE, 10 * 20L);
	}



}
