package net.pitsim.skywars.game;

import dev.kyro.arcticapi.misc.AOutput;
import net.pitsim.skywars.PitSim;
import net.pitsim.skywars.misc.Sounds;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.Arrays;
import java.util.List;

public class GameClock {
	public static BukkitTask countdown;
	public static int minutes = 3;
	public static int seconds = 0;
	public static List<Integer> countdownAnnouncements = Arrays.asList(3, 2, 1);
	public static boolean refill = false;

	public static void countdown() {
		String id = PitSim.INSTANCE.getConfig().getString("server-ID");
		if(id.equalsIgnoreCase("test")) {
			minutes = 0;
			seconds = 15;
		}

		countdown = new BukkitRunnable() {
			@Override
			public void run() {

				if(seconds == 0) {
					if(countdownAnnouncements.contains(minutes)) {
						if(refill) {
							AOutput.broadcast("&cThe game is ending in " + minutes + " &cminutes!");
							for(Player onlinePlayer : Bukkit.getOnlinePlayers()) {
								Sounds.ERROR.play(onlinePlayer);
							}
						}
					}
					if(minutes != 0) {
						minutes--;
						seconds = 60;
					} else {
						if(!refill) {
							ChestManager.refillChests();
							refill = true;
							minutes = 5;
							return;
						}
						GameManager.endTieGame();
						this.cancel();
						countdown = null;

					}
				}
				seconds--;
			}
		}.runTaskTimer(PitSim.INSTANCE, 20L, 20L);
	}
}
