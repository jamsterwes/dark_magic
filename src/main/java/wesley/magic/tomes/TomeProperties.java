package wesley.magic.tomes;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.sound.SoundEvent;
import net.minecraft.text.Text;

public class TomeProperties {
    public double MaxUseDistance;
    public SoundEvent UseSound;
    public String TomeID;
    public int CooldownTicks;
    public List<Text> lore = new ArrayList<>();

    public TomeProperties(double maxUseDistance, SoundEvent useSound, String tomeID, int cooldownTicks) {
        MaxUseDistance = maxUseDistance;
        UseSound = useSound;
        TomeID = tomeID;
        CooldownTicks = cooldownTicks;
    }

    public TomeProperties addLore(Text loreLine) {
        lore.add(loreLine);
        return this;
    }
}
