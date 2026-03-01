package com.stormai.plugin;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class CommandHandler implements CommandExecutor {
    private final Main plugin;

    public CommandHandler(Main plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!command.getName().equalsIgnoreCase("lifesteal")) {
            return false;
        }

        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "This command can only be used by players!");
            return true;
        }

        Player player = (Player) sender;

        if (args.length == 0) {
            sender.sendMessage(ChatColor.GOLD + "LifeSteal Commands:");
            sender.sendMessage(ChatColor.YELLOW + "/lifesteal withdraw <amount> - Convert health to Heart items");
            return true;
        }

        if (args[0].equalsIgnoreCase("withdraw")) {
            if (args.length != 2) {
                sender.sendMessage(ChatColor.RED + "Usage: /lifesteal withdraw <amount>");
                return true;
            }

            int amount;
            try {
                amount = Integer.parseInt(args[1]);
            } catch (NumberFormatException e) {
                sender.sendMessage(ChatColor.RED + "Please enter a valid number!");
                return true;
            }

            double currentHealth = player.getHealth();
            double maxHealth = player.getAttribute(org.bukkit.attribute.Attribute.GENERIC_MAX_HEALTH).getValue();
            double healthToConvert = amount * 2.0; // Each heart is 2 HP

            if (amount <= 0) {
                sender.sendMessage(ChatColor.RED + "You must withdraw at least 1 heart!");
                return true;
            }

            if (healthToConvert > currentHealth - 2.0) { // Keep minimum 1 heart (2 HP)
                sender.sendMessage(ChatColor.RED + "You don't have enough health to convert!");
                return true;
            }

            // Create Heart items
            ItemStack heartItem = new ItemStack(Material.NETHER_STAR, amount);
            ItemMeta meta = heartItem.getItemMeta();
            meta.setDisplayName(ChatColor.RED + "Heart");
            heartItem.setItemMeta(meta);

            // Give items to player
            if (player.getInventory().firstEmpty() == -1) {
                sender.sendMessage(ChatColor.RED + "Your inventory is full!");
                return true;
            }

            player.getInventory().addItem(heartItem);
            player.sendMessage(ChatColor.GREEN + "Converted " + amount + " heart" + (amount > 1 ? "s" : "") + " to items!");

            // Reduce player's current health
            player.setHealth(currentHealth - healthToConvert);
            return true;
        }

        return true;
    }
}