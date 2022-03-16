package net.pitsim.skywars.enchants;

import dev.kyro.arcticapi.builders.ALoreBuilder;
import net.pitsim.skywars.controllers.objects.PitEnchant;
import net.pitsim.skywars.enums.ApplyType;
import net.pitsim.skywars.events.KillEvent;
import net.pitsim.skywars.game.KillManager;
import org.bukkit.event.EventHandler;

import java.util.List;

public class Sweaty extends PitEnchant {

	public Sweaty() {
		super("Sweaty", false, ApplyType.ALL,
				"sweaty", "sw");
		levelStacks = true;
	}

	@EventHandler
	public void onKill(KillEvent killEvent) {

		int enchantLvl = killEvent.getKillerEnchantLevel(this);
		if(enchantLvl == 0) return;

		if(KillManager.kills.containsKey(killEvent.killer))
			killEvent.xpReward += getXpIncrease(enchantLvl) * KillManager.kills.get(killEvent.killer);
	}

	@Override
	public List<String> getDescription(int enchantLvl) {

		return new ALoreBuilder("&7On kill, earn &b+" + getXpIncrease(enchantLvl) + " XP &7per", "&7kill this game.").getLore();
	}

	public int getXpIncrease(int enchantLvl) {

		return enchantLvl * 5;
	}
}
