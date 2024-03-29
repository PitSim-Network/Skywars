package net.pitsim.skywars.enchants;

import dev.kyro.arcticapi.builders.ALoreBuilder;
import net.pitsim.skywars.controllers.objects.PitEnchant;
import net.pitsim.skywars.enums.ApplyType;
import net.pitsim.skywars.events.AttackEvent;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;

import java.util.List;

public class BeatTheSpammers extends PitEnchant {

	public BeatTheSpammers() {
		super("Beat the Spammers", false, ApplyType.SWORDS,
				"bts", "spammers", "beat", "beat-the-spammers", "beatthespammers");
	}

	@EventHandler
	public void onAttack(AttackEvent.Apply attackEvent) {
		if(!canApply(attackEvent)) return;

		int enchantLvl = attackEvent.getAttackerEnchantLevel(this);
		if(enchantLvl == 0) return;

		if(!attackEvent.defender.getItemInHand().getType().equals(Material.BOW)) return;
		attackEvent.increasePercent += getDamage(enchantLvl) / 100D;
	}

	@Override
	public List<String> getDescription(int enchantLvl) {

		return new ALoreBuilder("&7Deal &c+" + getDamage(enchantLvl) + "% &7damage vs. players", "&7holding a bow").getLore();
	}

	public int getDamage(int enchantLvl) {

		return enchantLvl * 25;
	}
}
