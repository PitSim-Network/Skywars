package net.pitsim.skywars.enchants;

import dev.kyro.arcticapi.builders.ALoreBuilder;
import net.pitsim.skywars.controllers.objects.PitEnchant;
import net.pitsim.skywars.enums.ApplyType;
import net.pitsim.skywars.events.KillEvent;
import org.bukkit.event.EventHandler;

import java.util.List;

public class CoinBoost extends PitEnchant {

	public CoinBoost() {
		super("Coin Boost", false, ApplyType.ALL,
				"coinboost", "coin-boost", "cboost", "boost");
		levelStacks = true;
	}

	@EventHandler
	public void onKill(KillEvent killEvent) {

		int enchantLvl = killEvent.getKillerEnchantLevel(this);
		if(enchantLvl == 0) return;

		killEvent.coinMultipliers.add((getGoldIncrease(enchantLvl) / 100D) + 1);
	}

	@Override
	public List<String> getDescription(int enchantLvl) {

		return new ALoreBuilder("&7Earn &e+" + getGoldIncrease(enchantLvl) + "% Coins &7from kills").getLore();
	}

	public int getGoldIncrease(int enchantLvl) {

		return enchantLvl * 15;
	}
}
