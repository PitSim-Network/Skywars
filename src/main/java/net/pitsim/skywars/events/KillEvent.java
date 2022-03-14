package net.pitsim.skywars.events;

import net.pitsim.skywars.controllers.objects.PitEnchant;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class KillEvent extends Event {
	private static final HandlerList handlers = new HandlerList();

	//	public AttackEvent.Apply attackEvent;
	public Player killer;
	public Player dead;
	private final Map<PitEnchant, Integer> killerEnchantMap;
	private final Map<PitEnchant, Integer> deadEnchantMap;

	public boolean exeDeath;
	public int xpReward = 50;
	public double coinReward = 100;
	public List<Double> xpMultipliers = new ArrayList<>();
	public List<Double> coinMultipliers = new ArrayList<>();
	public AttackEvent attackEvent;

	public boolean isLuckyKill = false;
	public int playerKillWorth = 1;

	public KillEvent(AttackEvent attackEvent, Player killer, Player dead, boolean exeDeath) {

		this.killerEnchantMap = killer == attackEvent.attacker ? attackEvent.getAttackerEnchantMap() : attackEvent.getDefenderEnchantMap();
		this.deadEnchantMap = killer == attackEvent.attacker ? attackEvent.getDefenderEnchantMap() : attackEvent.getAttackerEnchantMap();
		this.killer = killer;
		this.dead = dead;
		this.exeDeath = exeDeath;
		this.attackEvent = attackEvent;
	}

	public int getFinalXp() {

		double xpReward = this.xpReward;
		for(Double xpMultiplier : xpMultipliers) {
			xpReward *= xpMultiplier;
		}
		return (int) xpReward;
	}

	public double getFinalcoin() {
		double coinReward = this.coinReward;
		for(Double coinMultiplier : coinMultipliers) {
			coinReward *= coinMultiplier;
		}
		return coinReward;
	}

	@Override
	public HandlerList getHandlers() {
		return handlers;
	}

	public static HandlerList getHandlerList() {
		return handlers;
	}

	public int getKillerEnchantLevel(PitEnchant pitEnchant) {

		return killerEnchantMap.getOrDefault(pitEnchant, 0);
	}

	public int getDeadEnchantLevel(PitEnchant pitEnchant) {

		return deadEnchantMap.getOrDefault(pitEnchant, 0);
	}

	public Map<PitEnchant, Integer> getKillerEnchantMap() {
		return killerEnchantMap;
	}

	public Map<PitEnchant, Integer> getDeadEnchantMap() {
		return deadEnchantMap;
	}
}
