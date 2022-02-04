package net.pitsim.skywars.commands;

import dev.kyro.arcticapi.misc.AOutput;
import dev.kyro.arcticapi.misc.AUtil;
import net.pitsim.skywars.controllers.CombatManager;
import net.pitsim.skywars.controllers.DamageManager;
import net.pitsim.skywars.controllers.SpawnManager;
import net.pitsim.skywars.controllers.objects.PitEnchant;
import net.pitsim.skywars.controllers.objects.PitPlayer;
import net.pitsim.skywars.enums.MysticType;
import net.pitsim.skywars.events.AttackEvent;
import net.pitsim.skywars.events.OofEvent;
import net.pitsim.skywars.game.MysticFactory;
import net.pitsim.skywars.misc.Sounds;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class OofCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if(!(sender instanceof Player)) return false;
        Player player = (Player) sender;

        String itemType = args[0];
        int tier = Integer.parseInt(args[1]);

        if(itemType.equals("sword")) AUtil.giveItemSafely(player, MysticFactory.createItem(MysticType.SWORD, tier));
        else if(itemType.equals("bow")) AUtil.giveItemSafely(player, MysticFactory.createItem(MysticType.BOW, tier));
        else AUtil.giveItemSafely(player, MysticFactory.createItem(MysticType.PANTS, tier))


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
