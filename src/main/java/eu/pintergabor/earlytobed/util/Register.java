package eu.pintergabor.earlytobed.util;

import eu.pintergabor.earlytobed.Global;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public final class Register {

    /**
     * Register mod item
     *
     * @param name Name, as in lang/*.json files without the "item.modid." prefix
     * @param item Item
     * @return The same item
     */
    @SuppressWarnings("UnusedReturnValue")
    public static Item registerItem(String name, Item item) {
        return Registry.register(
                Registries.ITEM,
                Global.ModIdentifier(name),
                item);
    }
}
