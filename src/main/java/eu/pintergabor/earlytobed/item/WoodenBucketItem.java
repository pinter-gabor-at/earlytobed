package eu.pintergabor.earlytobed.item;

import org.jetbrains.annotations.NotNull;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BucketPickup;
import net.minecraft.world.level.block.LiquidBlockContainer;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;


/**
 * Similar to {@link BucketItem}, but only for water.
 */
public class WoodenBucketItem extends BucketItem {

	// It must be duplicated, because it is private in BucketItem.
	protected final Fluid fluid;

	/**
	 * Create a wooden bucket.
	 *
	 * @param fluid {@link Fluids#EMPTY} or {@link Fluids#WATER}.
	 */
	public WoodenBucketItem(Fluid fluid, Item.Properties properties) {
		super(fluid, fluid != Fluids.EMPTY ?
			properties.craftRemainder(ModItems.WOODEN_BUCKET_ITEM.asItem()) :
			properties);
		this.fluid = fluid;
	}

	/**
	 * Fill empty bucket, if possible.
	 *
	 * @param level          Level.
	 * @param user           Player.
	 * @param itemStack      ItemStack in Player's hand (=one empty wooden bucket).
	 * @param blockHitResult Block hit by player.
	 * @return The usual {@link InteractionResult} of {@link #use(Level, Player, InteractionHand)}.
	 */
	protected InteractionResult fillEmptyBucket(
		Level level, Player user, ItemStack itemStack, BlockHitResult blockHitResult) {
		BlockPos blockHitPos = blockHitResult.getBlockPos();
		BlockState blockState = level.getBlockState(blockHitPos);
		Block block = blockState.getBlock();
		if (block == Blocks.WATER) {
			// Try to empty it
			BucketPickup bucketPickup = (BucketPickup) block;
			ItemStack emptiedStack = bucketPickup.pickupBlock(user, level, blockHitPos, blockState);
			// Normally it returns a WATER_BUCKET_ITEM
			if (!emptiedStack.isEmpty()) {
				// Change it to WOODEN_WATER_BUCKET_ITEM
				emptiedStack = new ItemStack(ModItems.WOODEN_WATER_BUCKET_ITEM.asItem());
				user.awardStat(Stats.ITEM_USED.get(this));
				Fluids.WATER.getPickupSound().ifPresent(
					(sound) -> user.playSound(sound, 1F, 1F));
				level.gameEvent(user, GameEvent.FLUID_PICKUP, blockHitPos);
				ItemStack filledStack = ItemUtils.createFilledResult(itemStack, user, emptiedStack);
				if (!level.isClientSide) {
					CriteriaTriggers.FILLED_BUCKET.trigger((ServerPlayer) user, emptiedStack);
				}
				return InteractionResult.SUCCESS.heldItemTransformedTo(filledStack);
			}
		}
		return InteractionResult.FAIL;
	}

	/**
	 * Empty bucket, if possible.
	 *
	 * @param world          Level.
	 * @param user           Player.
	 * @param itemStack      ItemStack in Player's hand (=one wooden bucket filled with water).
	 * @param blockHitResult Block hit by player.
	 * @return The usual {@link InteractionResult} of {@link #use(Level, Player, InteractionHand)}.
	 */
	protected InteractionResult emptyBucket(
		Level world, Player user, ItemStack itemStack, BlockHitResult blockHitResult) {
		BlockPos blockHitPos = blockHitResult.getBlockPos();
		Direction direction = blockHitResult.getDirection();
		BlockPos blockNextPos = blockHitPos.relative(direction);
		BlockState blockState = world.getBlockState(blockHitPos);
		BlockPos targetPos = (blockState.getBlock() instanceof LiquidBlockContainer) ? blockHitPos : blockNextPos;
		if (emptyContents(user, world, targetPos, blockHitResult, null)) {
			checkExtraContent(user, world, itemStack, targetPos);
			if (user instanceof ServerPlayer) {
				CriteriaTriggers.PLACED_BLOCK.trigger((ServerPlayer) user, targetPos, itemStack);
			}
			user.awardStat(Stats.ITEM_USED.get(this));
			ItemStack emptiedStack = ItemUtils.createFilledResult(itemStack, user, getEmptySuccessItem(itemStack, user));
			return InteractionResult.SUCCESS.heldItemTransformedTo(emptiedStack);
		}
		return InteractionResult.FAIL;
	}

	/**
	 * Use wooden bucket on a block.
	 * <p>
	 * Similar to {@link BucketItem#use(Level, Player, InteractionHand)}.
	 *
	 * @param world Level.
	 * @param user  Player.
	 * @param hand  Active hand.
	 * @return The usual {@link InteractionResult} values.
	 */
	@Override
	@NotNull
	public InteractionResult use(
		@NotNull Level world, Player user, @NotNull InteractionHand hand) {
		ItemStack itemStack = user.getItemInHand(hand);
		BlockHitResult blockHitResult = getPlayerPOVHitResult(world, user,
			fluid == Fluids.EMPTY ?
				ClipContext.Fluid.SOURCE_ONLY : ClipContext.Fluid.NONE);
		if (blockHitResult.getType() == HitResult.Type.MISS) {
			return InteractionResult.PASS;
		} else if (blockHitResult.getType() != HitResult.Type.BLOCK) {
			return InteractionResult.PASS;
		} else if (fluid == Fluids.EMPTY) {
			return fillEmptyBucket(world, user, itemStack, blockHitResult);
		}
		return emptyBucket(world, user, itemStack, blockHitResult);
	}

	/**
	 * Do not empty the bucket in creative mode.
	 * <p>
	 * Similar to {@link BucketItem#getEmptySuccessItem(ItemStack, Player)}.
	 */
	@NotNull
	public static ItemStack getEmptySuccessItem(
		@NotNull ItemStack stack, Player player) {
		return !player.hasInfiniteMaterials() ?
			new ItemStack(ModItems.WOODEN_BUCKET_ITEM.asItem()) : stack;
	}
}
