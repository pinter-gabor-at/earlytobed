package eu.pintergabor.earlytobed;

import net.minecraft.block.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.entity.Entity;
import net.minecraft.fluid.FlowableFluid;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.FluidModificationItem;
import net.minecraft.item.ItemUsage;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.event.GameEvent;
import org.jetbrains.annotations.Nullable;

import static eu.pintergabor.earlytobed.EarlyToBed.WOODEN_BUCKET_ITEM;
import static eu.pintergabor.earlytobed.EarlyToBed.WOODEN_WATER_BUCKET_ITEM;

public class WoodenBucketItem
        extends Item
        implements FluidModificationItem {
    private final Fluid fluid;

    public WoodenBucketItem(Fluid fluid, Item.Settings settings) {
        super(settings);
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
                            itemStack2 = new ItemStack(WOODEN_WATER_BUCKET_ITEM);
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
            return new ItemStack(WOODEN_BUCKET_ITEM);
        }
        return stack;
    }

    @Override
    public void onEmptied(@Nullable PlayerEntity player, World world, ItemStack stack, BlockPos pos) {
    }

    @Override
    public boolean placeFluid(@Nullable PlayerEntity player, World world, BlockPos pos, @Nullable BlockHitResult hitResult) {
        boolean bl2;
        if (!(this.fluid instanceof FlowableFluid)) {
            return false;
        }
        BlockState blockState = world.getBlockState(pos);
        Block block = blockState.getBlock();
        Material material = blockState.getMaterial();
        boolean bl = blockState.canBucketPlace(this.fluid);
        bl2 = blockState.isAir() || bl || block instanceof FluidFillable &&
                ((FluidFillable)((Object)block)).canFillWithFluid(world, pos, blockState, this.fluid);
        if (!bl2) {
            return hitResult != null && this.placeFluid(player, world, hitResult.getBlockPos().offset(hitResult.getSide()), null);
        }
        if (world.getDimension().isUltrawarm()) {
            int i = pos.getX();
            int j = pos.getY();
            int k = pos.getZ();
            world.playSound(player, pos, SoundEvents.BLOCK_FIRE_EXTINGUISH, SoundCategory.BLOCKS, 0.5f, 2.6f + (world.random.nextFloat() - world.random.nextFloat()) * 0.8f);
            for (int l = 0; l < 8; ++l) {
                world.addParticle(ParticleTypes.LARGE_SMOKE, (double)i + Math.random(), (double)j + Math.random(), (double)k + Math.random(), 0.0, 0.0, 0.0);
            }
            return true;
        }
        if (block instanceof FluidFillable) {
            ((FluidFillable)((Object)block)).tryFillWithFluid(world, pos, blockState, ((FlowableFluid)this.fluid).getStill(false));
            this.playEmptyingSound(player, world, pos);
            return true;
        }
        if (!world.isClient && bl && !material.isLiquid()) {
            world.breakBlock(pos, true);
        }
        if (world.setBlockState(pos, this.fluid.getDefaultState().getBlockState(), Block.NOTIFY_ALL | Block.REDRAW_ON_MAIN_THREAD) || blockState.getFluidState().isStill()) {
            this.playEmptyingSound(player, world, pos);
            return true;
        }
        return false;
    }

    protected void playEmptyingSound(@Nullable PlayerEntity player, WorldAccess world, BlockPos pos) {
        SoundEvent soundEvent = SoundEvents.ITEM_BUCKET_EMPTY;
        world.playSound(player, pos, soundEvent, SoundCategory.BLOCKS, 1.0f, 1.0f);
        world.emitGameEvent((Entity)player, GameEvent.FLUID_PLACE, pos);
    }
}

