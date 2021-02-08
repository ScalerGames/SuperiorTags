package me.scalergames.SuperiorTags.listener;

import me.scalergames.SuperiorTags.files.Data;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class OnJoin implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {

        Player p = event.getPlayer();

        if (!Data.getDataConfig().contains(p.getUniqueId().toString())) {
            Data.getDataConfig().set(p.getUniqueId() + ".tag", "none");
            Data.saveData();
        }

    }

}
