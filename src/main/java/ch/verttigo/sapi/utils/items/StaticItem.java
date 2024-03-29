package ch.verttigo.sapi.utils.items;

import org.bukkit.inventory.ItemStack;

public class StaticItem {

    private final ItemStack itemStack;
    private final int category;
    private final int slot;
    private final boolean all;

    public StaticItem(int slot, ItemStack itemStack) {
        this(1, true, slot, itemStack);
    }

    public StaticItem(int category, int slot, ItemStack itemStack) {
        this(category, false, slot, itemStack);
    }

    public StaticItem(int category, boolean all, int slot, ItemStack itemStack) {
        this.itemStack = itemStack;
        this.category = category;
        this.all = all;
        this.slot = slot;
    }

    public ItemStack getItemStack() {
        return itemStack;
    }

    public int getCategory() {
        return category;
    }

    public int getSlot() {
        return slot;
    }

    public boolean isAllCategories() {
        return all;
    }

}
