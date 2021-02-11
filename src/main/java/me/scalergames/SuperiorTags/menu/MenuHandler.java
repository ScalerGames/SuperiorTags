package me.scalergames.SuperiorTags.menu;

import me.scalergames.SuperiorTags.Main;
import me.scalergames.SuperiorTags.files.Gui;
import me.scalergames.SuperiorTags.files.Tags;
import me.scalergames.SuperiorTags.utils.Color;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class MenuHandler {

    public static Inventory menu;

    public static Integer page = 0;

    public static void createMenu(Player p) {

        menu = Bukkit.createInventory(null, 54, Color.format(Gui.getGuiConfig().getString("GUI.Title")));
        p.openInventory(menu);
        page = 1;

        //Format The Gui
        ItemHandler.fp(p);


        //Format The Tags
        ItemHandler.items(p);
    }

    public static Integer slots = 4;

    public static void guiCheck() {

        slots = 4;

        for (Integer holder : Gui.getGuiConfig().getIntegerList("GUI.Layout.placeholder.slots")) {
            slots++;
        }

        slots = 53 - slots;
    }

    public static void newPage(Player p) {

        menu = Bukkit.createInventory(null, 54, Color.format(Gui.getGuiConfig().getString("GUI.Title"))
                + " (Page " + page + ")");
        p.openInventory(menu);

        ItemHandler.fp(p);

        ItemHandler.items(p);
    }

}
