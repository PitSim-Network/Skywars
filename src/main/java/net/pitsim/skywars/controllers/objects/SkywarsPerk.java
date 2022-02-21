package net.pitsim.skywars.controllers.objects;

import net.pitsim.skywars.PitSim;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import java.util.ArrayList;
import java.util.List;

public abstract class SkywarsPerk implements Listener {

	public static List<SkywarsPerk> perks = new ArrayList<>();

	public String name;
	public String refName;
	public List<Integer> cost;

	public static SkywarsPerk noPerkInstance;

	public SkywarsPerk(String name, String refName, List<Integer> cost) {
		this.name = name;
		this.refName = refName;
		this.cost = cost;
	}

	public abstract Material getMaterial();

	public abstract List<String> getEquipLore(Player player);

	public abstract List<String> getUpgradeLore(Player player);

	public abstract SkywarsPerk getInstance();

	public static void registerPerk(SkywarsPerk perk) {
		PitSim.INSTANCE.getServer().getPluginManager().registerEvents(perk, PitSim.INSTANCE);
		perks.add(perk.getInstance());
		if(perk.refName.equals("no_perk")) noPerkInstance = perk.getInstance();
	}

	public static SkywarsPerk getPerk(String refName) {
		//MANAGE LEGACY PERK SUPPORT HERE TO PREVENT SQL DATA LOSS
		for(SkywarsPerk perk : perks) {
			if(perk.refName.equals(refName)) return perk;
		}

		return noPerkInstance;
	}

	public static int getPerkTier(Player player, String refName) {
		PitPlayer pitPlayer = PitPlayer.getPitPlayer(player);

		return pitPlayer.purchasedPerks.perkTiers.get(refName);
	}

	public static boolean hasPerkEquipped(Player player, String refName) {
		 PitPlayer pitPlayer = PitPlayer.getPitPlayer(player);

		for(SkywarsPerk skywarsPerk : pitPlayer.equippedPerks.perks) {
			if(skywarsPerk.refName.equals(refName)) return true;
		}
		return false;
	}




}
