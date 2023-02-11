package wesley.magic.scepters;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import wesley.magic.combat.CombatNetworking;
import wesley.magic.combat.HitscanWeapon;

public class ScepterItem extends HitscanWeapon {

    private SoundEvent _useSound = SoundEvents.BLOCK_AMETHYST_BLOCK_CHIME;
    private int _cooldownTicks = 10;

    public ScepterItem(Settings settings) {
        // Initialize Item
        super(settings);
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
        world.playSound(null, user.getX(), user.getY(), user.getZ(), _useSound, SoundCategory.NEUTRAL, 1.0f, 1.0f);

        // Call super use
        return super.use(world, user, hand);
    }

    @Override
    public void onHit(PlayerEntity player, Entity hit) {
        // Damage entity
        CombatNetworking.damageEntity(hit, 7.0f);
    }
}
