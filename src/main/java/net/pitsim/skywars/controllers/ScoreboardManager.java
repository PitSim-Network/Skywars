package net.pitsim.skywars.controllers;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import net.pitsim.skywars.controllers.objects.Scoreboard;
import org.bukkit.entity.Player;

public class ScoreboardManager extends PlaceholderExpansion {
	public static Scoreboard activeScoreboard;

	public static void setScoreboard(Scoreboard scoreboard) {
		activeScoreboard = scoreboard;
	}

	@Override
	public String onPlaceholderRequest(Player player, String identifier){
		if(player == null || activeScoreboard == null) return "";

		for(int i = 1; i <= 8; i++) {
			String testString = "line_" + i;
			if(!identifier.equalsIgnoreCase(testString)) continue;
			switch(i) {
				case 1:
					return activeScoreboard.getLine1(player);
				case 2:
					return activeScoreboard.getLine2(player);
				case 3:
					return activeScoreboard.getLine3(player);
				case 4:
					return activeScoreboard.getLine4(player);
				case 5:
					return activeScoreboard.getLine5(player);
				case 6:
					return activeScoreboard.getLine6(player);
				case 7:
					return activeScoreboard.getLine7(player);
				case 8:
					return activeScoreboard.getLine8(player);
			}
		}

		return null;
	}

	@Override
	public String getIdentifier() {
		return "skywars";
	}

	@Override
	public String getAuthor() {
		return "KyroKrypt";
	}

	@Override
	public String getVersion() {
		return "1.0";
	}
}
