package wesley.magic.scepters;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import wesley.magic.networking.DarkMagicNetworkingConstants;
import wesley.magic.scepters.projectiles.ScepterProjectileEntity;

public abstract class BaseScepterItem extends Item {

    private String _netID;
    private SoundEvent _useSound = SoundEvents.BLOCK_AMETHYST_BLOCK_CHIME;
    private int _cooldownTicks = 10;

    public BaseScepterItem(String netID, Settings settings) {
        // Initialize Item
        super(settings);

        // Save tome-specific properties
        this._netID = netID;
    }

    // @Override
    // public boolean hasGlint(ItemStack itemStack) {
    //     return true;
    // }

    // @Override
    // public void appendTooltip(ItemStack itemStack, World world, List<Text> tooltip, TooltipContext tooltipContext) {
    //     for (Text text : _props.lore) {
    //         tooltip.add(text);
    //     }
    // }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        // Set cooldown
        if (_cooldownTicks > 0) {
            ((PlayerEntity)user).getItemCooldownManager().set(this, _cooldownTicks);
        }
        
        // Play sound
        ItemStack itemStack = user.getStackInHand(hand);
        world.playSound(null, user.getX(), user.getY(), user.getZ(), _useSound, SoundCategory.NEUTRAL, 0.5F, 1F);
        
        if (!world.isClient) {
            ScepterProjectileEntity snowballEntity = new ScepterProjectileEntity(world, user);
            // snowballEntity.setItem(new ItemStack(Items.DIAMOND));
            snowballEntity.setVelocity(user, user.getPitch(), user.getYaw(), 0.0f, 5.0f, 0.1f);
            world.spawnEntity(snowballEntity);
        }
    
        return TypedActionResult.success(itemStack, world.isClient());
    }
}
