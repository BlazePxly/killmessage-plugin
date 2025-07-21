package org.blazepxly.killMessage.command;

import org.blazepxly.killMessage.KillMessage;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class mainCommandTab implements TabCompleter {

    private final KillMessage plugin;

    public mainCommandTab(KillMessage plugin) {
        this.plugin = plugin;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (args.length == 1) {
            List<String> subcommands = new ArrayList<>();
            subcommands.add("reload");
            subcommands.add("set");
            subcommands.add("clear");
            return filter(subcommands, args[0]);
        }

        if (args.length == 2 && args[0].equalsIgnoreCase("set")) {
            return filter(new ArrayList<>(plugin.customMessages.keySet()), args[1]);
        }

        return Collections.emptyList();
    }

    private List<String> filter(List<String> input, String prefix) {
        List<String> result = new ArrayList<>();
        for (String item : input) {
            if (item.toLowerCase().startsWith(prefix.toLowerCase())) {
                result.add(item);
            }
        }
        return result;
    }
}
