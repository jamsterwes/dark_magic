package wesley.magic.tomes;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundEvent;

public class EffectTomeItem extends BaseTomeItem {
    // Hold the status effect for this tome
    private StatusEffect _effect;
    private int _duration, _amplifier;

    public EffectTomeItem(String tomeID, StatusEffect effect, int duration, int amplifier, double maxUseDistance, SoundEvent useSound, Settings settings) {
        super(tomeID, maxUseDistance, useSound, settings);

        this._effect = effect;
        this._duration = duration;
        this._amplifier = amplifier;
    }

    @Override
    public void onTomeUsed(ServerPlayerEntity player, Entity entity) {
        if (entity instanceof LivingEntity) {
            
            ((LivingEntity)entity).addStatusEffect(new StatusEffectInstance(_effect, _duration, _amplifier), player);
        }
    }
}
