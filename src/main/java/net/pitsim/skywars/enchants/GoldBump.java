package net.pitsim.skywars.enchants;

import dev.kyro.arcticapi.builders.ALoreBuilder;
import net.pitsim.skywars.controllers.objects.PitEnchant;
import net.pitsim.skywars.enums.ApplyType;
import net.pitsim.skywars.events.KillEvent;
import net.pitsim.skywars.game.GoldManager;
import net.pitsim.skywars.misc.Misc;
import net.pitsim.skywars.misc.Sounds;
import org.bukkit.event.EventHandler;

import java.text.DecimalFormat;
import java.util.List;

public class GoldBump extends PitEnchant {

	public GoldBump() {
		super("Gold Bump", false, ApplyType.ALL,
				"goldbump", "gold-bump", "bump", "gbump");
		levelStacks = true;
	}

	@EventHandler
	public void onKill(KillEvent killEvent) {

		int enchantLvl = killEvent.getKillerEnchantLevel(this);
		if(enchantLvl == 0) return;

		GoldManager.gold.put(killEvent.killer, GoldManager.gold.get(killEvent.killer) + getGoldIncrease(enchantLvl));

		Sounds.ASSIST.play(killEvent.killer);
		String message = "&6+" + new DecimalFormat("###,###,###").format(getGoldIncrease(enchantLvl)) + "g (" +
				new DecimalFormat("###,###,###").format(GoldManager.gold.get(killEvent.killer)) + "g)";
		GoldManager.pausePlayer(killEvent.killer);
		Misc.sendActionBar(killEvent.killer, message);
	}

	@Override
	public List<String> getDescription(int enchantLvl) {

		return new ALoreBuilder("&7Earn &6+" + getGoldIncrease(enchantLvl) + "g &7on kill").getLore();
	}

	public int getGoldIncrease(int enchantLvl) {

		return enchantLvl * 500;
	}
}
