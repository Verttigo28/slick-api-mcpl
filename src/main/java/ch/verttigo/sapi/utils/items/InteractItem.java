package ch.verttigo.sapi.utils.items;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.inventory.ItemStack;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public abstract class InteractItem {

    public static Map<ItemStack, InteractItem> cache = new ConcurrentHashMap<>();

    private final boolean isDrop;
    private final ItemStack itemStack;

    public InteractItem(ItemStack itemStack) {
        this(false, itemStack);
    }

    public InteractItem(boolean isDrop, ItemStack itemStack) {
        this.itemStack = itemStack;
        this.isDrop = isDrop;

        cache.put(itemStack, this);
    }

    public ItemStack getItemStack() {
        return itemStack;
    }

    public boolean isDrop() {
        return isDrop;
    }

    public abstract void onClick(Player p, Action e, Block block);
}

