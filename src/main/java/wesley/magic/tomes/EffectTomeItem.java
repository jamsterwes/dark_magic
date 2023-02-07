package wesley.magic.tomes;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundEvent;
import wesley.magic.ExampleMod;

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
        entity.damage(DamageSource.player(player).setUsesMagic(), 5.0f);
    }
}
