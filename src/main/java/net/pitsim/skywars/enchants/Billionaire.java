package net.pitsim.skywars.enchants;

import dev.kyro.arcticapi.builders.ALoreBuilder;
import net.pitsim.skywars.controllers.objects.PitEnchant;
import net.pitsim.skywars.controllers.objects.PitPlayer;
import net.pitsim.skywars.enums.ApplyType;
import net.pitsim.skywars.events.AttackEvent;
import net.pitsim.skywars.game.GoldManager;
import net.pitsim.skywars.misc.Sounds;
import org.bukkit.event.EventHandler;

import java.text.DecimalFormat;
import java.util.List;

public class Billionaire extends PitEnchant {

	public static Billionaire INSTANCE;

	public Billionaire() {
		super("Billionaire", true, ApplyType.SWORDS,
				"bill", "billionaire");
		INSTANCE = this;
	}

	@EventHandler
	public void onAttack(AttackEvent.Apply attackEvent) {
		if(!canApply(attackEvent)) return;

		int enchantLvl = attackEvent.getAttackerEnchantLevel(this);
		if(enchantLvl == 0) return;

		PitPlayer pitPlayer = PitPlayer.getPitPlayer(attackEvent.attacker);

		int gold = GoldManager.gold.get(attackEvent.attacker);
		if(gold - getGoldCost(enchantLvl) < 0) return;
		GoldManager.gold.put(attackEvent.attacker, GoldManager.gold.get(attackEvent.attacker) - getGoldCost(enchantLvl));

		attackEvent.multiplier.add(getDamageMultiplier(enchantLvl));
//		attackEvent.increasePercent += getDamageIncrease(enchantLvl) / 100.0;
		Sounds.BILLIONAIRE.play(attackEvent.attacker);
	}

	@Override
	public List<String> getDescription(int enchantLvl) {
		DecimalFormat decimalFormat = new DecimalFormat("0.##");
		return new ALoreBuilder("&7Hits with this sword deal &c" + getDamageMultiplier(enchantLvl) + "x",
				"&cdamage &7but cost &6" + getGoldCost(enchantLvl) + "g").getLore();
	}

//	public double getDamageIncrease(int enchantLvl) {
//		if(enchantLvl % 3 == 0) return enchantLvl;
//		return (enchantLvl / 3.0) * 100;
//	}

	public double getDamageMultiplier(int enchantLvl) {
		return (double) Math.round((1 + (double) enchantLvl / 3) * 100) / 100;
	}

	public int getGoldCost(int enchantLvl) {
		if(enchantLvl == 1) return 100;
		return enchantLvl * 450 - 600;
	}
}
