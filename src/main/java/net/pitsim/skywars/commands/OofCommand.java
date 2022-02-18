package net.pitsim.skywars.commands;

import net.pitsim.skywars.controllers.objects.PitPlayer;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class OofCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		if(!(sender instanceof Player)) return false;
		Player player = (Player) sender;

		PitPlayer pitPlayer = PitPlayer.getPitPlayer(player);
		Bukkit.broadcastMessage(pitPlayer.stats.wins + "");
		Bukkit.broadcastMessage(pitPlayer.stats.kills + "");
		pitPlayer.stats.wins++;
		pitPlayer.stats.kills = pitPlayer.stats.kills + 2;
		pitPlayer.stats.save();


		;

//        if(SpawnManager.isInSpawn(player.getLocation())) {
//
//            AOutput.send(player, "&c&lNOPE! &7Cant /oof in spawn!");
//            Sounds.ERROR.play(player);
//        } else {
//
//            if(!CombatManager.taggedPlayers.containsKey(player.getUniqueId())) {
//                DamageManager.death(player);
//                OofEvent oofEvent = new OofEvent(player);
//                Bukkit.getPluginManager().callEvent(oofEvent);
//                return false;
//            }
//
//            PitPlayer pitPlayer = PitPlayer.getPitPlayer(player);
//            UUID attackerUUID = pitPlayer.lastHitUUID;
//            for(Player onlinePlayer : Bukkit.getOnlinePlayers()) {
//                if(onlinePlayer.getUniqueId().equals(attackerUUID)) {
//
//                    Map<PitEnchant, Integer> attackerEnchant = new HashMap<>();
//                    Map<PitEnchant, Integer> defenderEnchant = new HashMap<>();
//                    EntityDamageByEntityEvent ev = new EntityDamageByEntityEvent(onlinePlayer, player, EntityDamageEvent.DamageCause.CUSTOM, 0);
//                    AttackEvent attackEvent = new AttackEvent(ev, attackerEnchant, defenderEnchant, false);
//
//
//                    DamageManager.kill(attackEvent, onlinePlayer, player, false);
//                    return false;
//                }
//            }
//            DamageManager.death(player);
//            OofEvent oofEvent = new OofEvent(player);
//            Bukkit.getPluginManager().callEvent(oofEvent);
//        }
		return false;
	}
}
