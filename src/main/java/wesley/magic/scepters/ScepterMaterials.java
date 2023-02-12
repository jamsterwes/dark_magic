package wesley.magic.scepters;

import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;

public enum ScepterMaterials {

    DIAMOND (
        5.0f,
        10,
        -1,
        ParticleTypes.GLOW,
        SoundEvents.ENTITY_FIREWORK_ROCKET_LAUNCH
    ),

    EMERALD (
        -5.0f,
        20,
        20*30,
        ParticleTypes.HAPPY_VILLAGER,
        SoundEvents.ENTITY_CAT_HISS
    );
    
    public final double damage;
    public final int cooldownTicks;
    public final int altCooldownTicks;
    public final ParticleEffect particleEffect;
    public final SoundEvent useSound;
    
    ScepterMaterials(double damage, int cooldownTicks, int altCooldownTicks, ParticleEffect particleEffect, SoundEvent useSound) {
        this.damage = damage;
        this.cooldownTicks = cooldownTicks;
        this.altCooldownTicks = altCooldownTicks;
        this.particleEffect = particleEffect;
        this.useSound = useSound;
    }
}
