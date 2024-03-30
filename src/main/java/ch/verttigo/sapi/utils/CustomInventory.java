package ch.verttigo.sapi.utils;

import ch.verttigo.sapi.SAPI;
import ch.verttigo.sapi.utils.items.ActionItem;
import ch.verttigo.sapi.utils.items.ItemCreator;
import ch.verttigo.sapi.utils.items.StaticItem;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;


public abstract class CustomInventory {

    public static Map<UUID, CustomInventory> cache = new ConcurrentHashMap<>();

    public Player player;
    public String title;
    public int lines;
    public int categories;

    public List<StaticItem> staticItems;
    public List<ActionItem> actionItems;
    public int actual_categorie;
    public Inventory inv;

    /**
     * @param p          le joueur
     * @param title      le titre
     * @param lines      le nombre de lignes
     * @param categories la categorie de base
     */

    public CustomInventory(Player p, String title, int lines, int categories) {
        this.player = p;
        this.title = title;
        this.lines = lines;
        this.categories = categories;

        this.staticItems = new ArrayList<>();
        this.actionItems = new ArrayList<>();
        this.actual_categorie = 1;
    }

    public abstract void setupMenu();

    /**
     * Ouvrir le menu
     */

    public void openMenu() {
        setupMenu();

        this.inv = Bukkit.createInventory(null, getSlots(), getTitle());
        refresh();
    }

    /**
     * Ouvrir le menu apres
     *
     * @param tick tick de decalage
     */
    public void openMenuCooldown(int tick) {
        Bukkit.getScheduler().runTaskLater(SAPI.getInstance(), new Runnable() {
            @Override
            public void run() {
                openMenu();
            }
        }, tick);
    }

    /**
     * Permet de refresh a tous se menu (a utiliser dans les relog pour exemple)
     */

    public void refresh() {
        cache.remove(player.getUniqueId());

        for (StaticItem staticItem : staticItems) {
            if (staticItem.getCategory() != getActualCategorie() && !staticItem.isAllCategories()) {
                continue;
            }

            inv.setItem(staticItem.getSlot(), staticItem.getItemStack());
        }

        for (ActionItem actionItem : actionItems) {
            if (actionItem.getCategory() != getActualCategorie() && !actionItem.isAllCategories()) {
                continue;
            }

            inv.setItem(actionItem.getSlot(), actionItem.getItemStack());
        }
        player.getPlayer().openInventory(this.inv);
        cache.put(player.getUniqueId(), this);
    }

    /**
     * Remplir l'inventaire d'un item
     *
     * @param item itemstack
     */

    public void remplirInventory(ItemStack item) {
        for (int i = 0; i < getSlots(); i++) {
            addItem(new StaticItem(i, item));
        }
    }

    /**
     * Remplire une ligne d'un item
     *
     * @param item   itemstack
     * @param lignes ligne a remplire
     */

    public void remplirLignesInventory(ItemStack item, int lignes) {
        for (int i = (9 * (lignes - 1)); i < lignes * 9; i++) {
            addItem(new StaticItem(i, item));
        }
    }

    /**
     * Remplire les corner
     *
     * @param item itemstack
     */

    public void remplirCornerInventory(ItemStack item) {
        Arrays.asList(0, 1, 7, 8, 9, 17).forEach(i -> {
            if (i < lines * 9) {
                addItem(new StaticItem(i, item));
            }
        });

        int lastItem = lines * 9 - 1;
        Arrays.asList(lastItem, lastItem - 1, lastItem - 7, lastItem - 8, lastItem - 9, lastItem - 17).forEach(i -> {
            if (i < lines * 9) {
                addItem(new StaticItem(i, item));
            }
        });
    }

    /**
     * remplire le cadre
     *
     * @param item itemstack
     */

    public void remplirCadreInventory(ItemStack item) {
        remplirLignesInventory(item, 1);
        remplirLignesInventory(item, lines);
        Arrays.asList(9, 17, 18, 26, 27, 35, 36, 44).forEach(i -> {
            if (i < lines * 9) {
                addItem(new StaticItem(i, item));
            }
        });
    }

    /**
     * remplire l'inv avec exeption
     *
     * @param item
     * @param slots
     */

    public void remplirInventoryWithSlotException(ItemStack item, List<Integer> slots) {
        for (int i = 0; i < getSlots(); i++) {
            if (slots.contains(i)) {
                continue;
            }
            addItem(new StaticItem(i, item));
        }
    }

    public void remplirInventoryWithSlot(ItemStack item, List<Integer> slots) {
        for (Integer s : slots) {
            if (s < 0 || s >= lines * 9) {
                continue;
            }
            addItem(new StaticItem(s, item));
        }
    }


    public void remplirInventoryWithSlotException(ItemStack item, Integer... slotException) {
        List<Integer> slots = Arrays.asList(slotException);
        for (int i = 0; i < getSlots(); i++) {
            if (slots.contains(i)) {
                continue;
            }
            addItem(new StaticItem(i, item));
        }
    }

    /**
     * Ouvrir un autre menu
     *
     * @param slot            slot
     * @param customInventory le nouvelle inventaire
     * @param stack           itemstack
     * @param lore            lore
     */

    public void addMenuItem(int slot, CustomInventory customInventory, ItemStack stack, String lore) {
        ItemCreator menuItem = new ItemCreator(stack);
        menuItem.addLore("").addLore(lore);

        addActionItem(new ActionItem(slot, menuItem.getItemstack()) {
            @Override
            public void onClick(InventoryClickEvent e) {
                customInventory.openMenu();
            }
        });

    }

    public void addMenuItem(int slot, CustomInventory customInventory, Material material, String name, String lore) {
        this.addMenuItem(slot, customInventory, new ItemCreator(material).setName(name).getItemstack(), lore);
    }

    public void addReturnItem(int slot, CustomInventory customInventory, String message) {
        ItemCreator arrow = new ItemCreator(Material.ARROW).setName(message);

        addActionItem(new ActionItem(slot, arrow.getItemstack()) {
            @Override
            public void onClick(InventoryClickEvent e) {
                customInventory.openMenu();
            }
        });
    }

    public void addReturnItem(int slot, CustomInventory customInventory) {
        this.addReturnItem(slot, customInventory, ChatColor.DARK_GREEN + "Revenir en arrière");
    }

    public void addCloseItem(int slot, String message) {
        ItemCreator close = new ItemCreator(Material.BARRIER).setName(message);

        addActionItem(new ActionItem(slot, close.getItemstack()) {
            @Override
            public void onClick(InventoryClickEvent e) {
                player.getPlayer().closeInventory();
            }
        });
    }

    public void addCloseItem(int slot) {
        this.addCloseItem(slot, ChatColor.DARK_RED + "✖ " + ChatColor.RED + "Fermer le menu" + ChatColor.DARK_RED + " ✖");
    }

    /**
     * changer la categorie
     *
     * @param categorie
     * @param slot
     * @param main_categorie
     * @param item
     * @param selected
     * @param selection
     */

    public void addSpecialCategorieItem(int categorie, int slot, int main_categorie, ItemCreator item, String selected, String selection) {
        List<String> lore = new ArrayList<>();
        if (!(item.getLores() == null)) {
            if (!item.getLores().isEmpty()) {
                lore.addAll(item.getLores());
                lore.add(" ");
            }
        }

        if (categorie == actual_categorie) {
            lore.add(selected);
        } else {
            lore.add(selection);
        }
        item.setLores(lore);

        addActionItem(new ActionItem(main_categorie, slot, item.getItemstack()) {
            @Override
            public void onClick(InventoryClickEvent e) {
                setActualCategorie(categorie);
                openMenu();
            }
        });
    }

    public void addSpecialCategorieItem(int categorie, int slot, int main_categorie, ItemCreator item) {
        this.addSpecialCategorieItem(categorie, slot, main_categorie, item,
                ChatColor.GREEN + String.valueOf(ChatColor.BOLD) + "Sélectionnée", ChatColor.DARK_GRAY + "»" + ChatColor.WHITE + " Sélectionner cette catégorie");
    }

    public void addCategorieItem(int categorie, int slot, ItemCreator item, String selected, String selection) {
        List<String> lore = new ArrayList<>();
        if (!(item.getLores() == null)) {
            if (!item.getLores().isEmpty()) {
                lore.addAll(item.getLores());
                lore.add(" ");
            }
        }

        if (categorie == actual_categorie) {
            lore.add(selected);
        } else {
            lore.add(selection);
        }
        item.setLores(lore);

        addActionItem(new ActionItem(slot, item.getItemstack()) {
            @Override
            public void onClick(InventoryClickEvent e) {
                setActualCategorie(categorie);
                openMenu();
            }
        });
    }

    public void addCategorieItem(int categorie, int slot, ItemCreator item) {
        this.addCategorieItem(categorie, slot, item, ChatColor.GREEN + String.valueOf(ChatColor.BOLD) + "Sélectionnée",
                ChatColor.DARK_GRAY + "»" + ChatColor.WHITE + " Sélectionner cette catégorie");
    }

    public void addCategorieItemWithSize(int categorie, int slot, int lines, ItemCreator item, String selected, String selection) {
        List<String> lore = new ArrayList<>();
        if (item.getLores() != null) {
            if (!item.getLores().isEmpty()) {
                lore.addAll(item.getLores());
                lore.add(" ");
            }
        }
        if (categorie == actual_categorie) {
            lore.add(selected);
        } else {
            lore.add(selection);
        }
        item.setLores(lore);

        addActionItem(new ActionItem(slot, item.getItemstack()) {
            @Override
            public void onClick(InventoryClickEvent e) {
                setActualCategorie(categorie);
                changeLines(lines);
                openMenu();
            }
        });
    }

    public void addCategorieItemWithSize(int categorie, int slot, int lines, ItemCreator item) {
        this.addCategorieItemWithSize(categorie, slot, lines, item,
                ChatColor.GREEN + String.valueOf(ChatColor.BOLD) + "Sélectionnée", ChatColor.DARK_GRAY + "»" + ChatColor.WHITE + " Sélectionner cette catégorie");
    }

    public void addPageCategorieItemWithSize(List<Integer> categories, int slot, int lines, ItemCreator item, String selected, String selection, String next_page, String previous_page) {
        List<String> lore = new ArrayList<>();
        int max_pages = categories.size();
        if (categories.contains(actual_categorie)) {

            int page = 1;
            for (int i = 0; i < max_pages; i++)
                if (categories.get(i) == actual_categorie) {
                    page = i + 1;
                }

            item.setName(item.getName() + ChatColor.GRAY + " (Page " + ChatColor.LIGHT_PURPLE + page + ChatColor.GRAY + "/" + ChatColor.DARK_PURPLE + max_pages + ChatColor.GRAY + ")");

            lore.add(next_page);
            lore.add(previous_page);
            lore.add(" ");
            lore.add(selected);
        } else {
            lore.add(selection);
        }
        item.setLores(lore);

        addActionItem(new ActionItem(slot, item.getItemstack()) {
            @Override
            public void onClick(InventoryClickEvent e) {
                if (categories.contains(actual_categorie)) {
                    int categorie = categories.get(0);
                    int page = 1;
                    for (int i = 0; i < max_pages; i++)
                        if (categories.get(i) == actual_categorie) {
                            page = i + 1;
                        }


                    if (e.getClick() == ClickType.LEFT) {
                        if (page == max_pages) {
                            categorie = categories.get(max_pages - 1);
                        } else {
                            categorie = categories.get(page);
                        }
                    }


                    if (e.getClick() == ClickType.RIGHT) {
                        if (page == 1) {
                            categorie = categories.get(0);
                        } else {
                            categorie = categories.get(page - 2);
                        }
                    }

                    setActualCategorie(categorie);
                    openMenu();
                } else {
                    setActualCategorie(categories.get(0));
                    changeLines(lines);
                    openMenu();
                }
            }
        });
    }

    public void addPageCategorieItemWithSize(List<Integer> categories, int slot, int lines, ItemCreator item) {
        this.addPageCategorieItemWithSize(categories, slot, lines, item,
                ChatColor.GREEN + String.valueOf(ChatColor.BOLD) + "Sélectionnée", ChatColor.DARK_GRAY + "»" + ChatColor.WHITE + " Sélectionner cette catégorie",
                ChatColor.YELLOW + "Clic-Gauche: " + ChatColor.WHITE + "Aller à la page suivante", ChatColor.YELLOW + "Clic-Droite: " + ChatColor.WHITE + "Aller à la page précédente");
    }

    /**
     * ajouter un item de page
     */

    public void addPageItem(int slot, String next_page, String previous_page) {
        ItemCreator page = new ItemCreator(Material.MAP).setName(ChatColor.YELLOW + "Page " + ChatColor.LIGHT_PURPLE + getActualCategorie() + ChatColor.GOLD + "/" + ChatColor.DARK_PURPLE + getMaxCategories());
        page.addLore(next_page).addLore(previous_page);
        page.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
        addActionItem(new ActionItem(slot, page.addItemFlags(ItemFlag.HIDE_ATTRIBUTES).getItemstack()) {
            @Override
            public void onClick(InventoryClickEvent e) {
                if (e.getClick() == ClickType.LEFT) {
                    setActualCategorie(getNextCategorie());
                }
                if (e.getClick() == ClickType.RIGHT) {
                    setActualCategorie(getPreviousCategorie());
                }
                openMenu();
            }
        });
    }

    /**
     * ajouter un item de page
     *
     * @param slot
     */

    public void addPageItem(int slot) {
        this.addPageItem(slot, ChatColor.YELLOW + "Clic-Gauche: " + ChatColor.WHITE + "Aller à la page suivante",
                ChatColor.YELLOW + "Clic-Droite: " + ChatColor.WHITE + "Aller à la page précédente");
    }

    public void addItem(StaticItem item) {
        staticItems.add(item);
    }

    public void addActionItem(ActionItem item) {
        actionItems.add(item);
    }

    public Player getPlayer() {
        return player;
    }

    public String getTitle() {
        return title;
    }

    public void changeLines(int lines) {
        this.lines = lines;
    }

    public int getLines() {
        return lines;
    }

    public int getSlots() {
        return lines * 9;
    }

    public int getMaxCategories() {
        return categories;
    }

    public int getActualCategorie() {
        return actual_categorie;
    }

    public void setActualCategorie(int categorie) {
        actual_categorie = categorie;
    }

    public int getPreviousCategorie() {
        if (getActualCategorie() == 1) {
            return 1;
        }

        return getActualCategorie() - 1;
    }

    public int getNextCategorie() {
        if (getActualCategorie() == getMaxCategories()) {
            return getMaxCategories();
        }

        return getActualCategorie() + 1;
    }

    public StaticItem getStaticItem(int slot) {
        StaticItem item = null;
        for (StaticItem staticItem : staticItems) {
            if (staticItem.getCategory() != getActualCategorie() && !staticItem.isAllCategories()) {
                continue;
            }

            if (staticItem.getSlot() == slot) {
                item = staticItem;
            }

        }
        return item;
    }

    public ActionItem getActionItem(int slot) {
        ActionItem item = null;
        for (ActionItem actionItem : actionItems) {
            if (actionItem.getCategory() != getActualCategorie() && !actionItem.isAllCategories()) {
                continue;
            }

            if (actionItem.getSlot() == slot) {
                item = actionItem;
            }

        }
        return item;
    }

    /**
     * Permet d'actualiser le menu a tout les joueurs qui ont ce menu
     */

    public void openMenuAll() {
        List<CustomInventory> customUI = new ArrayList<>(cache.values());
        customUI.forEach(cui -> {
            if (cui.getTitle().equals(title)) {
                cui.openMenu();
            }
        });
    }
}

