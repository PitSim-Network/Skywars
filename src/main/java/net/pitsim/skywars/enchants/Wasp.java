package net.pitsim.skywars.enchants;

import dev.kyro.arcticapi.builders.ALoreBuilder;
import dev.kyro.arcticapi.misc.AUtil;
import net.pitsim.skywars.controllers.objects.PitEnchant;
import net.pitsim.skywars.controllers.objects.PitPlayer;
import net.pitsim.skywars.enums.ApplyType;
import net.pitsim.skywars.events.AttackEvent;
import net.pitsim.skywars.misc.Misc;
import org.bukkit.event.EventHandler;
import org.bukkit.potion.PotionEffectType;

import java.util.List;

public class Wasp extends PitEnchant {

	public Wasp() {
		super("Wasp", false, ApplyType.BOWS,
				"wasp");
		isUncommonEnchant = true;
	}

	@EventHandler
	public void onAttack(AttackEvent.Apply attackEvent) {
		if(!canApply(attackEvent)) return;

		int enchantLvl = attackEvent.getAttackerEnchantLevel(this);
		if(enchantLvl == 0) return;

		Misc.applyPotionEffect(attackEvent.defender, PotionEffectType.WEAKNESS, getDuration(enchantLvl) * 20, enchantLvl, true, false);

		PitPlayer pitPlayer = PitPlayer.getPitPlayer(attackEvent.attacker);
	}

	@Override
	public List<String> getDescription(int enchantLvl) {

		return new ALoreBuilder("&7Apply &cWeakness " + AUtil.toRoman(enchantLvl + 1) + " &7(" +
				getDuration(enchantLvl) + "s) on hit").getLore();
	}

	public int getDuration(int enchantLvl) {

		return enchantLvl * 5 + 1;
	}
}
