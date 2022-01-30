package net.pitsim.skywars.enchants;

import dev.kyro.arcticapi.builders.ALoreBuilder;
import net.pitsim.skywars.controllers.objects.PitEnchant;
import net.pitsim.skywars.enums.ApplyType;
import net.pitsim.skywars.events.AttackEvent;
import org.bukkit.event.EventHandler;

import java.util.List;

public class Punisher extends PitEnchant {

	public Punisher() {
		super("Punisher", false, ApplyType.SWORDS,
				"pun", "punisher");
	}

	@EventHandler
	public void onAttack(AttackEvent.Apply attackEvent) {
		if(!canApply(attackEvent)) return;

		int enchantLvl = attackEvent.getAttackerEnchantLevel(this);
		if(enchantLvl == 0) return;

		if(attackEvent.defender.getHealth() / attackEvent.defender.getMaxHealth() > 0.5) return;
		attackEvent.increasePercent += getDamage(enchantLvl) / 100D;
	}

	@Override
	public List<String> getDescription(int enchantLvl) {

		return new ALoreBuilder("&7Deal &c+" + getDamage(enchantLvl) + "% &7damage vs. players", "&7below 50% HP").getLore();
	}

	public int getDamage(int enchantLvl) {

		return enchantLvl * 6;
	}
}
