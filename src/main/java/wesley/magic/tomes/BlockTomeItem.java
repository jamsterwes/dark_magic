package wesley.magic.tomes;

import java.util.List;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.block.Block;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import wesley.magic.networking.DarkMagicNetworkingConstants;
import wesley.magic.networking.combat.TomeAlchemyListener;

public abstract class BlockTomeItem extends Item {

    private TomeProperties _props;

    // TODO: abstract away Settings?
    public BlockTomeItem(TomeProperties props, Settings settings) {
        // Initialize Item
        super(settings);

        // Save tome-specific properties
        this._props = props;

        // Register this tome with networking
        TomeAlchemyListener.addHandler(_props.TomeID, (ServerPlayerEntity player, BlockPos pos) -> onTomeUsed(player, pos));
    }

    @Override
    public boolean hasGlint(ItemStack itemStack) {
        return true;
    }

    @Override
    public void appendTooltip(ItemStack itemStack, World world, List<Text> tooltip, TooltipContext tooltipContext) {
        for (Text text : _props.lore) {
            tooltip.add(text);
        }
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        // Verify we are processing the use action on the logical client
        if (!world.isClient()) return super.use(world, user, hand);

        // Raycast to see if target
        MinecraftClient client = MinecraftClient.getInstance();
        HitResult hit = client.crosshairTarget;

        // If hit an entity?
        if (hit.getType() == HitResult.Type.BLOCK) {
            // Cast to block hit
            BlockHitResult blockHit = (BlockHitResult)hit;

            // Can we even use?
            Block block = world.getBlockState(blockHit.getBlockPos()).getBlock();
            if (!canTomeTransform(block)) {
                return TypedActionResult.fail(user.getStackInHand(hand));
            }

            // Measure distance first
            double dist = hit.getPos().distanceTo(user.getEyePos());

            // If distance in range?
            if (dist <= _props.MaxUseDistance) {
                // Play sound
                user.playSound(_props.UseSound, 1.0f, 1.0f);

                // Set cooldown
                if (_props.CooldownTicks > 0) {
                    ((PlayerEntity)user).getItemCooldownManager().set(this, _props.CooldownTicks);
                }

                // TODO: Draw particle trail
                
                // Call subclass effect
                // TODO: make this packet more robust
                PacketByteBuf buf = PacketByteBufs.create();

                buf.writeString(_props.TomeID);
                buf.writeBlockPos(blockHit.getBlockPos());

                ClientPlayNetworking.send(DarkMagicNetworkingConstants.TRANSFORM_TOME_PACKET_ID, buf);

                // Don't affect anything in-hand
                return TypedActionResult.success(user.getStackInHand(hand));
            }
        }
        
        return TypedActionResult.fail(user.getStackInHand(hand));
    }

    // Called by tomes to see if we can use it?
    public abstract boolean canTomeTransform(Block block);

    // Called by tomes to apply their effects
    public abstract void onTomeUsed(ServerPlayerEntity player, BlockPos pos);
}
