package io.github.lix3nn53.guardiansofadelia.events;

import io.github.lix3nn53.guardiansofadelia.GuardiansOfAdelia;
import io.github.lix3nn53.guardiansofadelia.guardian.GuardianData;
import io.github.lix3nn53.guardiansofadelia.guardian.GuardianDataManager;
import io.github.lix3nn53.guardiansofadelia.guardian.character.RPGCharacter;
import io.github.lix3nn53.guardiansofadelia.guardian.character.RPGClass;
import io.github.lix3nn53.guardiansofadelia.menu.MenuList;
import io.github.lix3nn53.guardiansofadelia.npc.QuestNPCManager;
import io.github.lix3nn53.guardiansofadelia.quests.Quest;
import io.github.lix3nn53.guardiansofadelia.rpginventory.RPGInventory;
import io.github.lix3nn53.guardiansofadelia.towns.Town;
import io.github.lix3nn53.guardiansofadelia.towns.TownManager;
import io.github.lix3nn53.guardiansofadelia.utilities.SkillAPIUtils;
import io.github.lix3nn53.guardiansofadelia.utilities.gui.Gui;
import io.github.lix3nn53.guardiansofadelia.utilities.gui.GuiGeneric;
import io.github.lix3nn53.guardiansofadelia.utilities.managers.CharacterSelectionScreenManager;
import io.github.lix3nn53.guardiansofadelia.utilities.managers.CompassManager;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.api.npc.NPCRegistry;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

public class MyInventoryClickEvent implements Listener {

    @EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
    public void onEvent(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        UUID uuid = player.getUniqueId();

        Gui activeGui = null;
        GuardianData guardianData = null;
        RPGCharacter rpgCharacter = null;

        if (GuardianDataManager.hasGuardianData(uuid)) {
            guardianData = GuardianDataManager.getGuardianData(uuid);
            if (guardianData.hasActiveCharacter()) {
                rpgCharacter = guardianData.getActiveCharacter();
            }

            if (guardianData.hasActiveGui()) {
                activeGui = guardianData.getActiveGui();
                if (activeGui.isLocked()) {
                    event.setCancelled(true);
                }
            }
        }

        //Open RPG-Inventory
        Inventory clickedInventory = event.getClickedInventory();
        ItemStack cursor = event.getCursor();
        if (clickedInventory != null && clickedInventory.getType().equals(InventoryType.CRAFTING)) {
            event.setCancelled(true);
            if (cursor.getType().equals(Material.AIR)) {
                if (rpgCharacter != null) {
                    GuiGeneric guiGeneric = rpgCharacter.getRpgInventory().formRPGInventory(player);
                    guiGeneric.openInventory(player);
                }
            }
            return;
        }

        if (event.getCurrentItem() == null) return;
        if (event.getCurrentItem().getType().equals(Material.AIR)
                || event.getCurrentItem().getType().equals(Material.FEATHER)
                || event.getCurrentItem().getType().equals(Material.IRON_HOE)) return;
        if (!(event.getCurrentItem().hasItemMeta())) return;
        if (!(event.getCurrentItem().getItemMeta().hasDisplayName())) return;

        ItemStack current = event.getCurrentItem();
        String currentName = current.getItemMeta().getDisplayName();
        String title = event.getView().getTitle();
        Inventory topInventory = event.getView().getTopInventory();
        int slot = event.getSlot();

        if (currentName.equals(ChatColor.GREEN + "Menu")) {
            event.setCancelled(true);
            GuiGeneric guiGeneric = MenuList.mainMenu();
            guiGeneric.openInventory(player);
        } else if (title.equals(org.bukkit.ChatColor.GREEN + "Menu")) {
            if (currentName.equals(ChatColor.GREEN + "Guides")) {
                GuiGeneric guide = MenuList.guide();
                guide.openInventory(player);
            } else if (currentName.equals(ChatColor.AQUA + "Compass")) {
                GuiGeneric compass = MenuList.compass();
                compass.openInventory(player);
            } else if (currentName.equals(ChatColor.DARK_GREEN + "Maps")) {
                player.closeInventory();
                TextComponent message = new TextComponent(" Maps ! (Click Me)");
                message.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "http://guardiansofadelia.com/#t5"));
                message.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("Click to see Maps from our website").color(ChatColor.AQUA).create()));
                message.setColor(ChatColor.GREEN);
                message.setBold(true);
                player.spigot().sendMessage(message);
            } else if (currentName.equals(ChatColor.AQUA + "Announcements and News")) {
                player.closeInventory();
                TextComponent message = new TextComponent(" Announcements and News ! (Click Me)");
                message.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "http://guardiansofadelia.com/#t2"));
                message.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("Click to see Announcements and News from our website").color(ChatColor.AQUA).create()));
                message.setColor(ChatColor.AQUA);
                message.setBold(true);
                player.spigot().sendMessage(message);
            } else if (currentName.equals(ChatColor.GOLD + "Character")) {
                GuiGeneric character = MenuList.character();
                character.openInventory(player);
            } else if (currentName.equals(ChatColor.DARK_PURPLE + "Guild")) {
                GuiGeneric guild = MenuList.guild();
                guild.openInventory(player);
            } else if (currentName.equals(ChatColor.DARK_PURPLE + "Minigames")) {
                GuiGeneric minigames = MenuList.minigames();
                minigames.openInventory(player);
            } else if (currentName.equals(ChatColor.YELLOW + "Bazaar")) {
                GuiGeneric bazaar = MenuList.bazaar();
                bazaar.openInventory(player);
            } else if (currentName.equals(ChatColor.LIGHT_PURPLE + "Donation ♥")) {
                player.closeInventory();
                TextComponent message = new TextComponent(" Donation ♥ ! (Click Me)");
                message.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "http://guardiansofadelia.com/#t6"));
                message.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("Click to see donate ♥").color(ChatColor.AQUA).create()));
                message.setColor(ChatColor.LIGHT_PURPLE);
                message.setBold(true);
                player.spigot().sendMessage(message);
            }
        } else if (title.contains("Character")) {
            if (title.contains("Creation")) {
                String charNoString = title.replace(ChatColor.YELLOW + "Character ", "");
                charNoString = charNoString.replace(" Creation", "");
                int charNo = Integer.parseInt(charNoString);

                CharacterSelectionScreenManager characterSelectionScreenManager = GuardiansOfAdelia.getCharacterSelectionScreenManager();
                if (currentName.contains("Knight")) {
                    characterSelectionScreenManager.createCharacter(player, charNo, RPGClass.KNIGHT);
                } else if (currentName.contains("Paladin")) {
                    characterSelectionScreenManager.createCharacter(player, charNo, RPGClass.PALADIN);
                } else if (currentName.contains("Ninja")) {
                    characterSelectionScreenManager.createCharacter(player, charNo, RPGClass.NINJA);
                } else if (currentName.contains("Archer")) {
                    characterSelectionScreenManager.createCharacter(player, charNo, RPGClass.ARCHER);
                } else if (currentName.contains("Mage")) {
                    characterSelectionScreenManager.createCharacter(player, charNo, RPGClass.MAGE);
                } else if (currentName.contains("Warrior")) {
                    characterSelectionScreenManager.createCharacter(player, charNo, RPGClass.WARRIOR);
                } else if (currentName.contains("Monk")) {
                    characterSelectionScreenManager.createCharacter(player, charNo, RPGClass.MONK);
                }
            } else if (title.contains("Selection")) {
                String charNoString = title.replace(ChatColor.YELLOW + "Character ", "");
                charNoString = charNoString.replace(" Selection", "");
                int charNo = Integer.parseInt(charNoString);

                CharacterSelectionScreenManager characterSelectionScreenManager = GuardiansOfAdelia.getCharacterSelectionScreenManager();
                if (currentName.contains("Roumen")) {
                    Location location = TownManager.getTown(1).getLocation();
                    characterSelectionScreenManager.selectCharacter(player, charNo, location);
                } else if (currentName.contains("Port Veloa")) {
                    Location location = TownManager.getTown(2).getLocation();
                    characterSelectionScreenManager.selectCharacter(player, charNo, location);
                } else if (currentName.contains("Elderine")) {
                    Location location = TownManager.getTown(3).getLocation();
                    characterSelectionScreenManager.selectCharacter(player, charNo, location);
                } else if (currentName.contains("Uruga")) {
                    Location location = TownManager.getTown(4).getLocation();
                    characterSelectionScreenManager.selectCharacter(player, charNo, location);
                } else if (currentName.contains("Arberstol Ruins")) {
                    Location location = TownManager.getTown(5).getLocation();
                    characterSelectionScreenManager.selectCharacter(player, charNo, location);
                } else if (currentName.contains("last location")) {
                    Location charLocation = characterSelectionScreenManager.getCharLocation(player, charNo);
                    if (charLocation != null) {
                        characterSelectionScreenManager.selectCharacter(player, charNo, charLocation);
                    } else {
                        player.sendMessage(ChatColor.RED + "Your saved quit-location is not valid");
                    }
                }
            } else {
                if (currentName.equals(ChatColor.LIGHT_PURPLE + "Skills")) {
                    SkillAPIUtils.openSkillMenu(player);
                } else if (currentName.equals(ChatColor.DARK_GREEN + "Elements")) {
                    SkillAPIUtils.openAttributeMenu(player);
                } else if (currentName.equals(ChatColor.YELLOW + "Job")) {
                    GuiGeneric job = MenuList.job();
                    job.openInventory(player);
                } else if (currentName.equals(ChatColor.AQUA + "Chat Tag")) {
                    GuiGeneric chatTag = MenuList.chatTag();
                    chatTag.openInventory(player);
                }
            }
        } else if (title.contains(org.bukkit.ChatColor.YELLOW + "Quest List of ")) {
            if (rpgCharacter != null) {
                if (activeGui != null) {
                    int resourceNPC = activeGui.getResourceNPC();
                    if (resourceNPC != 0) {
                        Material type = current.getType();
                        if (type.equals(Material.MAGENTA_WOOL)) {
                            String[] split = currentName.split("#");
                            int questNo = Integer.parseInt(split[1]);
                            int whoCanCompleteThisQuest = QuestNPCManager.getWhoCanCompleteThisQuest(questNo);
                            if (whoCanCompleteThisQuest == resourceNPC) {
                                //complete quest
                                boolean didComplete = rpgCharacter.turnInQuest(questNo, player);
                                if (didComplete) {
                                    GuiGeneric questGui = QuestNPCManager.getQuestGui(player, resourceNPC);
                                    questGui.openInventory(player);
                                } else {
                                    player.sendMessage(ChatColor.RED + "Couldn't complete the quest ERROR report pls");
                                }
                            } else {
                                NPCRegistry npcRegistry = CitizensAPI.getNPCRegistry();
                                NPC byId = npcRegistry.getById(whoCanCompleteThisQuest);
                                player.sendMessage(ChatColor.RED + "You can't complete this quest from this NPC. You need to talk with " + ChatColor.WHITE + byId.getName());
                            }
                        } else if (type.equals(Material.LIME_WOOL)) {
                            String[] split = currentName.split("#");
                            int questNo = Integer.parseInt(split[1]);
                            int whoCanGiveThisQuest = QuestNPCManager.getWhoCanGiveThisQuest(questNo);
                            if (whoCanGiveThisQuest == resourceNPC) {
                                //give quest
                                Quest questCopyById = QuestNPCManager.getQuestCopyById(questNo);

                                boolean questListIsNotFull = rpgCharacter.acceptQuest(questCopyById, player);
                                if (questListIsNotFull) {
                                    GuiGeneric questGui = QuestNPCManager.getQuestGui(player, resourceNPC);
                                    questGui.openInventory(player);
                                } else {
                                    player.sendMessage(ChatColor.RED + "Your quest list is full");
                                }
                            } else {
                                NPCRegistry npcRegistry = CitizensAPI.getNPCRegistry();
                                NPC byId = npcRegistry.getById(whoCanGiveThisQuest);
                                player.sendMessage(ChatColor.RED + "You can't take this quest from this NPC. You need to talk with " + ChatColor.WHITE + byId.getName());
                            }
                        }
                    }
                }
            }
        } else if (title.equals(ChatColor.AQUA + "Compass")) {
            if (current.getType().equals(Material.LIGHT_BLUE_WOOL)) {
                String displayName = current.getItemMeta().getDisplayName();
                String[] split = displayName.split("#");
                int i = Integer.parseInt(split[1]);

                Town town = TownManager.getTown(i);
                CompassManager.setCompassItemLocation(player, town.getName(), town.getLocation());
            } else if (current.getType().equals(Material.LIME_WOOL)) {
                String displayName = current.getItemMeta().getDisplayName();
                String[] split = displayName.split("#");
                int i = Integer.parseInt(split[1]);
                CompassManager.setCompassItemNPC(player, i);
            }
        } else if (title.equals(ChatColor.YELLOW.toString() + ChatColor.BOLD + "RPG Inventory")) {
            RPGInventory rpgInventory = rpgCharacter.getRpgInventory();
            if (clickedInventory.getType().equals(InventoryType.CHEST)) {
                event.setCancelled(true);
                if (cursor.getType().equals(Material.AIR)) {
                    boolean change = rpgInventory.onCursorClickWithAir(player, slot, topInventory, event.isShiftClick());
                } else {
                    boolean change = rpgInventory.onCursorClickWithItem(player, slot, cursor, topInventory);
                }
            } else if (clickedInventory.getType().equals(InventoryType.PLAYER)) {
                if (cursor.getType().equals(Material.AIR)) {
                    if (event.isShiftClick()) {
                        event.setCancelled(true);
                        boolean change = rpgInventory.onShiftClick(current, player, slot, topInventory);
                    }
                }
            }
        }
    }

}
