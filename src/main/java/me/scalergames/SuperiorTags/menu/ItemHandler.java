package me.scalergames.SuperiorTags.menu;


import me.arcaniax.hdb.api.HeadDatabaseAPI;
import me.arcaniax.hdb.object.head.Head;
import me.clip.placeholderapi.PlaceholderAPI;
import me.scalergames.SuperiorTags.Main;
import me.scalergames.SuperiorTags.files.Gui;
import me.scalergames.SuperiorTags.files.Tags;
import me.scalergames.SuperiorTags.utils.Color;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ItemHandler {


    public static void items(Player p) {

        ItemStack item = new ItemStack(Material.PAPER);
        ItemMeta meta = item.getItemMeta();

        ConfigurationSection tagssection = Tags.getTagsConfig().getConfigurationSection("SuperiorTags.tags");

        if (MenuHandler.page == 1) {

            for (String tags : tagssection.getKeys(false)) {

                String tagName = tags.substring(tags.lastIndexOf(".") + 1);

                if (p.hasPermission(Tags.getTagsConfig().getString("SuperiorTags.tags." + tagName + ".permission"))) {

                    int slot = MenuHandler.menu.firstEmpty();

                    if (!(slot == -1)) {

                        //Head Data Base Support
                        if (Tags.getTagsConfig().getString("SuperiorTags.tags." + tagName + ".item").contains("hdb-")) {

                            if (Bukkit.getPluginManager().getPlugin("HeadDatabase") != null) {
                                HeadDatabaseAPI api = new HeadDatabaseAPI();

                                String hdb = Tags.getTagsConfig().getString("SuperiorTags.tags." + tagName + ".item");
                                String[] split = hdb.split("-");
                                String id = split[1];

                                try {
                                    ItemStack head = api.getItemHead(id);
                                    ItemMeta headmeta = head.getItemMeta();
                                    headmeta.setDisplayName(Color.format(Main.getInstance().getConfig().getString("Identifier")+ ": " + tagName));
                                    List<String> headlore = new ArrayList<String>();
                                    headlore.add(Color.format(Tags.getTagsConfig().getString("SuperiorTags.tags." + tagName + ".tag")));
                                    for (String looplore : Tags.getTagsConfig().getStringList("SuperiorTags.tags." + tagName + ".lore")) {
                                        headlore.add(Color.format(looplore));
                                    }
                                    headmeta.setLore(headlore);
                                    head.setItemMeta(headmeta);

                                    MenuHandler.menu.setItem(slot, head);
                                } catch (NullPointerException e) {
                                    ItemStack head = new ItemStack(Material.RED_STAINED_GLASS_PANE);
                                    ItemMeta headmeta = head.getItemMeta();
                                    headmeta.setDisplayName(Color.format(Main.getInstance().getConfig().getString("Identifier")+ ": " + tagName));
                                    List<String> headlore = new ArrayList<String>();
                                    headlore.add(Color.format(Tags.getTagsConfig().getString("SuperiorTags.tags." + tagName + ".tag")));
                                    headlore.add(Color.format("&4&lInvalid HDB ID"));
                                    for (String looplore : Tags.getTagsConfig().getStringList("SuperiorTags.tags." + tagName + ".lore")) {
                                        headlore.add(Color.format(looplore));
                                    }
                                    headmeta.setLore(headlore);
                                    head.setItemMeta(headmeta);

                                    MenuHandler.menu.setItem(slot, head);
                                }


                            }

                        }
                        //Normal Blocks
                        else {

                            List<String> lore = new ArrayList<String>();
                            try {
                                item.setType(Material.matchMaterial(Tags.getTagsConfig()
                                        .getString("SuperiorTags.tags." + tagName + ".item")));
                            } catch (Exception e) {
                                item.setType(Material.RED_STAINED_GLASS_PANE);
                                lore.add(Color.format("&4&lInvalid Material ID"));
                            }

                            //Display name
                            meta.setDisplayName(Color.format(Main.getInstance().getConfig().getString("Identifier")+ ": " + tagName));

                            //Lore
                            lore.add(Color.format(Tags.getTagsConfig().getString("SuperiorTags.tags." + tagName + ".tag")));
                            for (String alllore : Tags.getTagsConfig().getStringList("SuperiorTags.tags." + tagName + ".lore")) {
                                lore.add(Color.format(alllore));
                            }

                            //Enchants BETA
                            if (Tags.getTagsConfig().isList("SuperiorTags.tags." + tagName + ".enchantments")) {

                                for (String enchants : Tags.getTagsConfig().getStringList("SuperiorTags.tags." + tagName + ".enchantments")) {

                                    String[] split = enchants.split(":");
                                    String enchant = split[0];
                                    int level = Integer.parseInt(split[1]);

                                    try {
                                        meta.addEnchant(Enchantment.getByName(enchant), level, true);
                                    } catch (Exception e) {
                                        lore.add(Color.format("&4&lInvalid Enchantment"));
                                    }

                                }

                            }

                            meta.setLore(lore);
                            item.setItemMeta(meta);
                            MenuHandler.menu.setItem(slot, item);
                        }
                    }

                }
            }

        } else {

            for (String tags : tagssection.getKeys(false)) {

                String tagName = tags.substring( tags.lastIndexOf( "." ) + 1);
                if (Tags.getTagsConfig().getInt("SuperiorTags.tags." + tagName + ".page") == MenuHandler.page) {

                    if (p.hasPermission(Tags.getTagsConfig().getString("SuperiorTags.tags." + tagName + ".permission"))) {

                        int slot = MenuHandler.menu.firstEmpty();

                        if (!(slot == -1)) {

                            //Head Data Base Support
                            if (Tags.getTagsConfig().getString("SuperiorTags.tags." + tagName + ".item").contains("hdb-")) {

                                if (Bukkit.getPluginManager().getPlugin("HeadDatabase") != null) {
                                    HeadDatabaseAPI api = new HeadDatabaseAPI();

                                    String hdb = Tags.getTagsConfig().getString("SuperiorTags.tags." + tagName + ".item");
                                    String[] split = hdb.split("-");
                                    String id = split[1];

                                    try {
                                        ItemStack head = api.getItemHead(id);
                                        ItemMeta headmeta = head.getItemMeta();
                                        headmeta.setDisplayName(Color.format(Main.getInstance().getConfig().getString("Identifier")+ ": " + tagName));
                                        List<String> headlore = new ArrayList<String>();
                                        headlore.add(Color.format(Tags.getTagsConfig().getString("SuperiorTags.tags." + tagName + ".tag")));
                                        for (String looplore : Tags.getTagsConfig().getStringList("SuperiorTags.tags." + tagName + ".lore")) {
                                            headlore.add(Color.format(looplore));
                                        }
                                        headmeta.setLore(headlore);
                                        head.setItemMeta(headmeta);

                                        MenuHandler.menu.setItem(slot, head);
                                    } catch (NullPointerException e) {
                                        ItemStack head = new ItemStack(Material.RED_STAINED_GLASS_PANE);
                                        ItemMeta headmeta = head.getItemMeta();
                                        headmeta.setDisplayName(Color.format(Main.getInstance().getConfig().getString("Identifier")+ ": " + tagName));
                                        List<String> headlore = new ArrayList<String>();
                                        headlore.add(Color.format(Tags.getTagsConfig().getString("SuperiorTags.tags." + tagName + ".tag")));
                                        headlore.add(Color.format("&4&lInvalid HDB ID"));
                                        for (String looplore : Tags.getTagsConfig().getStringList("SuperiorTags.tags." + tagName + ".lore")) {
                                            headlore.add(Color.format(looplore));
                                        }
                                        headmeta.setLore(headlore);
                                        head.setItemMeta(headmeta);

                                        MenuHandler.menu.setItem(slot, head);
                                    }


                                }

                            }
                            //Normal Blocks
                            else {

                                List<String> lore = new ArrayList<String>();
                                try {
                                    item.setType(Material.matchMaterial(Tags.getTagsConfig()
                                            .getString("SuperiorTags.tags." + tagName + ".item")));
                                } catch (Exception e) {
                                    item.setType(Material.RED_STAINED_GLASS_PANE);
                                    lore.add(Color.format("&4&lInvalid Material ID"));
                                }

                                //Display name
                                meta.setDisplayName(Color.format(Main.getInstance().getConfig().getString("Identifier")+ ": " + tagName));

                                //Lore
                                lore.add(Color.format(Tags.getTagsConfig().getString("SuperiorTags.tags." + tagName + ".tag")));
                                for (String alllore : Tags.getTagsConfig().getStringList("SuperiorTags.tags." + tagName + ".lore")) {
                                    lore.add(Color.format(alllore));
                                }

                                //Enchants BETA
                                if (Tags.getTagsConfig().isList("SuperiorTags.tags." + tagName + ".enchantments")) {

                                    for (String enchants : Tags.getTagsConfig().getStringList("SuperiorTags.tags." + tagName + ".enchantments")) {

                                        String[] split = enchants.split(":");
                                        String enchant = split[0];
                                        int level = Integer.parseInt(split[1]);

                                        try {
                                            meta.addEnchant(Enchantment.getByName(enchant), level, true);
                                        } catch (Exception e) {
                                            lore.add(Color.format("&4&lInvalid Enchantment"));
                                        }

                                    }

                                }

                                meta.setLore(lore);
                                item.setItemMeta(meta);
                                MenuHandler.menu.setItem(slot, item);
                            }
                        }

                    }

                }

            }

        }



    }


    public static void fp(Player p) {

        ItemStack item = new ItemStack(Material.matchMaterial(Gui.getGuiConfig().getString("GUI.Layout.placeholder.type")));
        ItemMeta meta = item.getItemMeta();

        //Placeholders
        meta.setDisplayName(Color.format(Gui.getGuiConfig().getString("GUI.Layout.placeholder.name")));
        item.setItemMeta(meta);
        for (Integer slots : Gui.getGuiConfig().getIntegerList("GUI.Layout.placeholder.slots")) {
            MenuHandler.menu.setItem(slots, item);
        }

        //Current Tag

        if (Gui.getGuiConfig().getString("GUI.Layout.current-tag.type").contains("PLAYER_HEAD")) {
            if (Gui.getGuiConfig().getString("GUI.Layout.current-tag.owner").equalsIgnoreCase("player")) {

                ItemStack head = new ItemStack(Material.PLAYER_HEAD, 1, (short) 3);
                SkullMeta sm = (SkullMeta) head.getItemMeta();

                sm.setOwner(p.getDisplayName());
                sm.setDisplayName(Color.format(Gui.getGuiConfig().getString("GUI.Layout.current-tag.name")));
                List<String> headlore = new ArrayList<String>();
                for (String lore : Gui.getGuiConfig().getStringList("GUI.Layout.current-tag.lore")) {
                    if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
                        String withplace = PlaceholderAPI.setPlaceholders(p, lore);
                        headlore.add(Color.format(withplace));
                    } else {
                        headlore.add(Color.format(lore));
                    }
                }
                sm.setLore(headlore);
                head.setItemMeta(sm);
                MenuHandler.menu.setItem(Gui.getGuiConfig().getInt("GUI.Layout.current-tag.slot"), head);
            } else {

                ItemStack head = new ItemStack(Material.PLAYER_HEAD, 1, (short) 3);
                SkullMeta sm = (SkullMeta) head.getItemMeta();

                sm.setOwner(Gui.getGuiConfig().getString("GUI.Layout.current-tag.owner"));
                sm.setDisplayName(Color.format(Gui.getGuiConfig().getString("GUI.Layout.current-tag.name")));
                List<String> headlore = new ArrayList<String>();
                for (String lore : Gui.getGuiConfig().getStringList("GUI.Layout.current-tag.lore")) {
                    if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
                        String withplace = PlaceholderAPI.setPlaceholders(p, lore);
                        headlore.add(Color.format(withplace));
                    } else {
                        headlore.add(Color.format(lore));
                    }
                }
                sm.setLore(headlore);
                head.setItemMeta(sm);
                MenuHandler.menu.setItem(Gui.getGuiConfig().getInt("GUI.Layout.current-tag.slot"), head);

            }
        } else {
            ItemStack headitem = new ItemStack(Material.RED_STAINED_GLASS_PANE);
            ItemMeta him = headitem.getItemMeta();
            List<String> lore = new ArrayList<String>();
            try {
                headitem.setType(Material.matchMaterial(Gui.getGuiConfig().getString("GUI.Layout.current-tag.type")));
            } catch (Exception e) {
                headitem.setType(Material.RED_STAINED_GLASS_PANE);
                lore.add(Color.format("&4&lInvalid Material ID"));
            }


            him.setDisplayName(Color.format(Gui.getGuiConfig().getString("GUI.Layout.current-tag.name")));
            for (String headlore2 : Gui.getGuiConfig().getStringList("GUI.Layout.current-tag.lore")) {
                if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
                    String placeholder = PlaceholderAPI.setPlaceholders(p, headlore2);
                    lore.add(Color.format(placeholder));
                } else {
                    lore.add(headlore2);
                }
            }
            him.setLore(lore);
            headitem.setItemMeta(him);
            MenuHandler.menu.setItem(Gui.getGuiConfig().getInt("GUI.Layout.current-tag.slot"), headitem);
        }


        //Close Button

        item.setType(Material.matchMaterial(Gui.getGuiConfig().getString("GUI.Layout.close-button.type")));
        meta.setDisplayName(Color.format(Gui.getGuiConfig().getString("GUI.Layout.close-button.name")));
        item.setItemMeta(meta);
        MenuHandler.menu.setItem(Gui.getGuiConfig().getInt("GUI.Layout.close-button.slot"), item);

        //Next Button

        item.setType(Material.matchMaterial(Gui.getGuiConfig().getString("GUI.Layout.next-page.type")));
        meta.setDisplayName(Color.format(Gui.getGuiConfig().getString("GUI.Layout.next-page.name")));
        item.setItemMeta(meta);
        MenuHandler.menu.setItem(Gui.getGuiConfig().getInt("GUI.Layout.next-page.slot"), item);

        //Previous Button

        item.setType(Material.matchMaterial(Gui.getGuiConfig().getString("GUI.Layout.previous-page.type")));
        meta.setDisplayName(Color.format(Gui.getGuiConfig().getString("GUI.Layout.previous-page.name")));
        item.setItemMeta(meta);
        MenuHandler.menu.setItem(Gui.getGuiConfig().getInt("GUI.Layout.previous-page.slot"), item);

    }


}
