package com.stormai.plugin;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

public class RecipeManager {
    private final JavaPlugin plugin;

    public RecipeManager(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    public void registerHeartRecipe() {
        // Create the Heart item
        ItemStack heartItem = new ItemStack(Material.NETHER_STAR, 1);
        ItemMeta meta = heartItem.getItemMeta();
        meta.setDisplayName("§cHeart");
        heartItem.setItemMeta(meta);

        // Create the recipe
        NamespacedKey key = new NamespacedKey(plugin, "heart");
        ShapedRecipe recipe = new ShapedRecipe(key, heartItem);

        recipe.shape("GGG", "GNH", "GGG");
        recipe.setIngredient('G', Material.GOLD_BLOCK);
        recipe.setIngredient('N', Material.NETHER_STAR);

        plugin.getServer().addRecipe(recipe);
    }
}