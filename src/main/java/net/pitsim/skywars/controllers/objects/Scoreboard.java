package net.pitsim.skywars.controllers.objects;

import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Score;

public abstract class Scoreboard {
	public abstract String getLine1(Player player);
	public abstract String getLine2(Player player);
	public abstract String getLine3(Player player);
	public abstract String getLine4(Player player);
	public abstract String getLine5(Player player);
	public abstract String getLine6(Player player);
	public abstract String getLine7(Player player);
	public abstract String getLine8(Player player);
}
