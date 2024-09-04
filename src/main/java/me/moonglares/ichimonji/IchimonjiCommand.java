package me.moonglares.ichimonji;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class IchimonjiCommand implements CommandExecutor {
    private IchimonjiPlugin plugin;

    public IchimonjiCommand(IchimonjiPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission("ichimonji.admin")) {
            sender.sendMessage(ChatColor.RED + "You do not have permission to execute this command.");
            return false;
        }

        if (args.length != 1) {
            sender.sendMessage(ChatColor.RED + "Usage: /" + label + " <enable|disable|reset>");
            return false;
        }

        String subCommand = args[0].toLowerCase();

        if (subCommand.equals("enable")) {
            plugin.setCraftingEnabled();
            sender.sendMessage(ChatColor.GREEN + "Ichimonji crafting has been Enabled.");
            return true;
        } else if (subCommand.equals("disable")) {
            plugin.setCraftingDisabled();
            sender.sendMessage(ChatColor.GREEN + "Ichimonji crafting has been Disabled.");
            return true;
        } else if (subCommand.equals("reset")) {
            plugin.resetIchimonjiCrafting();
            sender.sendMessage(ChatColor.GREEN + "Ichimonji crafting has been reset.");
            return true;
        } else {
            sender.sendMessage(ChatColor.RED + "Invalid sub-command. Usage: /" + label + " <enable|disable|reset>");
            return false;
        }
    }
}
