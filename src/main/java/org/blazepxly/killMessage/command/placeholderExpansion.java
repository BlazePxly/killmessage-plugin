package org.blazepxly.killMessage.command;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.blazepxly.killMessage.KillMessage;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class placeholderExpansion extends PlaceholderExpansion {

    private final KillMessage plugin;

    public placeholderExpansion(KillMessage plugin) {
        this.plugin = plugin;
    }

    @Override
    public String getIdentifier() {
        return "killMessage";
    }

    @Override
    public String getAuthor() {
        return "BlazePxly";
    }

    @Override
    public String getVersion() {
        return "1.2";
    }

    @Override
    public boolean canRegister() {
        return true;
    }

    @Override
    public boolean persist() {
        return true;
    }

    @Override
    public @Nullable String onPlaceholderRequest(Player player, @NotNull String params) {
        if (player == null) return "";

        if (params.equalsIgnoreCase("selected")) {
            String style = plugin.playerMessageStyles.get(player.getUniqueId());
            return (style != null) ? style : "None";
        }

        return null;

    }
}
