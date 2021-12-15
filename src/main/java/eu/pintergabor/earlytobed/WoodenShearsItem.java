package eu.pintergabor.earlytobed;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.SheepEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;

import static net.minecraft.entity.EntityType.SHEEP;

public class WoodenShearsItem
        extends ShearsItem {

    public WoodenShearsItem(Item.Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult useOnEntity(ItemStack stack, PlayerEntity user, LivingEntity entity, Hand hand) {
        if (entity.getType() == SHEEP) {
            Global.LOGGER.info("Sheep click");
        }
        return super.useOnEntity(stack, user, entity, hand);
    }
}

