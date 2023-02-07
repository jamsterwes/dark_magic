package wesley.magic.effects;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;

public class DecayStatusEffect extends StatusEffect {

    // CONFIG
    private final float dpt = 2.0f;
    private final int ticksPerEffect = 20;

    public DecayStatusEffect() {
        super(StatusEffectCategory.HARMFUL, 0x870E2C);
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return (duration >= 1) && (duration % ticksPerEffect == 0);
    }

    // Called every tick
    @Override
    public void applyUpdateEffect(LivingEntity entity, int amplifier) {
        entity.damage(DamageSource.MAGIC, dpt);
    }
}
