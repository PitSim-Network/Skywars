package net.pitsim.skywars.inventories;

import dev.kyro.arcticapi.gui.AGUI;
import dev.kyro.arcticapi.gui.AGUIPanel;
import me.clip.placeholderapi.PlaceholderAPI;
import net.pitsim.skywars.controllers.objects.PitPlayer;
import net.pitsim.skywars.game.GameManager;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.ArrayList;
import java.util.List;

public class SpectatorPanel extends AGUIPanel {
	PitPlayer pitPlayer = PitPlayer.getPitPlayer(player);
	public SpectatorGUI spectatorGUI;

	public List<Player> headList = new ArrayList<>();

	public SpectatorPanel(AGUI gui) {
		super(gui);
		spectatorGUI = (SpectatorGUI) gui;
	}

	@Override
	public String getName() {
		return "Teleport to a player";
	}

	@Override
	public int getRows() {
		if(GameManager.alivePlayers.size() < 10) return 1;
		else return 2;
	}

	@Override
	public void onClick(InventoryClickEvent event) {
		int slot = event.getSlot();
		if(event.getClickedInventory().getHolder() == this) {
			if(slot > headList.size()) return;

			player.teleport(headList.get(slot));
			player.closeInventory();
		}
	}

	@Override
	public void onOpen(InventoryOpenEvent event) {
		for(int i = 0; i < GameManager.alivePlayers.size(); i++) {
			Player alivePlayer = GameManager.alivePlayers.get(i);
			ItemStack head = new ItemStack(Material.SKULL_ITEM, 1, (byte) 3);
			SkullMeta headMeta = (SkullMeta) head.getItemMeta();
			headMeta.setOwner(alivePlayer.getName());

			headMeta.setDisplayName(PlaceholderAPI.setPlaceholders(alivePlayer, "%luckperms_prefix%" + alivePlayer.getDisplayName()));
			List<String> headLore = new ArrayList<>();
			headLore.add(ChatColor.GRAY + "Health: " + ChatColor.RED + (int) (player.getHealth() / 2) + "\u2764");
			headLore.add("");
			headLore.add(ChatColor.GRAY + "Click to teleport to player!");
			headMeta.setLore(headLore);
			head.setItemMeta(headMeta);

			getInventory().setItem(i, head);
			headList.add(alivePlayer);
		}
	}

	@Override
	public void onClose(InventoryCloseEvent event) { }
}
