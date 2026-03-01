# LifeSteal Plugin for Minecraft 1.21

A comprehensive LifeSteal plugin for PaperMC 1.21 with heart theft mechanics, craftable heart items, and health persistence.

## Features

- **Heart Theft**: Players gain +1 max health (1 heart) when killing another player, while the victim loses -1 max health
- **Health Limits**: Minimum 1 heart (2 HP), Maximum 20 hearts (40 HP)
- **Elimination**: Players dying at 1 heart are automatically banned with server broadcast
- **Heart Item**: Craftable item using Nether Star base that restores +1 max health when consumed
- **Health Conversion**: `/lifesteal withdraw` command to convert current health into physical Heart items
- **Persistence**: Health changes persist across player logout and server restarts

## Commands

- `/lifesteal withdraw <amount>` - Convert current health into Heart items (1 heart = 2 HP)

## Permissions

- `lifesteal.withdraw` - Allows player to withdraw health as items (default: true)

## Installation

1. Build the plugin using Maven: `mvn clean package`
2. Copy the generated JAR file from `target/lifesteal-1.0.jar` to your server's `plugins/` directory
3. Restart your server

## Configuration

The plugin automatically creates a config.yml file with default settings. No manual configuration is required for basic functionality.

## Crafting Recipe