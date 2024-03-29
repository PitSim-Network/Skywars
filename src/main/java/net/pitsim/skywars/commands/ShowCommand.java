package net.pitsim.skywars.commands;

import dev.kyro.arcticapi.misc.AOutput;
import me.clip.placeholderapi.PlaceholderAPI;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.pitsim.skywars.controllers.objects.PitPlayer;
import net.pitsim.skywars.misc.Misc;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class ShowCommand implements CommandExecutor {
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		if(!(sender instanceof Player)) return false;

		Player player = (Player) sender;
		PitPlayer pitPlayer = PitPlayer.getPitPlayer(player);

		if(Misc.isAirOrNull(player.getItemInHand())) return false;

		ItemStack item = player.getItemInHand();
		ItemMeta meta = item.getItemMeta();
		List<String> lore = meta.getLore();

		double coins = pitPlayer.stats.coins;
		if((coins - 50) < 0) {
			AOutput.error(player, "&cYou do not have enough coins to do this!");
			return false;
		} else {
			pitPlayer.stats.coins-= 50;
			AOutput.send(player, "&aShowed off item for &e50 Coins&a.");
		}


		if(!item.hasItemMeta()) {
			AOutput.error(player, "&cThis item does not have lore!");
			return false;
		}

		StringBuilder builder = new StringBuilder();
		builder.append(meta.getDisplayName()).append("\n");

		int i = 0;
		if(lore.size() < 1) {
			AOutput.error(player, "&cThis item does not have lore!");
			return false;
		}

		for(String s : lore) {

			if(i == lore.size() - 1) builder.append(s);
			else builder.append(s).append("\n");
			i++;
		}

		String playername = "%luckperms_prefix%%essentials_nickname%";
		String playernamecolor = PlaceholderAPI.setPlaceholders(player, playername);


		BaseComponent[] hoverEventComponents = new BaseComponent[]{
				new TextComponent(String.valueOf(builder))
		};

		TextComponent nonhover = new TextComponent(ChatColor.translateAlternateColorCodes('&',"&6SHOWOFF! " + playernamecolor + " &7shows off their "));
		TextComponent hover = new TextComponent(ChatColor.translateAlternateColorCodes('&', meta.getDisplayName()));
		hover.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, hoverEventComponents));

		nonhover.addExtra(hover);

		for(Player onlinePlayer : Bukkit.getOnlinePlayers()) {
			onlinePlayer.spigot().sendMessage(nonhover);
		}

		return false;
	}
}
