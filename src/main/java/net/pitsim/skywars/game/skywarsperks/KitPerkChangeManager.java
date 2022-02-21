package net.pitsim.skywars.game.skywarsperks;

import net.pitsim.skywars.game.GameManager;
import net.pitsim.skywars.game.GameStatus;
import net.pitsim.skywars.inventories.PerkEquipGUI;
import net.pitsim.skywars.misc.Misc;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryInteractEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class KitPerkChangeManager implements Listener {


	public static void givePerkChange(Player player) {
		ItemStack perkItem = new ItemStack(Material.EYE_OF_ENDER);
		ItemMeta meta = perkItem.getItemMeta();
		meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&6&lChange Perks"));
		perkItem.setItemMeta(meta);

		player.getInventory().setItem(4, perkItem);
	}

	@EventHandler
	public void onInteract(PlayerInteractEvent event) {
		Player player = event.getPlayer();
		if(Misc.isAirOrNull(player.getItemInHand())) return;
		if(event.getAction() != Action.RIGHT_CLICK_AIR && event.getAction() != Action.RIGHT_CLICK_BLOCK) return;
		if(player.getItemInHand().getType() != Material.EYE_OF_ENDER) return;
		if(GameManager.status != GameStatus.QUEUE) return;
		event.setCancelled(true);

		PerkEquipGUI perkEquipGUI = new PerkEquipGUI(player);
		perkEquipGUI.open();
	}

	@EventHandler
	public void onDrop(PlayerDropItemEvent event) {
		Player player = event.getPlayer();
		if(GameManager.status != GameStatus.QUEUE) return;
		event.setCancelled(true);
	}

	@EventHandler
	public void onDrop(InventoryInteractEvent event) {
		if(GameManager.status != GameStatus.QUEUE) return;
		event.setCancelled(true);
	}
}
