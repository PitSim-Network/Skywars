package net.pitsim.skywars.game;

public class SkywarsLevelingSystem {

	public static void main(String[] args) {
		for(int i = 1; i <= 100; i++) System.out.println(i + " " + getCostForLevel(i) / 100);
	}

	public static int getCostForLevel(int level) {
		return (int) (Math.floor(0.5 * Math.pow(level, 2.2) + 0.000006 * Math.pow(level, 5)) * 50 + 50);
	}

	public static int getTotalXPAtLevel(int level) {
		int total = 0;
		for(int i = 1; i < (level); i++) total += getCostForLevel(i);
		return total;
	}
}