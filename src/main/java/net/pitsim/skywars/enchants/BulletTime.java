package net.pitsim.skywars.enchants;

import dev.kyro.arcticapi.builders.ALoreBuilder;
import net.pitsim.skywars.controllers.objects.PitEnchant;
import net.pitsim.skywars.controllers.objects.PitPlayer;
import net.pitsim.skywars.enums.ApplyType;
import net.pitsim.skywars.events.AttackEvent;
import net.pitsim.skywars.misc.Misc;
import net.pitsim.skywars.misc.Sounds;
import org.bukkit.Effect;
import org.bukkit.event.EventHandler;

import java.util.List;

public class BulletTime extends PitEnchant {

	public BulletTime() {
		super("Bullet Time", false, ApplyType.SWORDS,
				"bullettime", "bullet-time", "bullet", "bt");
		isUncommonEnchant = true;
	}

	@EventHandler
	public void cancel(AttackEvent.Pre attackEvent) {
		if(!canApply(attackEvent)) return;

		int enchantLvl = attackEvent.getDefenderEnchantLevel(this);
		if(enchantLvl == 0 || attackEvent.arrow == null || !(attackEvent.defender.isBlocking())) return;

		Sounds.BULLET_TIME.play(attackEvent.defender);
		attackEvent.arrow.getWorld().playEffect(attackEvent.arrow.getLocation(), Effect.EXPLOSION, 0, 30);

		PitPlayer pitDefender = PitPlayer.getPitPlayer(attackEvent.defender);
		pitDefender.heal(getHealing(enchantLvl));

		attackEvent.setCancelled(true);
		attackEvent.arrow.remove();
	}

	@Override
	public List<String> getDescription(int enchantLvl) {

		if(enchantLvl == 1) {
			return new ALoreBuilder("&7Blocking destroys arrows that hit", "&7you").getLore();
		} else {
			return new ALoreBuilder("&7Blocking destroys arrows that hit", "&7you. Destroying arrows this way",
					"&7heals &c" + Misc.getHearts(getHealing(enchantLvl))).getLore();
		}
	}

	public int getHealing(int enchantLvl) {

		return (int) (Math.pow(enchantLvl, 0.75) * 3 - 3);
	}
}
