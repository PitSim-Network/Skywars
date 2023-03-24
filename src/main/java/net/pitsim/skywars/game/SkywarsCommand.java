package net.pitsim.skywars.game;

import dev.kyro.arcticapi.misc.AOutput;
import net.pitsim.skywars.PitSim;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SkywarsCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(!(sender instanceof Player)) return false;
		Player player = (Player) sender;
		if(args.length == 0) return false;

		String command = args[0].toLowerCase();
		if(command.equals("start")) {
			if(!player.hasPermission("skywars.start")) {
				AOutput.send(player, "&c&lERROR!&7 You do not have permission for that");
				return false;
			}

			QueueManager.override = true;
			QueueManager.countdown();
			QueueManager.setSeconds(10);
			QueueManager.setMinutes(0);
		}

		if(!player.isOp()) return false;

		if(command.equals("load")) {
			PitSim.INSTANCE.onInit();
		}

		return false;
	}
}
