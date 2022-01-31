package net.pitsim.skywars.game;

import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.List;

public class GameManager {
	public static GameStatus status;
	public static List<Player> players = new ArrayList<>();

	public static void init() {
		status = GameStatus.QUEUE;
	}

	public static void startGame() {
		status = GameStatus.ACTIVE;
		PluginMessageSender.sendStart();
	}
}
