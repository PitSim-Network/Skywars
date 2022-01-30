package net.pitsim.skywars.enchants;

import dev.kyro.arcticapi.builders.ALoreBuilder;
import net.pitsim.skywars.controllers.objects.PitEnchant;
import net.pitsim.skywars.enums.ApplyType;
import net.pitsim.skywars.events.AttackEvent;
import net.pitsim.skywars.misc.Misc;
import org.bukkit.event.EventHandler;

import java.util.List;

public class Prick extends PitEnchant {

	public Prick() {
		super("Prick", false, ApplyType.PANTS,
				"prick", "thorns");
	}

	@EventHandler
	public void onAttack(AttackEvent.Apply attackEvent) {
		if(!canApply(attackEvent)) return;

		int enchantLvl = attackEvent.getDefenderEnchantLevel(this);
		if(enchantLvl == 0) return;

		attackEvent.selfTrueDamage += getDamage(enchantLvl);
	}

	@Override
	public List<String> getDescription(int enchantLvl) {

		return new ALoreBuilder("&7Enemies hitting you receive", "&c" + Misc.getHearts(getDamage(enchantLvl)) + " &7true damage").getLore();
	}

	public double getDamage(int enchantLvl) {

		return enchantLvl * 0.25 + 0.25;
	}
}
