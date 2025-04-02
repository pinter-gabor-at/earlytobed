package eu.pintergabor.earlytobed.item;

import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.ShearsItem;


/**
 * Same as {@link ShearsItem}, but with some hard coded properties.
 */
public class WoodenShearsItem extends ShearsItem {

	public WoodenShearsItem(Properties properties) {
		super(properties.component(DataComponents.TOOL, ShearsItem.createToolProperties()));
	}
}
