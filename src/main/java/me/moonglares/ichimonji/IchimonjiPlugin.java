package me.moonglares.ichimonji;

import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;

public class IchimonjiPlugin extends JavaPlugin {
    private boolean isCraftingEnabled = true;
    private boolean isIchimonjiCrafted = false;

    @Override
    public void onEnable() {
        getCommand("ichimonji").setExecutor(new IchimonjiCommand(this));
        getServer().getPluginManager().registerEvents(new CraftItemListener(this), this);
        getServer().getPluginManager().registerEvents(new IchimonjiListener(this), this);
        registerIchimonjiRecipe();
    }

    public boolean canCraftIchimonji() {
        return isCraftingEnabled && !isIchimonjiCrafted;
    }

    public void setCraftingEnabled() {
        isCraftingEnabled = true;
    }

    public void setCraftingDisabled() {
        isCraftingEnabled = false;
    }

    public void resetIchimonjiCrafting() {
        isIchimonjiCrafted = false;
    }
    // New method to set the crafted state of Ichimonji
    public void setIchimonjiCrafted(boolean crafted) {
        this.isIchimonjiCrafted = crafted;
    }
    private void registerIchimonjiRecipe() {
        ItemStack ichimonji = new ItemStack(Material.DIAMOND_SWORD);
        ItemMeta meta = ichimonji.getItemMeta();
        if (meta != null) {
            meta.setDisplayName(ChatColor.AQUA + "Ichimonji");
            ichimonji.setItemMeta(meta);
        }

        ShapedRecipe recipe = new ShapedRecipe(ichimonji);
        recipe.shape("IFI", "SDS", "IWI");
        recipe.setIngredient('I', Material.INK_SACK);
        recipe.setIngredient('S', Material.INK_SACK);
        recipe.setIngredient('F', Material.FERMENTED_SPIDER_EYE);
        recipe.setIngredient('D', Material.DIAMOND_SWORD);
        recipe.setIngredient('W', Material.WOOL, (short) 15);

        Bukkit.addRecipe(recipe);
    }
}
