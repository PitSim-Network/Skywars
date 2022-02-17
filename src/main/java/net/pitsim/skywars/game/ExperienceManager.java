package net.pitsim.skywars.game;

import dev.kyro.arcticapi.misc.AOutput;
import net.pitsim.skywars.controllers.objects.PitPlayer;
import net.pitsim.skywars.misc.Misc;
import net.pitsim.skywars.misc.Sounds;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.text.DecimalFormat;

public class ExperienceManager {

	public static void addXP(Player player, int amount) {
		PitPlayer pitPlayer = PitPlayer.getPitPlayer(player);

		final int level = pitPlayer.stats.level;
		double currentXP = pitPlayer.stats.xp;
		double neededXP = getXP(level);

		if(currentXP + amount >= neededXP) {
			pitPlayer.stats.level++;
			pitPlayer.stats.xp = amount - (neededXP - currentXP);
			pitPlayer.stats.coins += coinReward(pitPlayer.stats.level);
			pitPlayer.saveSQLData();

			Sounds.LEVEL_UP.play(player);
			AOutput.send(player, "&8&m--------------------------");
			AOutput.send(player, "&a&lLEVEL UP!");
			AOutput.send(player, "");
			AOutput.send(player, formattedLevel(level) + " \u27a4 " + formattedLevel(pitPlayer.stats.level));
			AOutput.send(player, "");
			AOutput.send(player, "&eRewards:");
			AOutput.send(player, "&6+" + new DecimalFormat("###,###,###").format(coinReward(pitPlayer.stats.level)) + " Coins");
			AOutput.send(player, "&8&m--------------------------");

			Misc.sendTitle(player, "&a&lLEVEL UP!", 60);
			Misc.sendSubTitle(player, "", 60);

		} else {
			pitPlayer.stats.xp += amount;
		}
		setXPBar(player);
	}

	public static int getXP(int level) {
		return SkywarsLevelingSystem.getCostForLevel(level);
	}

	public static String formattedLevel(int level) {
		return getColor(level) + "[" + level + "]";
	}

	public static int coinReward(int level) {
		return level * 500;
	}

	public static ChatColor getColor(int level) {
		if(level < 10) return ChatColor.GRAY;
		if(level < 20) return ChatColor.YELLOW;
		if(level < 30) return ChatColor.AQUA;
		if(level < 40) return ChatColor.GREEN;
		if(level < 50) return ChatColor.RED;
		if(level < 60) return ChatColor.LIGHT_PURPLE;
		if(level < 70) return ChatColor.GOLD;
		if(level < 80) return ChatColor.DARK_BLUE;
		if(level < 90) return ChatColor.DARK_GREEN;
		if(level < 100) return ChatColor.DARK_RED;
		return ChatColor.DARK_PURPLE;
	}

	public static void setXPBar(Player player) {
		PitPlayer pitPlayer = PitPlayer.getPitPlayer(player);
		player.setLevel(pitPlayer.stats.level);
		float remaining = (float) (getXP(pitPlayer.stats.level) - pitPlayer.stats.xp);
		float total = (float) getXP(pitPlayer.stats.level);
		float xp = (total - remaining) / total;
		player.setExp(xp);
	}
}
