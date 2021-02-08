package me.scalergames.SuperiorTags.commands;

import me.scalergames.SuperiorTags.Main;
import me.scalergames.SuperiorTags.files.Data;
import me.scalergames.SuperiorTags.files.Tags;
import me.scalergames.SuperiorTags.utils.Color;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TagsCMD implements CommandExecutor {

    public String[] list = {"", "", ""};

    public Tags tags;

    int tagamount = Data.getDataConfig().getInt("TagCount");

    public boolean onCommand(CommandSender s, Command c, String label, String[] args) {

        String prefix = Main.getInstance().getConfig().getString("Prefix");

        if(label.equalsIgnoreCase("tags")) {
            if (s.hasPermission("superiortags.cmd")) {
                if (args.length == 0) {


                    if (s instanceof Player) {

                        Player p = (Player) s;
                        Inventory gui = Bukkit.createInventory(null, 54,
                                Color.format(Main.getInstance().getConfig().getString("TitleColor")
                                        + Main.getInstance().getConfig().getString("Title")));

                        ItemStack item = new ItemStack(Material.CYAN_STAINED_GLASS_PANE);
                        ItemMeta meta = item.getItemMeta();

                        //Glass Panes
                        meta.setDisplayName(Color.format("&f"));
                        item.setItemMeta(meta);
                        gui.setItem(45, item);
                        gui.setItem(46, item);
                        gui.setItem(47, item);
                        gui.setItem(48, item);
                        gui.setItem(52, item);
                        gui.setItem(51, item);
                        gui.setItem(50, item);

                        //Close Button
                        item.setType(Material.BARRIER);
                        meta.setDisplayName(Color.format("&cClose"));
                        item.setItemMeta(meta);
                        gui.setItem(53, item);

                        //Current Tag
                        ItemStack head = new ItemStack(Material.PLAYER_HEAD, 1, (short)3);
                        SkullMeta sm = (SkullMeta) head.getItemMeta();
                        sm.setOwner(p.getDisplayName());
                        sm.setDisplayName(Color.format("&3Current Tag"));
                        String data = Data.getDataConfig().getString(p.getUniqueId() + ".tag");
                        try {
                            List<String> lore = new ArrayList<String>();
                            String tag = Tags.getTagsConfig().getString("SuperiorTags.tags." + data + ".tag");
                            lore.add(Color.format(tag));
                            sm.setLore(lore);
                        } catch (NullPointerException e) {
                        }
                        head.setItemMeta(sm);
                        gui.setItem(49, head);

                        ConfigurationSection tagssection = Tags.getTagsConfig().getConfigurationSection("SuperiorTags.tags");

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
                                    gui.setItem(slot, item);
                                    slot++;


                                } else {

                                    item.setType(Material.ARROW);
                                    meta.setDisplayName(Color.format("&9Next ->"));
                                    taglore.clear();
                                    meta.setLore(taglore);
                                    item.setItemMeta(meta);
                                    gui.setItem(50, item);

                                }

                            }

                        }

                        p.openInventory(gui);
                    }


                }

                if (args.length >= 1) {
                    if (args[0].equalsIgnoreCase("create")) {
                        if (s.hasPermission("superiortags.cmd.create")) {
                            if (args.length == 1) {
                                s.sendMessage(Color.format(prefix + "&cUsage: /tags create [identifier] [tag]"));
                            } else {
                                String input = StringUtils.join(Arrays.copyOfRange(args, 2, args.length), " ");
                                Tags.getTagsConfig().set("SuperiorTags.tags." + args[1] + ".tag", input);
                                Tags.getTagsConfig().set("SuperiorTags.tags." + args[1] + ".description", "&f");
                                Tags.getTagsConfig().set("SuperiorTags.tags." + args[1] + ".permission", "superiortags.tag." + args[1]);
                                Tags.getTagsConfig().set("SuperiorTags.tags." + args[1] + ".item",
                                        Main.getInstance().getConfig().getString("DefaultItem"));
                                Tags.getTagsConfig().set("SuperiorTags.tags." + args[1] + ".effects", "none");
                                if (tagamount >= 46) {
                                    Tags.getTagsConfig().set("SuperiorTags.tags." + args[1] + ".page", 2);
                                }
                                Tags.saveTags();
                                s.sendMessage(Color.format(prefix + "&aCreated the " + args[1] + " tag which looks like: " + input));
                            }
                        } else {
                            s.sendMessage(Color.format(prefix + "&cYou do not have permission to create a tag."));
                        }
                    }

                    if (args[0].equalsIgnoreCase("description")) {
                        if (s.hasPermission("superiortags.cmd.description")) {
                            if (args.length == 1) {
                                s.sendMessage(Color.format(prefix + "&cUsage: /tags description [tag] [description]"));
                            } else {
                                if (Tags.getTagsConfig().getConfigurationSection("SuperiorTags.tags.").getKeys(false).contains(args[1])) {
                                    String input = StringUtils.join(Arrays.copyOfRange(args, 2, args.length), " ");
                                    Tags.getTagsConfig().set("SuperiorTags.tags." + args[1] + ".description", input);
                                    Tags.saveTags();
                                    s.sendMessage(Color.format(prefix + "&aSet the description for " + args[1] + " to: " + input));
                                }
                                else {
                                    s.sendMessage(Color.format(prefix + "&cThat tag dose not exist"));

                                }
                            }
                        } else {
                            s.sendMessage(Color.format(prefix + "&cYou do not have permission to set the description of a tag."));
                        }
                    }

                    if (args[0].equalsIgnoreCase("permission")) {
                        if (s.hasPermission("superiortags.cmd.permission")) {
                            if (args.length == 1) {
                                s.sendMessage(Color.format(prefix + "&cUsage: /tags permission [tag] [permission]"));
                            } else {
                                if (Tags.getTagsConfig().getConfigurationSection("SuperiorTags.tags.").getKeys(false).contains(args[1])) {
                                    String input = StringUtils.join(Arrays.copyOfRange(args, 2, args.length), " ");
                                    Tags.getTagsConfig().set("SuperiorTags.tags." + args[1] + ".permission", ("superiortags.tag." + input).replace(" ", "."));
                                    String permission = "superiortags.tag." + input.replace(" ", ".");
                                    Tags.saveTags();
                                    s.sendMessage(Color.format(prefix + "&aSet the permission for " + args[1] + " to: " + permission));
                                }
                                else {
                                    s.sendMessage(Color.format(prefix + "&cThat tag dose not exist"));
                                }
                            }
                        } else {
                            s.sendMessage(Color.format(prefix + "&cYou do not have permission to set the permission of a tag"));
                        }
                    }

                    if (args[0].equalsIgnoreCase("equip")) {
                        if (s instanceof Player) {
                            Player p = (Player) s;
                            if (args.length == 1) {
                                p.sendMessage(Color.format(prefix + "&cUsage: /tags equip [tag]"));
                            } else {
                                if (Tags.getTagsConfig().getConfigurationSection("SuperiorTags.tags.").getKeys(false).contains(args[1])) {
                                    if (p.hasPermission(Tags.getTagsConfig().getString("SuperiorTags.tags." + args[1] + ".permission"))) {
                                        Data.getDataConfig().set(p.getUniqueId() + ".tag", args[1]);
                                        Data.saveData();
                                        p.sendMessage(Color.format(prefix + "&aYou equipped the " + args[1] + " tag"));
                                    } else {
                                        s.sendMessage(Color.format(prefix + "&cYou do not have permission to equip that tag!"));
                                    }
                                }
                                else {
                                    p.sendMessage(Color.format(prefix + "&cThat is not a valid tag!"));
                                }
                            }
                        } else {
                            s.sendMessage(Color.format(prefix + "&cThis is a player only command!"));
                        }
                    }

                    if (args[0].equalsIgnoreCase("reload")) {
                        if (s.hasPermission("superiortags.cmd.reload")) {
                            ConfigurationSection tagssection = Tags.getTagsConfig().getConfigurationSection("SuperiorTags.tags");
                            int amount = 0;

                            for (String tags : tagssection.getKeys(false)) {
                                String tagName = tags.substring( tags.lastIndexOf( "." ) + 1);
                                amount++;
                                if (amount >= 46) {
                                    Tags.getTagsConfig().set("SuperiorTags.tags." + tagName + ".page", 2);
                                }
                            }

                            Tags.saveTags();
                            Data.getDataConfig().set("TagCount", amount);
                            Data.reloadData();
                            Tags.reloadTags();
                            Main.getInstance().reloadConfig();
                            s.sendMessage(Color.format(prefix + "&3Reloaded Everything!"));
                            Main.getInstance().getLogger().info(Color.format("&2Successfully loaded "
                                    + Data.getDataConfig().getInt("TagsCount") + " tags"));
                        } else {
                            s.sendMessage(Color.format(prefix + "&cYou do not have permission to reload SuperiorTags"));
                        }
                    }

                    if (args[0].equalsIgnoreCase("unequip")) {
                        if (s instanceof Player) {
                            if (s.hasPermission("superiortags.cmd.unequip")) {
                                Player p = (Player) s;
                                Data.getDataConfig().set(p.getUniqueId() + ".tag", "none");
                                Data.saveData();
                                s.sendMessage(Color.format(prefix + "&6You removed your current tag"));
                            } else {
                                s.sendMessage(Color.format(prefix + "&cYou do not have permission to unequip a tag."));
                            }
                        }
                    }

                    if (args[0].equalsIgnoreCase("item")) {
                        if (s.hasPermission("superiortags.cmd.item")) {
                            if (args.length == 1) {
                                s.sendMessage(Color.format(prefix + "&cUsage: /tags item [tag] [item]"));
                            } else {
                                if (Tags.getTagsConfig().getConfigurationSection("SuperiorTags.tags.").getKeys(false).contains(args[1])) {
                                    String input = StringUtils.join(Arrays.copyOfRange(args, 2, args.length), " ");
                                    Tags.getTagsConfig().set("SuperiorTags.tags." + args[1] + ".item", input.toUpperCase().replace(" ", "_"));
                                    Tags.saveTags();
                                    s.sendMessage(Color.format(prefix + "&aSet the item of " + args[1] + " &ato: &2" + input));
                                }
                                else {
                                    s.sendMessage(Color.format(prefix + "&cThat tag dose not exist."));
                                }
                            }
                        } else {
                            s.sendMessage(Color.format(prefix + "&cYou do not have permission to set the item of a tag."));
                        }
                    }

                    if (args[0].equalsIgnoreCase("remove")) {
                        if (s.hasPermission("superiortags.cmd.remove")) {
                            if (args.length == 1) {
                                s.sendMessage(Color.format(prefix + "&cUsage: /tags remove [tag]"));
                            } else {
                                if (Tags.getTagsConfig().getConfigurationSection("SuperiorTags.tags.").getKeys(false).contains(args[1])) {
                                    s.sendMessage(Color.format(prefix + "&cRemoved tag: " + Tags.getTagsConfig().getString("SuperiorTags.tags." + args[1] + ".tag")));
                                    Tags.getTagsConfig().set("SuperiorTags.tags." + args[1], null);
                                    Tags.saveTags();
                                }
                                else {
                                    s.sendMessage(Color.format(prefix + "&cThat tag dose not exist."));
                                }
                            }
                        } else {
                            s.sendMessage(Color.format(prefix + "&cYou do not have permission to remove a tag."));
                        }
                    }

                }
            }
        }


        return false;
    }

}
