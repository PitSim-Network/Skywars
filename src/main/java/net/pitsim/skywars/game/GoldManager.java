package net.pitsim.skywars.game;

import net.pitsim.skywars.controllers.objects.SkywarsPerk;
import net.pitsim.skywars.game.objects.SkywarsChest;
import net.pitsim.skywars.misc.Misc;
import net.pitsim.skywars.misc.Sounds;
import org.bukkit.Material;
import org.bukkit.block.Chest;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import java.text.DecimalFormat;
import java.util.*;

public class GoldManager implements Listener {

    public static Map<Player, ArrayList<SkywarsChest>> openedChests = new HashMap<>();
    public static Map<Player, Integer> gold = new HashMap<>();

    @EventHandler(priority = EventPriority.LOW)
    public void onOpen(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if(event.getAction() != Action.RIGHT_CLICK_BLOCK) return;
        if(event.getClickedBlock().getType() != Material.CHEST) return;

        SkywarsChest chest = SkywarsChest.getChest(event.getClickedBlock().getLocation());
        if(chest == null) return;

        ArrayList<SkywarsChest> playerChests = openedChests.get(player);
        if(openedChests.containsKey(player) && playerChests.contains(chest)) return;

        if(!openedChests.containsKey(player)) {
            openedChests.put(player, new ArrayList<>(Collections.singletonList(chest)));
        } else playerChests.add(chest);

        int obtainedGold = 50 + (150 * chest.tier);
        boolean banker = false;
        if(SkywarsPerk.hasPerkEquipped(player, "banker") && SkywarsPerk.getPerkTier(player, "banker") != 0) {
            if(!GameClock.refill && openedChests.get(player).size() <= SkywarsPerk.getPerkTier(player, "banker")) {
                obtainedGold *= 2;
                banker = true;
            }
        }

        if(!gold.containsKey(player)) {
            gold.put(player, obtainedGold);
        } else gold.put(player, gold.get(player) + obtainedGold);

        Sounds.ASSIST.play(player);
        String message = "&6+" + new DecimalFormat("###,###,###").format(obtainedGold) + "g (" + new DecimalFormat("###,###,###").format(gold.get(player)) + "g)";
        if(banker) message += " &a&lBANKER &e2x";
        Misc.sendActionBar(player, message);


    }
}
