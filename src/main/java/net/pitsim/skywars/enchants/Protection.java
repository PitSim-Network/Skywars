package net.pitsim.skywars.enchants;

import dev.kyro.arcticapi.builders.ALoreBuilder;
import net.pitsim.skywars.controllers.objects.PitEnchant;
import net.pitsim.skywars.enums.ApplyType;
import net.pitsim.skywars.events.AttackEvent;
import net.pitsim.skywars.misc.Misc;
import org.bukkit.event.EventHandler;

import java.util.List;

public class Protection extends PitEnchant {

	public Protection() {
		super("Protection", false, ApplyType.PANTS,
				"prot", "protection", "p");
	}

	@EventHandler
	public void onAttack(AttackEvent.Apply attackEvent) {
		if(!canApply(attackEvent)) return;

		int enchantLvl = attackEvent.getDefenderEnchantLevel(this);
		if(enchantLvl == 0) return;

		attackEvent.multipliers.add(Misc.getReductionMultiplier(getDamageReduction(enchantLvl)));
	}

	@Override
	public List<String> getDescription(int enchantLvl) {

		return new ALoreBuilder("&7Receive &9-" + Misc.roundString(getDamageReduction(enchantLvl)) + "% &7damage").getLore();
	}

	public double getDamageReduction(int enchantLvl) {

		return (int) Math.floor(Math.pow(enchantLvl, 1.3) * 2) * 2 + 4;
	}
}
