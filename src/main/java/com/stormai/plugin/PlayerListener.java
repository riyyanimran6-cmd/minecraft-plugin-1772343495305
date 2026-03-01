package com.stormai.plugin;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class PlayerListener implements Listener {
    private final Main plugin;

    public PlayerListener(Main plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerDeath(EntityDeathEvent event) {
        if (event.getEntity() instanceof Player victim && victim.getKiller() instanceof Player killer) {
            // Get current max health values
            double victimMaxHealth = victim.getAttribute(org.bukkit.attribute.Attribute.GENERIC_MAX_HEALTH).getValue();
            double killerMaxHealth = killer.getAttribute(org.bukkit.attribute.Attribute.GENERIC_MAX_HEALTH).getValue();

            // Check if victim is at minimum health (1 heart = 2 HP)
            if (victimMaxHealth <= 2.0) {
                // Eliminate the player (ban for this example)
                Bukkit.getServer().banPlayer(victim.getName(), "Eliminated from LifeSteal");
                Bukkit.broadcastMessage(ChatColor.RED + victim.getName() + " has been eliminated from LifeSteal!");
                return;
            }

            // Check if killer is at maximum health (20 hearts = 40 HP)
            if (killerMaxHealth < 40.0) {
                // Transfer 1 heart (2 HP) from victim to killer
                victim.getAttribute(org.bukkit.attribute.Attribute.GENERIC_MAX_HEALTH).setBaseValue(victimMaxHealth - 2.0);
                killer.getAttribute(org.bukkit.attribute.Attribute.GENERIC_MAX_HEALTH).setBaseValue(killerMaxHealth + 2.0);

                Bukkit.broadcastMessage(ChatColor.GOLD + killer.getName() + " stole 1 heart from " + victim.getName() + "!");
            }
        }
    }

    @EventHandler
    public void onPlayerConsume(PlayerItemConsumeEvent event) {
        ItemStack item = event.getItem();
        if (item.getType() == Material.NETHER_STAR && item.hasItemMeta()) {
            ItemMeta meta = item.getItemMeta();
            if (meta.hasDisplayName() && meta.getDisplayName().equals(ChatColor.RED + "Heart")) {
                Player player = event.getPlayer();
                double currentMaxHealth = player.getAttribute(org.bukkit.attribute.Attribute.GENERIC_MAX_HEALTH).getValue();

                if (currentMaxHealth < 40.0) {
                    // Increase max health by 1 heart (2 HP)
                    player.getAttribute(org.bukkit.attribute.Attribute.GENERIC_MAX_HEALTH).setBaseValue(currentMaxHealth + 2.0);
                    player.sendMessage(ChatColor.GREEN + "You gained 1 heart!");
                    event.setCancelled(true);
                    item.setAmount(item.getAmount() - 1);
                } else {
                    player.sendMessage(ChatColor.RED + "You already have maximum health!");
                }
            }
        }
    }

    @EventHandler
    public void onPlayerDamage(EntityDamageEvent event) {
        if (event.getEntity() instanceof Player player) {
            double maxHealth = player.getAttribute(org.bukkit.attribute.Attribute.GENERIC_MAX_HEALTH).getValue();
            double currentHealth = player.getHealth();

            // Ensure player doesn't die from max health reduction
            if (currentHealth > maxHealth) {
                player.setHealth(maxHealth);
            }
        }
    }
}