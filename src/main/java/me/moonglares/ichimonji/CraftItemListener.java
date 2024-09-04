package me.moonglares.ichimonji;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class CraftItemListener implements Listener {
    private IchimonjiPlugin plugin;

    public CraftItemListener(IchimonjiPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onCraft(CraftItemEvent event) {
        if (event.isCancelled()) {
            return;
        }

        ItemStack result = event.getRecipe().getResult();
        if (result != null && result.getType() == Material.DIAMOND_SWORD) {
            ItemMeta meta = result.getItemMeta();
            if (meta != null && meta.hasDisplayName() && ChatColor.stripColor(meta.getDisplayName()).equalsIgnoreCase("Ichimonji")) {
                if (!plugin.canCraftIchimonji()) {
                    event.setCancelled(true);
                    event.getWhoClicked().sendMessage(ChatColor.RED + "Ichimonji has already been crafted or crafting is disabled.");
                } else {
                    plugin.setIchimonjiCrafted(true);
                    // Broadcast message to all players on the server
                    Bukkit.broadcastMessage(ChatColor.AQUA + "" + ChatColor.BOLD + event.getWhoClicked().getName() + " has crafted the legendary Ichimonji!");
                }
            }
        }
    }
}
