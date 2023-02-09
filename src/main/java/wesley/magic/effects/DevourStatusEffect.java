package wesley.magic.effects;

import java.util.UUID;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.AttributeContainer;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.player.PlayerEntity;
import wesley.magic.state.DevourState;
import wesley.magic.state.ServerState;

public class DevourStatusEffect extends StatusEffect {

    // CONFIG

    private final float dpt = 2.0f;  // Damage / apply
    private final float hpt = 1.0f;  // Heal / apply
    private final int fpt = 1;  // Feed / apply


    private final int ticksPerEffect = 20;

    public DevourStatusEffect() {
        super(StatusEffectCategory.HARMFUL, 0x870E2C);
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return (duration >= 1) && (duration % ticksPerEffect == 0);
    }

    // Called every tick
    @Override
    public void applyUpdateEffect(LivingEntity entity, int amplifier) {
        // Step 1: damage entity
        if (entity.damage(DamageSource.MAGIC, dpt)) {
            // Get devour state for this entity
            UUID uuid = entity.getUuid();
            ServerState serverState = ServerState.getServerState(entity.world.getServer());
            if (serverState.devourMap.containsKey(uuid)) {
                // Get player
                DevourState devourState = serverState.devourMap.get(uuid);

                // CHECK IF PLAYER UUID IS VALID
                PlayerEntity player = entity.world.getPlayerByUuid(devourState.player);
                if (player == null) {
                    removeDevourKey(entity);
                    return;
                }

                // Heal player
                player.heal(hpt);

                // Feed player?
                if (devourState.feeding) {
                    player.getHungerManager().add(fpt, 1.0f);
                }
            }

            // Did we kill the entity?
            if (entity.isDead()) removeDevourKey(entity);
        }
    }

    @Override
    public void onRemoved(LivingEntity entity, AttributeContainer attributes, int amplifier) {
        // Remove devour key
        removeDevourKey(entity);

        // Call super
        super.onRemoved(entity, attributes, amplifier);
    }

    // Helper to remove devour map key
    private void removeDevourKey(LivingEntity entity) {
        ServerState state = ServerState.getServerState(entity.world.getServer());
        state.devourMap.remove(entity.getUuid());
        state.markDirty();
    }
}
