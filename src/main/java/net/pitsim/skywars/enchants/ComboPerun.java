package net.pitsim.skywars.enchants;

import dev.kyro.arcticapi.builders.ALoreBuilder;
import net.pitsim.skywars.controllers.HitCounter;
import net.pitsim.skywars.controllers.objects.PitEnchant;
import net.pitsim.skywars.controllers.objects.PitPlayer;
import net.pitsim.skywars.enums.ApplyType;
import net.pitsim.skywars.events.AttackEvent;
import net.pitsim.skywars.misc.Misc;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;

import java.util.List;

public class ComboPerun extends PitEnchant {

	public ComboPerun() {
		super("Combo: Perun's Wrath", true, ApplyType.SWORDS,
				"perun", "lightning");
	}

	@EventHandler
	public void onAttack(AttackEvent.Apply attackEvent) {
		if(!canApply(attackEvent)) return;

		int enchantLvl = attackEvent.getAttackerEnchantLevel(this);
		if(enchantLvl == 0) return;

		int regLvl = attackEvent.getAttackerEnchantLevel(Regularity.INSTANCE);
		if(Regularity.isRegHit(attackEvent.defender) && Regularity.skipIncrement(regLvl)) return;

		PitPlayer pitPlayer = PitPlayer.getPitPlayer(attackEvent.attacker);
		HitCounter.incrementCounter(pitPlayer.player, this);
		if(!HitCounter.hasReachedThreshold(pitPlayer.player, this, enchantLvl == 3 ? 4 : getStrikes(enchantLvl)))
			return;

		if(enchantLvl == 3) {
			int damage = 2;
			if(!(attackEvent.defender.getInventory().getHelmet() == null) && attackEvent.defender.getInventory().getHelmet().getType() == Material.DIAMOND_HELMET) {
				damage += 1;
			}
			if(!(attackEvent.defender.getInventory().getChestplate() == null) && attackEvent.defender.getInventory().getChestplate().getType() == Material.DIAMOND_CHESTPLATE) {
				damage += 1;
			}
			if(!(attackEvent.defender.getInventory().getLeggings() == null) && attackEvent.defender.getInventory().getLeggings().getType() == Material.DIAMOND_LEGGINGS) {
				damage += 1;
			}
			if(!(attackEvent.defender.getInventory().getBoots() == null) && attackEvent.defender.getInventory().getBoots().getType() == Material.DIAMOND_BOOTS) {
				damage += 1;
			}

			attackEvent.trueDamage += damage;
		} else {
			attackEvent.trueDamage += getTrueDamage(enchantLvl);
		}

		Misc.strikeLightningForPlayers(attackEvent.defender.getLocation(), 10);
	}

	@Override
	public List<String> getDescription(int enchantLvl) {

		if(enchantLvl == 3) {
			return new ALoreBuilder("&7Every &efourth &7hit strikes", "&elightning &7for &c" + Misc.getHearts(2) + " &7+ &c" + Misc.getHearts(1),
					"&7per &bdiamond piece &7on your", "&7victim.", "&7(Lightning deals true damage)").getLore();
		}

		return new ALoreBuilder("&7Every&e" + Misc.ordinalWords(getStrikes(enchantLvl)) + " &7hit strikes",
				"&elightning &7for &c" + Misc.getHearts(getTrueDamage(enchantLvl))).getLore();
	}

	public double getTrueDamage(int enchantLvl) {

		return enchantLvl + 2;
	}

	public int getStrikes(int enchantLvl) {

		return Math.max(6 - enchantLvl, 1);
	}
}
