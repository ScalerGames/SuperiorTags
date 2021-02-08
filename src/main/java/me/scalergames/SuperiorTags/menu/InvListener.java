package me.scalergames.SuperiorTags.menu;

import me.scalergames.SuperiorTags.Main;
import me.scalergames.SuperiorTags.files.Data;
import me.scalergames.SuperiorTags.files.Tags;
import me.scalergames.SuperiorTags.utils.Color;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class InvListener implements Listener {

    String prefix = Main.getInstance().getConfig().getString("Prefix");

    @EventHandler
    public void onClick(InventoryClickEvent event) {

        if (event.getView().getTitle().contains(Main.getInstance().getConfig().getString("Title"))) {


            Player p = (Player) event.getWhoClicked();

            if (event.getSlot() == 53) {
                //Close Inventory
                event.getWhoClicked().closeInventory();

            }

            ConfigurationSection tagssection = Tags.getTagsConfig().getConfigurationSection("SuperiorTags.tags");

            //Create New Page

            if (event.getSlot() == 50) {
                for (String tags : tagssection.getKeys(false)) {
                    String tagName = tags.substring( tags.lastIndexOf( "." ) + 1);
                    event.getClickedInventory().remove(Material.matchMaterial(Tags.getTagsConfig().getString(
                            "SuperiorTags.tags." + tagName + ".item")));
                }
                ItemStack item = new ItemStack(Material.IRON_HOE);
                ItemMeta meta = item.getItemMeta();

                item.setType(Material.IRON_HOE);
                meta.setDisplayName(Color.format("&9<- Back"));
                item.setItemMeta(meta);
                event.getClickedInventory().setItem(48, item);

                int slot = 0;
                List<String> taglore = new ArrayList<String>();
                for (String tags : tagssection.getKeys(false)) {
                    String tagName = tags.substring( tags.lastIndexOf( "." ) + 1);
                    if (Tags.getTagsConfig().getInt("SuperiorTags.tags." + tagName + ".page") == 2) {
                        if (p.hasPermission(Tags.getTagsConfig().getString("SuperiorTags.tags." + tagName + ".permission"))) {
                            if (!(slot >= 45)) {
                                try {
                                    item.setType(Material.matchMaterial(Tags.getTagsConfig()
                                            .getString("SuperiorTags.tags." + tagName + ".item")));
                                } catch (Exception e) {
                                    item.setType(Material.RED_STAINED_GLASS_PANE);
                                    taglore.add(Color.format("&4&lInvalid Item ID"));
                                }
                                taglore.clear();
                                meta.setDisplayName(Color.format(Main.getInstance().getConfig().getString("IdentifierColor") +
                                        Main.getInstance().getConfig().getString("Identifier")
                                        + Tags.getTagsConfig().getString("SuperiorTags.tags." + tagName + ".tag")));
                                taglore.add(Color.format(Tags.getTagsConfig().getString("SuperiorTags.tags." + tagName + ".description")));
                                taglore.add(tagName);
                                meta.setLore(taglore);
                                item.setItemMeta(meta);
                                event.getClickedInventory().setItem(slot, item);
                                slot++;

                            } else {

                                item.setType(Material.ARROW);
                                meta.setDisplayName(Color.format("&9Next ->"));
                                taglore.clear();
                                meta.setLore(taglore);
                                item.setItemMeta(meta);
                                event.getClickedInventory().setItem(50, item);

                            }
                        }
                    }
                }

            }

            //Create New Page End

            //Back a Page

            if (event.getSlot() == 48) {
                    for (String tags : tagssection.getKeys(false)) {
                        String tagName = tags.substring( tags.lastIndexOf( "." ) + 1);
                        event.getClickedInventory().remove(Material.matchMaterial(Tags.getTagsConfig().getString(
                                "SuperiorTags.tags." + tagName + ".item")));
                    }

                    ItemStack item = new ItemStack(Material.IRON_HOE);
                    ItemMeta meta = item.getItemMeta();
                    int slot = 0;

                    for (String tags : tagssection.getKeys(false)) {

                        String tagName = tags.substring( tags.lastIndexOf( "." ) + 1);
                        List<String> taglore = new ArrayList<String>();
                        if (p.hasPermission(Tags.getTagsConfig().getString("SuperiorTags.tags." + tagName + ".permission"))) {
                            if (!(slot >= 45)) {

                                try {
                                    item.setType(Material.matchMaterial(Tags.getTagsConfig()
                                            .getString("SuperiorTags.tags." + tagName + ".item")));
                                } catch (Exception e) {
                                    item.setType(Material.RED_STAINED_GLASS_PANE);
                                    taglore.add(Color.format("&4&lInvalid Item ID"));
                                }
                                meta.setDisplayName(Color.format(Main.getInstance().getConfig().getString("IdentifierColor") +
                                        Main.getInstance().getConfig().getString("Identifier")
                                        + Tags.getTagsConfig().getString("SuperiorTags.tags." + tagName + ".tag")));
                                taglore.add(Color.format(Tags.getTagsConfig().getString("SuperiorTags.tags." + tagName + ".description")));
                                taglore.add(tagName);
                                meta.setLore(taglore);
                                item.setItemMeta(meta);
                                event.getClickedInventory().setItem(slot, item);
                                slot++;


                            } else {

                                item.setType(Material.ARROW);
                                meta.setDisplayName(Color.format("&9Next ->"));
                                taglore.clear();
                                meta.setLore(taglore);
                                item.setItemMeta(meta);
                                event.getClickedInventory().setItem(50, item);

                            }

                        }

                    }

            }

            //Back a page End



            if (!(event.getCurrentItem() == null)) {

                event.setCancelled(true);

                if (event.getCurrentItem().getItemMeta().getDisplayName()
                        .contains(Main.getInstance().getConfig().getString("Identifier"))) {
                    List<String> itemlore = event.getCurrentItem().getItemMeta().getLore();
                    if (itemlore.size() >= 1) {
                        String name = itemlore.get(1);
                        Data.getDataConfig().set(p.getUniqueId() + ".tag", name);
                        Data.saveData();
                        p.closeInventory();
                        p.sendMessage(Color.format(prefix + "&aYou equipped the "
                                + Tags.getTagsConfig().getString("SuperiorTags.tags." + name + ".tag") + " &atag."));
                    }
                }

            }


        }
    }

}
