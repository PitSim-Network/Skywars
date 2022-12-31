package net.pitsim.skywars.enchants;

import dev.kyro.arcticapi.builders.ALoreBuilder;
import net.pitsim.skywars.controllers.objects.PitEnchant;
import net.pitsim.skywars.enums.ApplyType;
import net.pitsim.skywars.events.AttackEvent;
import net.pitsim.skywars.misc.Misc;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;

import java.util.List;

public class Solitude extends PitEnchant {

	public Solitude() {
		super("Solitude", true, ApplyType.PANTS,
				"solitude", "soli");
	}

	@EventHandler
	public void onAttack(AttackEvent.Apply attackEvent) {
		if(!canApply(attackEvent)) return;

		int enchantLvl = attackEvent.getDefenderEnchantLevel(this);
		if(enchantLvl == 0) return;

		int nearbyPlayers = 0;
		for(Entity nearby : attackEvent.defender.getNearbyEntities(7, 7, 7)) {
			if(!(nearby instanceof Player) || nearby == attackEvent.defender) continue;
			nearbyPlayers++;
		}

		double reduction = Math.max(getDamageReduction(enchantLvl) - nearbyPlayers * getReductionPerPlayer(enchantLvl), 0);
		attackEvent.multipliers.add(Misc.getReductionMultiplier(reduction));
	}

	@Override
	public List<String> getDescription(int enchantLvl) {

		return new ALoreBuilder("&7Receive &9-" + Misc.roundString(getDamageReduction(enchantLvl)) + "% &7damage, but",
				"&7lose &9" + getReductionPerPlayer(enchantLvl) + "% &7reduction for every", "&7nearby player besides yourself").getLore();
	}

	public int getReductionPerPlayer(int enchantLvl) {

		return 12;
	}

	public double getDamageReduction(int enchantLvl) {

		return Math.min(30 + enchantLvl * 10, 100);
	}
}
