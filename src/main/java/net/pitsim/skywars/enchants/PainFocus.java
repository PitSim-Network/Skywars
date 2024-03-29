package net.pitsim.skywars.enchants;

import dev.kyro.arcticapi.builders.ALoreBuilder;
import net.pitsim.skywars.controllers.objects.PitEnchant;
import net.pitsim.skywars.enums.ApplyType;
import net.pitsim.skywars.events.AttackEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;

import java.util.List;

public class PainFocus extends PitEnchant {

	public PainFocus() {
		super("Pain Focus", false, ApplyType.SWORDS,
				"painfocus", "pf", "pain-focus");
	}

	@EventHandler
	public void onAttack(AttackEvent.Apply attackEvent) {
		if(!canApply(attackEvent)) return;

		int enchantLvl = attackEvent.getAttackerEnchantLevel(this);
		if(enchantLvl == 0) return;

		attackEvent.increasePercent += getDamage(attackEvent.attacker, enchantLvl) / 100;
	}

	@Override
	public List<String> getDescription(int enchantLvl) {

		return new ALoreBuilder("&7Deal &c+" + getDamage(enchantLvl) + "% &7damage per &c\u2764", "&7you're missing").getLore();
	}

	public int getDamage(int enchantLvl) {
		if(enchantLvl == 1) return 1;
		return enchantLvl * 2 - 2;
	}

	public double getDamage(Player player, int enchantLvl) {

		int missingHearts = (int) ((player.getMaxHealth() - player.getHealth()) / 2);
		return missingHearts * getDamage(enchantLvl);
	}
}
