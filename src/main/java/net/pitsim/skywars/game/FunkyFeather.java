package net.pitsim.skywars.game;

import de.tr7zw.nbtapi.NBTItem;
import dev.kyro.arcticapi.misc.AOutput;
import dev.kyro.arcticapi.misc.AUtil;
import net.pitsim.skywars.PitSim;
import net.pitsim.skywars.controllers.ItemManager;
import net.pitsim.skywars.controllers.objects.PitPlayer;
import net.pitsim.skywars.enums.NBTTag;
import net.pitsim.skywars.misc.Misc;
import net.pitsim.skywars.misc.Sounds;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;

public class FunkyFeather {
    public static ItemStack getFeather(int amount) {
        ItemStack feather = new ItemStack(Material.FEATHER);
        ItemMeta meta = feather.getItemMeta();
        meta.setDisplayName(ChatColor.DARK_AQUA + "Funky Feather");
        List<String> lore = new ArrayList<>();
        lore.add(ChatColor.YELLOW + "Special item");
        lore.add(ChatColor.GRAY + "teleports you to a safe location,");
        lore.add(ChatColor.GRAY + "but gets consumed on void death if");
        lore.add(ChatColor.GRAY + "in your hotbar.");
        meta.setLore(lore);
        feather.setItemMeta(meta);
        feather.setAmount(amount);

        feather = ItemManager.enableDropConfirm(feather);

        NBTItem nbtItem = new NBTItem(feather);
        nbtItem.setBoolean(NBTTag.IS_FEATHER.getRef(), true);
       return nbtItem.getItem();
    }

    public static boolean useFeather(Player player, boolean isDivine) {
        if(isDivine) return false;

        for(int i = 0; i < 9; i++) {
            ItemStack itemStack = player.getInventory().getItem(i);
            if(Misc.isAirOrNull(itemStack)) continue;
            NBTItem nbtItem = new NBTItem(itemStack);
            if(nbtItem.hasKey(NBTTag.IS_FEATHER.getRef())) {
                AOutput.send(player, "&3&lFUNKY FEATHER! &7Teleported to last stable location.");
                if(itemStack.getAmount() > 1) itemStack.setAmount(itemStack.getAmount() - 1);
                else player.getInventory().setItem(i, null);
                Sounds.FUNKY_FEATHER.play(player);

                PitPlayer pitPlayer = PitPlayer.getPitPlayer(player);
                return true;
            }
        }
        return false;
    }
}
