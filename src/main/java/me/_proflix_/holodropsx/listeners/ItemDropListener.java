package me._proflix_.holodropsx.listeners;

import me._proflix_.holodropsx.Main;
import me._proflix_.holodropsx.util.Glow;
import me._proflix_.holodropsx.util.Strings;
import org.bukkit.ChatColor;
import org.bukkit.entity.Item;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ItemSpawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ItemDropListener implements Listener {
    
    
    @EventHandler
    public void itemDrop(ItemSpawnEvent e) {
        Item drop = e.getEntity();
        if (Main.m.settings.isWorldEnabled(drop.getWorld().getName())) {
            ItemStack item = drop.getItemStack();
            if (item.hasItemMeta()) {
                if (checkBlacklistLore(item.getItemMeta())) {
                    return;
                }
                if (Strings.hasWatermark(item)) {
                    Strings.removeWatermark(item);
                }
            }
            if (!Main.m.settings.getProtectedDrops().containsKey(drop)) {
                String name = Strings.makeName(drop, item.getAmount(), "", 0);
                drop.setCustomName(name);
            }
            drop.setCustomNameVisible(true);
            
            if (Main.m.settings.getItemGlow()) {
                String rawName = Strings.makeItemName(drop);
                if (Main.m.settings.isGlowlisted(rawName)) { // check the raw name
                    drop.setGlowing(true);
                    if (Main.m.settings.getGlowColor()) {
                        ChatColor color = Glow.getColor(rawName);
                        Glow.setGlowColor(color, drop);
                    }
                }
            }
        }
    }
    
    private boolean checkBlacklistLore(ItemMeta meta) {
        /*
         * hardcoded solution to stop display items in Slabbo showing their name
         */
        
        for (String s : meta.getDisplayName()) {
            if (s.contains("Slabbo Item")) {
                return true;
            }
        }
        return false;
    }
    
}
