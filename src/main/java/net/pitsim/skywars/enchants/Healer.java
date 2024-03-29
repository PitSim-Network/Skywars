package net.pitsim.skywars.enchants;

import dev.kyro.arcticapi.builders.ALoreBuilder;
import net.pitsim.skywars.controllers.Cooldown;
import net.pitsim.skywars.controllers.objects.PitEnchant;
import net.pitsim.skywars.controllers.objects.PitPlayer;
import net.pitsim.skywars.enums.ApplyType;
import net.pitsim.skywars.events.AttackEvent;
import net.pitsim.skywars.misc.Misc;
import org.bukkit.Effect;
import org.bukkit.event.EventHandler;

import java.util.List;

public class Healer extends PitEnchant {

	public Healer() {
		super("Healer", true, ApplyType.SWORDS,
				"healer", "heal");
	}

	@EventHandler
	public void onAttack(AttackEvent.Apply attackEvent) {
		if(!canApply(attackEvent)) return;
		PitPlayer pitAttacker = PitPlayer.getPitPlayer(attackEvent.attacker);
		PitPlayer pitDefender = PitPlayer.getPitPlayer(attackEvent.defender);

		int enchantLvl = attackEvent.getAttackerEnchantLevel(this);
		if(enchantLvl == 0) return;

		Cooldown cooldown = getCooldown(attackEvent.attacker, 20);
		if(cooldown.isOnCooldown()) return;
		else cooldown.reset();

		attackEvent.multipliers.add(0d);
		pitAttacker.heal(0.5 + (0.5 * enchantLvl));
		pitDefender.heal(getHealing(enchantLvl));

		attackEvent.defender.getWorld().spigot().playEffect(attackEvent.defender.getLocation().add(0, 1, 0),
				Effect.HAPPY_VILLAGER, 0, 0, (float) 0.5, (float) 0.5, (float) 0.5, (float) 0.01, 20, 50);

	}

	@Override
	public List<String> getDescription(int enchantLvl) {

		return new ALoreBuilder("&7Your hits &aheal &7you for &c" + Misc.getHearts(0.5 + (0.5 * enchantLvl)),
				"&7and them for &c" + Misc.getHearts(getHealing(enchantLvl)) + " &7(1s cd)").getLore();
	}

	public double getHealing(int enchantLvl) {

		return enchantLvl * 2;
	}
}
