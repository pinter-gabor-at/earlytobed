package eu.pintergabor.earlytobed.item;

import org.jspecify.annotations.NonNull;

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
 * Wooden bucket item.
 */
public final class WoodenBucketItem extends BucketItem {

	/**
	 * Create a wooden bucket.
	 *
	 * @param content {@link Fluids#EMPTY} or {@link Fluids#WATER}.
	 */
	public WoodenBucketItem(Fluid content, Item.Properties properties) {
		super(content, properties);
	}

	/**
	 * Fill empty bucket, if possible.
	 *
	 * @param level          Level.
	 * @param player         Player.
	 * @param itemStack      ItemStack in Player's hand (=one empty wooden bucket).
	 * @param blockHitResult Block hit by player.
	 * @return The usual {@link InteractionResult} of {@link #use(Level, Player, InteractionHand)}.
	 */
	private @NonNull InteractionResult fillEmptyBucket(
		final @NonNull Level level, final @NonNull Player player,
		final @NonNull ItemStack itemStack, final @NonNull BlockHitResult blockHitResult
	) {
		final BlockPos blockHitPos = blockHitResult.getBlockPos();
		final BlockState blockState = level.getBlockState(blockHitPos);
		final Block block = blockState.getBlock();
		if (block == Blocks.WATER) {
			// Try to empty it
			final BucketPickup bucketPickup = (BucketPickup) block;
			final ItemStack emptiedStack = bucketPickup.pickupBlock(player, level, blockHitPos, blockState);
			// Normally it returns a WATER_BUCKET_ITEM
			if (!emptiedStack.isEmpty()) {
				// Change it to WOODEN_WATER_BUCKET_ITEM
				final ItemStack modEmptiedStack = new ItemStack(ModItems.WOODEN_WATER_BUCKET_ITEM);
				player.awardStat(Stats.ITEM_USED.get(this));
				bucketPickup.getPickupSound().ifPresent(
					(sound) -> player.playSound(sound, 1F, 1F));
				level.gameEvent(player, GameEvent.FLUID_PICKUP, blockHitPos);
				final ItemStack filledStack = ItemUtils.createFilledResult(itemStack, player, modEmptiedStack);
				if (!level.isClientSide()) {
					CriteriaTriggers.FILLED_BUCKET.trigger((ServerPlayer) player, modEmptiedStack);
				}
				return InteractionResult.SUCCESS.heldItemTransformedTo(filledStack);
			}
		}
		return InteractionResult.FAIL;
	}

	/**
	 * Empty bucket, if possible.
	 *
	 * @param level          Level.
	 * @param player         Player.
	 * @param itemStack      ItemStack in Player's hand (=one wooden bucket filled with water).
	 * @param blockHitResult Block hit by player.
	 * @return The usual {@link InteractionResult} of {@link #use(Level, Player, InteractionHand)}.
	 */
	private @NonNull InteractionResult emptyBucket(
		final @NonNull Level level, final @NonNull Player player,
		final @NonNull ItemStack itemStack, final @NonNull BlockHitResult blockHitResult
	) {
		BlockPos blockHitPos = blockHitResult.getBlockPos();
		Direction direction = blockHitResult.getDirection();
		BlockPos blockNextPos = blockHitPos.relative(direction);
		BlockState blockState = level.getBlockState(blockHitPos);
		BlockPos targetPos = (blockState.getBlock() instanceof LiquidBlockContainer) ? blockHitPos : blockNextPos;
		if (emptyContents(player, level, targetPos, blockHitResult)) {
			checkExtraContent(player, level, itemStack, targetPos);
			if (player instanceof ServerPlayer) {
				CriteriaTriggers.PLACED_BLOCK.trigger((ServerPlayer) player, targetPos, itemStack);
			}
			player.awardStat(Stats.ITEM_USED.get(this));
			ItemStack emptiedStack = ItemUtils.createFilledResult(itemStack, player, getEmptySuccessItem(itemStack, player));
			return InteractionResult.SUCCESS.heldItemTransformedTo(emptiedStack);
		}
		return InteractionResult.FAIL;
	}

	/**
	 * Use wooden bucket on a block.
	 * <p>
	 * Similar to {@link BucketItem#use(Level, Player, InteractionHand)}.
	 *
	 * @param level  Level.
	 * @param player Player.
	 * @param hand   Active hand.
	 * @return The usual {@link InteractionResult} values.
	 */
	@Override
	public @NonNull InteractionResult use(
		final @NonNull Level level, final @NonNull Player player,
		final @NonNull InteractionHand hand
	) {
		final ItemStack itemStack = player.getItemInHand(hand);
		final BlockHitResult blockHitResult = getPlayerPOVHitResult(level, player,
			content == Fluids.EMPTY ?
				ClipContext.Fluid.SOURCE_ONLY : ClipContext.Fluid.NONE);
		if (blockHitResult.getType() == HitResult.Type.MISS) {
			return InteractionResult.PASS;
		} else if (blockHitResult.getType() != HitResult.Type.BLOCK) {
			return InteractionResult.PASS;
		} else if (content == Fluids.EMPTY) {
			return fillEmptyBucket(level, player, itemStack, blockHitResult);
		}
		return emptyBucket(level, player, itemStack, blockHitResult);
	}

	/**
	 * Do not empty the bucket in creative mode.
	 * <p>
	 * Similar to {@link BucketItem#getEmptySuccessItem(ItemStack, Player)}.
	 */
	public static @NonNull ItemStack getEmptySuccessItem(
		final @NonNull ItemStack stack, final @NonNull Player player
	) {
		return !player.hasInfiniteMaterials() ?
			new ItemStack(ModItems.WOODEN_BUCKET_ITEM) : stack;
	}
}
