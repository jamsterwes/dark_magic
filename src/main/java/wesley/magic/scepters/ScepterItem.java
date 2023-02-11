package wesley.magic.scepters;

import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import wesley.magic.DarkMagicMod;
import wesley.magic.combat.CombatNetworking;
import wesley.magic.combat.HitscanWeapon;

public class ScepterItem extends HitscanWeapon {

    private SoundEvent _useSound = SoundEvents.ENTITY_FIREWORK_ROCKET_LAUNCH;
    private int _cooldownTicks = 10;
    private ScepterMaterials _material;

    public ScepterItem(ScepterMaterials material, Lore lore, Settings settings) {
        // Initialize Item
        super(lore, settings);
        this._material = material;
    }

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
    public void onHit(PlayerEntity player, EntityHitResult hit) {
        // Draw particles
        Vec3d start = player.getCameraPosVec(1.0f);
        Vec3d end = hit.getPos();
        Vec3d dir = end.subtract(start);

        int particles = 1 + (int)end.distanceTo(start);
    
        Vec3d pos;
        for (int i = 1; i <= particles; i++) {
            pos = start.add(dir.multiply((float)i / particles));
            player.world.addParticle(ParticleTypes.GLOW, pos.x, pos.y, pos.z, 0.0, 0.0, 0.0);
        }

        if (hit.getEntity() instanceof ItemEntity) {
            // Try to craft
            ItemEntity itemEntity = (ItemEntity)hit.getEntity();
            ItemStack stack = itemEntity.getStack();
            Item output = ScepterCrafting.tryCraft(_material, stack.getItem());
            if (output != null) {
                CombatNetworking.transformItem(itemEntity, new ItemStack(output, stack.getCount()));
            }
        } else {
            // Damage entity
            CombatNetworking.damageEntity(hit.getEntity(), 7.0f);
        }
    }
}
