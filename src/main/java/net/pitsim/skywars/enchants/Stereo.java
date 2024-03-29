package net.pitsim.skywars.enchants;

import com.xxmicloxx.NoteBlockAPI.model.RepeatMode;
import com.xxmicloxx.NoteBlockAPI.model.Song;
import com.xxmicloxx.NoteBlockAPI.songplayer.EntitySongPlayer;
import com.xxmicloxx.NoteBlockAPI.utils.NBSDecoder;
import dev.kyro.arcticapi.builders.ALoreBuilder;
import dev.kyro.arcticapi.events.armor.AChangeEquipmentEvent;
import dev.kyro.arcticapi.misc.AOutput;
import net.pitsim.skywars.PitSim;
import net.pitsim.skywars.controllers.StereoManager;
import net.pitsim.skywars.controllers.objects.PitEnchant;
import net.pitsim.skywars.enums.ApplyType;
import net.pitsim.skywars.events.AttackEvent;
import net.pitsim.skywars.misc.Misc;
import net.pitsim.skywars.misc.Sounds;
import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;
import java.util.List;
import java.util.Random;

public class Stereo extends PitEnchant {
	public static Stereo INSTANCE;

	public Stereo() {
		super("Stereo", false, ApplyType.PANTS,
				"stereo", "sterio");
		INSTANCE = this;
	}

	@EventHandler
	public void onArmorEquip(AChangeEquipmentEvent event) {

		if(StereoManager.hasStereo(event.getPlayer())) {

			if(StereoManager.playerMusic.containsKey(event.getPlayer())) return;


			File exampleSong = new File("plugins/NoteBlockAPI/Songs/AllStar.nbs");
			File dir = new File(exampleSong.getAbsoluteFile().getParent());

			File[] files = dir.listFiles();

			Random rand = new Random();

			assert files != null;
			File file = files[rand.nextInt(files.length)];


			Song song = NBSDecoder.parse(file);
			EntitySongPlayer esp = new EntitySongPlayer(song);
			esp.setEntity(event.getPlayer());
			esp.setDistance(16);
			esp.setRepeatMode(RepeatMode.ONE);

			for(Player onlinePlayer : Bukkit.getOnlinePlayers()) {
				if(StereoManager.toggledPlayers.contains(onlinePlayer)) return;
				esp.addPlayer(onlinePlayer);
			}


			esp.setPlaying(true);
			StereoManager.playerMusic.put(event.getPlayer(), esp);
		} else {
			EntitySongPlayer esp = StereoManager.playerMusic.get(event.getPlayer());

			if(StereoManager.playerMusic.containsKey(event.getPlayer())) esp.destroy();
			StereoManager.playerMusic.remove(event.getPlayer());


		}

	}

	static {
		new BukkitRunnable() {
			@Override
			public void run() {
				for(Player player : Bukkit.getOnlinePlayers()) {
					if(StereoManager.playerMusic.containsKey(player)) {
						player.getWorld().spigot().playEffect(player.getLocation(),
								Effect.NOTE, 0, 2, 0.5F, 0.5F, 0.5F, 1, 5, 25);
					}
				}
			}
		}.runTaskTimer(PitSim.INSTANCE, 0L, 4L);
	}


	@EventHandler
	public void onAttack(AttackEvent.Apply attackEvent) {

	}


	@Override
	public List<String> getDescription(int enchantLvl) {

		return new ALoreBuilder("&7You play a tune while cruising", "&7around").getLore();
	}

	public int getNearbyPlayers(int enchantLvl) {

		return Misc.linearEnchant(enchantLvl, 0.5, 1);
	}

	public double getDamageReduction(int enchantLvl) {

		return Math.min(30 + enchantLvl * 10, 100);
	}
}
