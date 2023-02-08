package wesley.magic.tomes;

import java.util.List;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import wesley.magic.networking.DarkMagicNetworkingConstants;
import wesley.magic.networking.combat.TomeCombatListener;
import wesley.magic.networking.combat.TomeUseListener;

public abstract class UseTomeItem extends Item {

    private TomeProperties _props;

    // TODO: abstract away Settings?
    public UseTomeItem(TomeProperties props, Settings settings) {
        // Initialize Item
        super(settings);

        // Save tome-specific properties
        this._props = props;

        // Register this tome with networking
        TomeUseListener.addHandler(_props.TomeID, (ServerPlayerEntity player) -> onTomeUsed(player));
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

        // Play sound
        user.playSound(_props.UseSound, 1.0f, 1.0f);

        // Set cooldown
        if (_props.CooldownTicks > 0) {
            ((PlayerEntity)user).getItemCooldownManager().set(this, _props.CooldownTicks);
        }
        
        // Call subclass effect
        PacketByteBuf buf = PacketByteBufs.create();
        buf.writeString(_props.TomeID);

        ClientPlayNetworking.send(DarkMagicNetworkingConstants.USE_TOME_PACKET_ID, buf);

        // Don't affect anything in-hand
        return TypedActionResult.success(user.getStackInHand(hand));
    }

    // Called by tomes to apply their effects
    public abstract void onTomeUsed(ServerPlayerEntity player);
}
