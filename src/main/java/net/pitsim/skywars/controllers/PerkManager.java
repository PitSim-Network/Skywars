package net.pitsim.skywars.controllers;

import net.pitsim.skywars.PitSim;
import net.pitsim.skywars.controllers.objects.PitPerk;

import java.util.ArrayList;
import java.util.List;

public class PerkManager {

	public static List<PitPerk> pitPerks = new ArrayList<>();

	public static void registerUpgrade(PitPerk pitPerk) {

		pitPerks.add(pitPerk);
		PitSim.INSTANCE.getServer().getPluginManager().registerEvents(pitPerk, PitSim.INSTANCE);
	}

}
