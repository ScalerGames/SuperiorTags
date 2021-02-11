package me.scalergames.SuperiorTags.commands;

import me.scalergames.SuperiorTags.Main;
import me.scalergames.SuperiorTags.menu.MenuHandler;
import me.scalergames.SuperiorTags.utils.Color;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class test implements CommandExecutor {

    public boolean onCommand(CommandSender s, Command cmd, String label, String[] args) {

        if (label.equalsIgnoreCase("test")) {
            if (s instanceof Player) {
                Player p = (Player) s;
                    MenuHandler.createMenu(p);
            }
        }

        return false;
    }

}
