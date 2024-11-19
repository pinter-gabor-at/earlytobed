package eu.pintergabor.earlytobed.util;

import eu.pintergabor.earlytobed.Global;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;

import java.util.function.Function;

public final class Register {

    /**
     * Register mod item
     *
     * @param name     Name, as in lang/*.json files without the "item.modid." prefix
     * @param factory  Constructor of the item
     * @param settings Properties of the item
     * @return The registered item
     */
    public static Item registerItem(String name, Function<Item.Settings, Item> factory, Item.Settings settings) {
        final RegistryKey<Item> registryKey = RegistryKey.of(RegistryKeys.ITEM, Global.ModIdentifier(name));
        // Write registryKey to settings, and then use that settings to create the item.
        // https://wiki.fabricmc.net/tutorial:items#creating_items_in_1212
        return Items.register(registryKey, factory, settings);
    }
}
