package eu.pintergabor.earlytobed;

import net.minecraft.block.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.*;
import net.minecraft.entity.Entity;
import net.minecraft.fluid.Fluid;
import net.minecraft.sound.SoundEvent;
import net.minecraft.stat.Stats;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;

public final class WoodenBucketItem
        extends BucketItem {

    // We must duplicate it, because it is private in BucketItem.
    private final Fluid fluid;

    /**
     * Create a wooden bucket
     * @param fluid Fluids#EMPTY or Fluids#WATER
     */
    public WoodenBucketItem(Fluid fluid, Item.Settings settings) {
        super(fluid, settings);
        this.fluid = fluid;
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);
        BlockHitResult blockHitResult = net.minecraft.item.BucketItem.raycast(world, user,
                this.fluid == Fluids.EMPTY ? RaycastContext.FluidHandling.SOURCE_ONLY : RaycastContext.FluidHandling.NONE);
        if (blockHitResult.getType() == HitResult.Type.MISS) {
            return TypedActionResult.pass(itemStack);
        }
        if (blockHitResult.getType() == HitResult.Type.BLOCK) {
            BlockPos blockHitPos = blockHitResult.getBlockPos();
            Direction direction = blockHitResult.getSide();
            BlockPos blockNextPos = blockHitPos.offset(direction);
            if (!world.canPlayerModifyAt(user, blockHitPos) || !user.canPlaceOn(blockNextPos, direction, itemStack)) {
                return TypedActionResult.fail(itemStack);
            }
            BlockState blockState = world.getBlockState(blockHitPos);
            // Fill empty bucket, if possible
            if (this.fluid == Fluids.EMPTY) {
                FluidDrainable fluidDrainable2;
                ItemStack itemStack2;
                if (blockState.getBlock() instanceof FluidDrainable) {
                    Block block = blockState.getBlock();
                    // Is it a water block?
                    if (block == Blocks.WATER) {
                        // Try to empty it
                        fluidDrainable2 = (FluidDrainable)block;
                        itemStack2 = fluidDrainable2.tryDrainFluid(world, blockHitPos, blockState);
                        // Normally it returns a WATER_BUCKET_ITEM
                        if (!itemStack2.isEmpty()) {
                            // Change it to WOODEN_WATER_BUCKET_ITEM
                            itemStack2 = new ItemStack(Items.WOODEN_WATER_BUCKET_ITEM);
                            user.incrementStat(Stats.USED.getOrCreateStat(this));
                            fluidDrainable2.getBucketFillSound().ifPresent(
                                    sound -> user.playSound((SoundEvent) sound, 1.0f, 1.0f));
                            world.emitGameEvent((Entity) user, GameEvent.FLUID_PICKUP, blockHitPos);
                            ItemStack itemStack3 = ItemUsage.exchangeStack(itemStack, user, itemStack2);
                            return TypedActionResult.success(itemStack3, world.isClient());
                        }
                    }
                }
                return TypedActionResult.fail(itemStack);
            }
            // Empty full bucket, if possible
            BlockPos targetPos = (blockState.getBlock() instanceof FluidFillable) ? blockHitPos : blockNextPos;
            if (this.placeFluid(user, world, targetPos, blockHitResult)) {
                this.onEmptied(user, world, itemStack, targetPos);
                user.incrementStat(Stats.USED.getOrCreateStat(this));
                return TypedActionResult.success(getEmptiedStack(itemStack, user), world.isClient());
            }
            return TypedActionResult.fail(itemStack);
        }
        return TypedActionResult.pass(itemStack);
    }

    public static ItemStack getEmptiedStack(ItemStack stack, PlayerEntity player) {
        if (!player.getAbilities().creativeMode) {
            return new ItemStack(Items.WOODEN_BUCKET_ITEM);
        }
        return stack;
    }
}

