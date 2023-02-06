package wesley.magic.tomes;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundEvent;

public class EmptyTomeItem extends BaseTomeItem {
    public EmptyTomeItem(double maxUseDistance, SoundEvent useSound, Settings settings) {
        super(maxUseDistance, useSound, settings);
    }

    @Override
    public void onTomeUsed(PlayerEntity player, Entity entity) {
        // DO NOTHING :D
    }
}
