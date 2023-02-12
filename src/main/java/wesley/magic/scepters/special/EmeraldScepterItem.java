package wesley.magic.scepters.special;

import java.util.Random;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.hit.EntityHitResult;
import wesley.magic.combat.CombatNetworking;
import wesley.magic.scepters.ScepterItem;
import wesley.magic.scepters.ScepterMaterials;

public class EmeraldScepterItem extends ScepterItem {

    private final float SELF_HEAL_CHANCE = 0.5f;
    private final float SELF_HEAL_AMOUNT = 0.5f;

    private Random random = new Random();

    public EmeraldScepterItem(ScepterMaterials material, Lore lore, Settings settings) {
        super(material, lore, settings);
        this.enableAltFire(this, _material.useSound);
    }

    @Override
    public void onHit(PlayerEntity player, EntityHitResult hit) {
        // Do the default stuff
        super.onHit(player, hit);

        // Then chance to heal self
        if (random.nextDouble() >= SELF_HEAL_CHANCE) {
            CombatNetworking.damageEntity(player, -SELF_HEAL_AMOUNT);
        }
    }

    @Override
    public void altFire(PlayerEntity player) {
        // Fully heal self
        float maxHealth = player.getMaxHealth();
        CombatNetworking.damageEntity(player, -maxHealth);

        // TODO: move this elsewhere
        // Do cooldown woo
        player.getItemCooldownManager().set(this, _material.altCooldownTicks);
    }
}
