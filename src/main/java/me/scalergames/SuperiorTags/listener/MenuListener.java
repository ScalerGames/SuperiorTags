package me.scalergames.SuperiorTags.listener;

import me.scalergames.SuperiorTags.Main;
import me.scalergames.SuperiorTags.files.Data;
import me.scalergames.SuperiorTags.files.Gui;
import me.scalergames.SuperiorTags.files.Tags;
import me.scalergames.SuperiorTags.menu.MenuHandler;
import me.scalergames.SuperiorTags.utils.Color;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class MenuListener implements Listener {


    @EventHandler
    public void onClick(InventoryClickEvent event) {

        if (event.getInventory().equals(MenuHandler.menu)) {


            Player p = (Player) event.getWhoClicked();
            event.setCancelled(true);

            if (!(event.getCurrentItem() == null)) {

                //Close Button
                if (event.getSlot() == Gui.getGuiConfig().getInt("GUI.Layout.close-button.slot")) {
                    p.closeInventory();
                }

                if (event.getCurrentItem().getItemMeta().getDisplayName().contains(":")) {
                    String name = event.getCurrentItem().getItemMeta().getDisplayName();
                    String[] split = name.split(":");
                    String identifier = split[1].replace(" ", "");
                    if (Tags.getTagsConfig().getConfigurationSection("SuperiorTags.tags.").getKeys(false).contains(identifier)) {

                        Data.getDataConfig().set(p.getUniqueId() + ".tag", identifier);
                        Data.saveData();
                        p.sendMessage(Color.format("&cHELLO " + identifier));

                    } else {
                        p.sendMessage(Color.format("&cThat identifier is invalid."));
                    }
                }

                //Next Page Button
                if (event.getSlot() == Gui.getGuiConfig().getInt("GUI.Layout.next-page.slot")) {
                    if (MenuHandler.menu.firstEmpty() == -1) {
                        MenuHandler.page++;
                        MenuHandler.newPage(p);
                    }
                }

                //Previous Page Button
                if (event.getSlot() == Gui.getGuiConfig().getInt("GUI.Layout.previous-page.slot")) {
                    if (MenuHandler.page >= 2) {
                        MenuHandler.page--;
                        MenuHandler.newPage(p);
                    }
                }

            }
        }

    }


}
