package eu.pintergabor.earlytobed.item;

import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.block.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.BucketItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsage;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.stat.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;

public class WoodenBucketItem
        extends BucketItem {

    // We must duplicate it, because it is private in BucketItem.
    protected final Fluid fluid;

    /**
     * Create a wooden bucket
     *
     * @param fluid Fluids#EMPTY or Fluids#WATER
     */
    public WoodenBucketItem(Fluid fluid, Item.Settings settings) {
        super(fluid, settings);
        this.fluid = fluid;
    }

    /**
     * Fill empty bucket, if possible
     *
     * @param world          World
     * @param user           Player
     * @param itemStack      ItemStack in Player's hand (=one empty wooden bucket)
     * @param blockHitResult Block hit by player
     * @return The usual {@link ActionResult} of {@link #use(World, PlayerEntity, Hand)}
     */
    protected ActionResult fillEmptyBucket(
            World world, PlayerEntity user, ItemStack itemStack, BlockHitResult blockHitResult) {
        BlockPos blockHitPos = blockHitResult.getBlockPos();
        BlockState blockState = world.getBlockState(blockHitPos);
        Block block = blockState.getBlock();
        if (world.canPlayerModifyAt(user, blockHitPos) && (block == Blocks.WATER)) {
            // Try to empty it
            FluidDrainable fluidDrainable = (FluidDrainable) block;
            ItemStack emptiedStack = fluidDrainable.tryDrainFluid(user, world, blockHitPos, blockState);
            // Normally it returns a WATER_BUCKET_ITEM
            if (!emptiedStack.isEmpty()) {
                // Change it to WOODEN_WATER_BUCKET_ITEM
                emptiedStack = new ItemStack(ModItems.WOODEN_WATER_BUCKET_ITEM);
                user.incrementStat(Stats.USED.getOrCreateStat(this));
                fluidDrainable.getBucketFillSound().ifPresent(
                        (sound) -> user.playSound(sound, 1f, 1f));
                world.emitGameEvent(user, GameEvent.FLUID_PICKUP, blockHitPos);
                ItemStack filledStack = ItemUsage.exchangeStack(itemStack, user, emptiedStack);
                if (!world.isClient) {
                    Criteria.FILLED_BUCKET.trigger((ServerPlayerEntity) user, emptiedStack);
                }
                return ActionResult.SUCCESS.withNewHandStack(filledStack);
            }
        }
        return ActionResult.FAIL;
    }

    /**
     * Empty bucket, if possible
     *
     * @param world          World
     * @param user           Player
     * @param itemStack      ItemStack in Player's hand (=one wooden bucket filled with water)
     * @param blockHitResult Block hit by player
     * @return The usual {@link ActionResult} of {@link #use(World, PlayerEntity, Hand)}
     */
    protected ActionResult emptyBucket(
            World world, PlayerEntity user, ItemStack itemStack, BlockHitResult blockHitResult) {
        BlockPos blockHitPos = blockHitResult.getBlockPos();
        Direction direction = blockHitResult.getSide();
        BlockPos blockNextPos = blockHitPos.offset(direction);
        BlockState blockState = world.getBlockState(blockHitPos);
        BlockPos targetPos = (blockState.getBlock() instanceof FluidFillable) ? blockHitPos : blockNextPos;
        if (user.canPlaceOn(blockNextPos, direction, itemStack) &&
                placeFluid(user, world, targetPos, blockHitResult)) {
            onEmptied(user, world, itemStack, targetPos);
            if (user instanceof ServerPlayerEntity) {
                Criteria.PLACED_BLOCK.trigger((ServerPlayerEntity) user, targetPos, itemStack);
            }
            user.incrementStat(Stats.USED.getOrCreateStat(this));
            ItemStack emptiedStack = ItemUsage.exchangeStack(itemStack, user, getEmptiedStack(itemStack, user));
            return ActionResult.SUCCESS.withNewHandStack(emptiedStack);
        }
        return ActionResult.FAIL;
    }

    /**
     * Use wooden bucket on a block
     *
     * @param world World
     * @param user  Player
     * @param hand  Active hand
     * @return The usual {@link ActionResult} values
     */
    @Override
    public ActionResult use(
            World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);
        BlockHitResult blockHitResult = net.minecraft.item.BucketItem.raycast(world, user,
                fluid == Fluids.EMPTY ?
                        RaycastContext.FluidHandling.SOURCE_ONLY : RaycastContext.FluidHandling.NONE);
        if (blockHitResult.getType() == HitResult.Type.MISS) {
            return ActionResult.PASS;
        } else if (blockHitResult.getType() != HitResult.Type.BLOCK) {
            return ActionResult.PASS;
        } else if (fluid == Fluids.EMPTY) {
            return fillEmptyBucket(world, user, itemStack, blockHitResult);
        }
        return emptyBucket(world, user, itemStack, blockHitResult);
    }

    public static ItemStack getEmptiedStack(ItemStack stack, PlayerEntity player) {
        if (!player.getAbilities().creativeMode) {
            return new ItemStack(ModItems.WOODEN_BUCKET_ITEM);
        }
        return stack;
    }
}

