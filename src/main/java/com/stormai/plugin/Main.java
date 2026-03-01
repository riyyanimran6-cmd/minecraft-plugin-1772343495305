package com.stormai.plugin;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
    @Override
    public void onEnable() {
        getLogger().info("LifeSteal plugin enabled!");

        // Register the event listener
        getServer().getPluginManager().registerEvents(new PlayerListener(this), this);

        // Register the command executor
        getCommand("lifesteal").setExecutor(new CommandHandler(this));

        // Initialize the configuration
        saveDefaultConfig();
        getConfig().options().copyDefaults(true);
        saveConfig();

        // Register the custom recipe
        new RecipeManager(this).registerHeartRecipe();
    }

    @Override
    public void onDisable() {
        getLogger().info("LifeSteal plugin disabled!");
    }
}