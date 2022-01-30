package net.pitsim.skywars.builders;

import dev.kyro.arcticapi.builders.ALoreBuilder;
import net.pitsim.skywars.controllers.objects.PitEnchant;

public class PitLoreBuilder extends ALoreBuilder {

	public PitEnchant pitEnchant;

	public PitLoreBuilder(PitEnchant pitEnchant) {
		this.pitEnchant = pitEnchant;
	}
}
