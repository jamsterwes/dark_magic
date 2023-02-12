package wesley.magic.scepters;

import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.math.Vec3d;
import wesley.magic.combat.CombatNetworking;
import wesley.magic.combat.HitscanWeapon;

public class ScepterItem extends HitscanWeapon {

    protected ScepterMaterials _material;

    public ScepterItem(ScepterMaterials material, Lore lore, Settings settings) {
        // Initialize Item
        super(lore, settings);
        this._material = material;
    }

    @Override
    public void onHit(PlayerEntity player, EntityHitResult hit) {
        // Set cooldown
        if (_material.cooldownTicks > 0) {
            player.getItemCooldownManager().set(this, _material.cooldownTicks);
        }

        // Play sound
        player.playSound(_material.useSound, SoundCategory.NEUTRAL, 1.0f, 1.0f);

        // Draw particles
        Vec3d start = player.getCameraPosVec(1.0f);
        Vec3d end = hit.getPos();
        Vec3d dir = end.subtract(start);

        int particles = 1 + (int)end.distanceTo(start);
    
        Vec3d pos;
        for (int i = 1; i <= particles; i++) {
            pos = start.add(dir.multiply((float)i / particles));
            player.world.addParticle(_material.particleEffect, pos.x, pos.y, pos.z, 0.0, 0.0, 0.0);
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
            CombatNetworking.damageEntity(hit.getEntity(), (float)_material.damage);
        }
    }
}
