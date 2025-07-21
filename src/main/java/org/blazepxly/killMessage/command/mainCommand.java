package org.blazepxly.killMessage.command;

import org.blazepxly.killMessage.KillMessage;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

public class mainCommand implements CommandExecutor {

    private final KillMessage plugin;

    public mainCommand(KillMessage plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0) {
            sender.sendMessage(ChatColor.RED + "Do /help killmessage");
            return true;
        }

        // --SUBCOMMAND RELOAD--
        if ((args[0].equalsIgnoreCase("reload"))) {
            if (sender.hasPermission("killmessage.reload"))
                sender.sendMessage(ChatColor.GREEN + "Plugin KillMessage reloaded.");
            plugin.reloadConfig();
            plugin.loadData();
            plugin.loadMessages();
        }

        // --SUBCOMMAND SET--
        if (args[0].equalsIgnoreCase("set")) {
            if (!(sender instanceof Player)) return true;
            Player player = (Player) sender;

            if (args.length < 2) {
                player.sendMessage("§Usage: /killmessage set <style>");
                return true;
            }

            String style = args[1].toLowerCase();
            if (!plugin.customMessages.containsKey(style)) {
                player.sendMessage("§cStyle '" + style + "' not found.");
                return true;
            }
            String permission = "killmessage.style." + style;
            if (!player.hasPermission(permission)) {
                player.sendMessage("§cYou dont have access to '" + style + "'.");
                return true;
            }

            plugin.playerMessageStyles.put(player.getUniqueId(), style);

            if (!plugin.playersActived.contains(player.getUniqueId())) {
                plugin.playersActived.add(player.getUniqueId());
            }
            plugin.saveData();
            player.sendMessage("§aSUCCESS! your kill message has been set to: " + style);
        }

        if (args[0].equalsIgnoreCase("clear")) {
            if (!(sender instanceof Player)) return true;
            Player player = (Player) sender;
            UUID uuid = player.getUniqueId();

            plugin.playersActived.remove(uuid);
            plugin.playerMessageStyles.remove(uuid);

            plugin.saveData();

            player.sendMessage("§aYour kill message has been reset..");
            return true;
        }

        return true;
    }
}

