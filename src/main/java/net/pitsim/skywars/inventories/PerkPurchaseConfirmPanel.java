package net.pitsim.skywars.inventories;

import dev.kyro.arcticapi.data.APlayerData;
import dev.kyro.arcticapi.gui.AGUI;
import dev.kyro.arcticapi.gui.AGUIPanel;
import dev.kyro.arcticapi.misc.AOutput;
import dev.kyro.arcticapi.misc.AUtil;
import net.luckperms.api.model.user.UserManager;
import net.luckperms.api.node.Node;
import net.pitsim.skywars.controllers.objects.PitPlayer;
import net.pitsim.skywars.controllers.objects.SkywarsPerk;
import net.pitsim.skywars.misc.Sounds;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PerkPurchaseConfirmPanel extends AGUIPanel {

    PitPlayer pitPlayer = PitPlayer.getPitPlayer(player);
    public PerkPurchaseGUI perkPurchaseGUI;
    public PerkPurchaseConfirmPanel(AGUI gui) {
        super(gui);
        perkPurchaseGUI = (PerkPurchaseGUI) gui;

    }

    @Override
    public String getName() {
        return "Are you sure?";
    }

    @Override
    public int getRows() {
        return 3;
    }

    @Override
    public void onClick(InventoryClickEvent event) {
        int slot = event.getSlot();

        if(event.getClickedInventory().getHolder() == this) {

            if(slot == 11) {
                SkywarsPerk clickedPerk = perkPurchaseGUI.clickedPerk;
                if(clickedPerk == null) {
                    openPreviousGUI();
                    return;
                }
                int cost = clickedPerk.cost.get(SkywarsPerk.getPerkTier(player, clickedPerk.refName));
                pitPlayer.stats.coins -= cost;
                pitPlayer.stats.save();
                pitPlayer.purchasedPerks.perkTiers.put(clickedPerk.refName, pitPlayer.purchasedPerks.perkTiers.get(clickedPerk.refName) + 1);
                pitPlayer.purchasedPerks.save();

                openPreviousGUI();
                Sounds.RENOWN_SHOP_PURCHASE.play(player);
                perkPurchaseGUI.clickedPerk = null;

                AOutput.send(player, ChatColor.translateAlternateColorCodes('&', "&a&lPURCHASE! &6" + clickedPerk.name + " " + AUtil.toRoman(SkywarsPerk.getPerkTier(player, clickedPerk.refName))));

                Sounds.RENOWN_SHOP_PURCHASE.play(player);
            }

            if(slot == 15)  {
                perkPurchaseGUI.clickedPerk = null;
                openPreviousGUI();
            }
            updateInventory();
        }
        updateInventory();
    }

    @Override
    public void onOpen(InventoryOpenEvent event) {
        ItemStack confirm = new ItemStack(Material.STAINED_CLAY, 1, (short) 13);
        ItemMeta confirmMeta = confirm.getItemMeta();
        confirmMeta.setDisplayName(ChatColor.GREEN + "Confirm");
        List<String> confirmLore = new ArrayList<>();
        SkywarsPerk clickedPerk = perkPurchaseGUI.clickedPerk;
        int cost = clickedPerk.cost.get(SkywarsPerk.getPerkTier(player, clickedPerk.refName));
        confirmLore.add(ChatColor.translateAlternateColorCodes('&', "&7Purchasing: &6" + clickedPerk.name + " " +
                AUtil.toRoman(SkywarsPerk.getPerkTier(player, clickedPerk.refName) + 1)));
        DecimalFormat format = new DecimalFormat("###,###,###");
        confirmLore.add(ChatColor.translateAlternateColorCodes('&', "&7Cost: &6" + format.format(cost) + " Coins"));

        confirmMeta.setLore(confirmLore);
        confirm.setItemMeta(confirmMeta);

        getInventory().setItem(11, confirm);



        ItemStack cancel = new ItemStack(Material.STAINED_CLAY, 1, (short) 14);
        ItemMeta cancelMeta = cancel.getItemMeta();
        cancelMeta.setDisplayName(ChatColor.RED + "Cancel");
        cancelMeta.setLore(Collections.singletonList(ChatColor.GRAY + "Return to previous menu."));
        cancel.setItemMeta(cancelMeta);

        getInventory().setItem(15, cancel);
    }

    @Override
    public void onClose(InventoryCloseEvent event) {
        perkPurchaseGUI.clickedPerk = null;
    }

}
