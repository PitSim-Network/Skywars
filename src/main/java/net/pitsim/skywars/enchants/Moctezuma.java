package net.pitsim.skywars.enchants;

import dev.kyro.arcticapi.builders.ALoreBuilder;
import net.pitsim.skywars.controllers.objects.PitEnchant;
import net.pitsim.skywars.enums.ApplyType;
import net.pitsim.skywars.events.KillEvent;
import org.bukkit.event.EventHandler;

import java.util.List;

public class Moctezuma extends PitEnchant {

	public Moctezuma() {
		super("Moctezuma", false, ApplyType.ALL,
				"moctezuma", "moct", "moc");
		levelStacks = true;
	}

	@EventHandler
	public void onKill(KillEvent killEvent) {

		int enchantLvl = killEvent.getKillerEnchantLevel(this);
		if(enchantLvl == 0) return;

		killEvent.coinReward += getGoldIncrease(enchantLvl);
	}

	@Override
	public List<String> getDescription(int enchantLvl) {

		return new ALoreBuilder("&7Earn &e+" + getGoldIncrease(enchantLvl) + " Coins &7on kill (assists", "&7excluded)").getLore();
	}

	public int getGoldIncrease(int enchantLvl) {

		return 5 + (15 * enchantLvl);
	}
}
