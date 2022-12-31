package net.pitsim.skywars.enchants;

import dev.kyro.arcticapi.builders.ALoreBuilder;
import net.pitsim.skywars.controllers.objects.PitEnchant;
import net.pitsim.skywars.enums.ApplyType;
import net.pitsim.skywars.events.AttackEvent;
import net.pitsim.skywars.game.GoldManager;
import net.pitsim.skywars.misc.Misc;
import org.bukkit.event.EventHandler;

import java.text.DecimalFormat;
import java.util.List;

public class FractionalReserve extends PitEnchant {

	public FractionalReserve() {
		super("Fractional Reserve", false, ApplyType.PANTS,
				"fractionalreserve", "frac", "frac-reserve", "fractional-reserve", "fracreserve");
		isUncommonEnchant = true;
	}

	@EventHandler
	public void onAttack(AttackEvent.Apply attackEvent) {
		if(!canApply(attackEvent)) return;

		int enchantLvl = attackEvent.getDefenderEnchantLevel(this);
		if(enchantLvl == 0) return;

		int reduction = Math.max((int) Math.log10(GoldManager.gold.get(attackEvent.defender)) + 1, 0);
		attackEvent.multipliers.add(Misc.getReductionMultiplier(reduction * getReduction(enchantLvl)));
	}

	@Override
	public List<String> getDescription(int enchantLvl) {
		DecimalFormat decimalFormat = new DecimalFormat("0.#");
		return new ALoreBuilder("&7Receive &9-" + decimalFormat.format(getReduction(enchantLvl)) + "% &7damage per",
				"&6digit &7in your gold").getLore();
	}

	public static double getReduction(int enchantLvl) {
		return enchantLvl * 3 + 1;
	}
}
