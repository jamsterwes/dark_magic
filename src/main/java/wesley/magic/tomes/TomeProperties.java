package wesley.magic.tomes;

import net.minecraft.sound.SoundEvent;

public class TomeProperties {
    public double MaxUseDistance;
    public SoundEvent UseSound;
    public String TomeID;
    public int CooldownTicks;

    public TomeProperties(double maxUseDistance, SoundEvent useSound, String tomeID, int cooldownTicks) {
        MaxUseDistance = maxUseDistance;
        UseSound = useSound;
        TomeID = tomeID;
        CooldownTicks = cooldownTicks;
    }
}
