package wesley.magic.tomes.special;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.server.network.ServerPlayerEntity;
import wesley.magic.tomes.BaseTomeItem;
import wesley.magic.tomes.TomeProperties;

public class EffectTomeItem extends BaseTomeItem {
    // Hold the status effect for this tome
    private StatusEffect _effect;
    private int _duration, _amplifier;

    public EffectTomeItem(TomeProperties props, StatusEffect effect, int duration, int amplifier, Settings settings) {
        super(props, settings);

        this._effect = effect;
        this._duration = duration;
        this._amplifier = amplifier;
    }

    @Override
    public void onTomeUsed(ServerPlayerEntity player, Entity entity) {
        if (entity instanceof LivingEntity) {
            // Add status effect to entity
            ((LivingEntity)entity).addStatusEffect(new StatusEffectInstance(_effect, _duration, _amplifier), player);
        }
    }
}
