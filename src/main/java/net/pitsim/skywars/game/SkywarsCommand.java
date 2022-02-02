package net.pitsim.skywars.game;

import dev.kyro.arcticapi.misc.AOutput;
import net.pitsim.skywars.PitSim;
import net.pitsim.skywars.controllers.CombatManager;
import net.pitsim.skywars.controllers.DamageManager;
import net.pitsim.skywars.controllers.SpawnManager;
import net.pitsim.skywars.controllers.objects.PitEnchant;
import net.pitsim.skywars.controllers.objects.PitPlayer;
import net.pitsim.skywars.events.AttackEvent;
import net.pitsim.skywars.events.OofEvent;
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

public class SkywarsCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if(args.length < 1) return false;

        if(args[0].equals("load")) {
            PitSim.INSTANCE.onInit();
        }

        return false;
    }
}
