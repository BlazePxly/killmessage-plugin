package org.blazepxly.killMessage.command;

import org.blazepxly.killMessage.KillMessage;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.PlayerDeathEvent;

public class Listener implements org.bukkit.event.Listener {

    private final KillMessage plugin;

    public Listener(KillMessage plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onKill(PlayerDeathEvent event){
        Player killer = event.getEntity().getKiller();
        Player victim = event.getEntity();

        if (killer != null && plugin.playersActived.contains(killer.getUniqueId())){
            String chosenStyle = plugin.playerMessageStyles.get(killer.getUniqueId());
            if (chosenStyle != null){
                String messageTemplate = plugin.customMessages.get(chosenStyle);
                if (messageTemplate != null){
                    String finalMessage = messageTemplate
                            .replace("%killer%", killer.getName())
                            .replace("%victim%", victim.getName());
                    event.setDeathMessage(ChatColor.translateAlternateColorCodes('&', finalMessage));
                }
            }
        }
    }
}
