package wesley.magic.tomes;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.entity.Entity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundEvents;
import wesley.magic.ExampleMod;
import wesley.magic.state.ServerState;

public class DevourTomeItem extends EffectTomeItem {
    public DevourTomeItem(StatusEffect devour) {
        super("devour_tome", devour, 5 * 20, 0,
			20, SoundEvents.ENTITY_SPIDER_HURT, new FabricItemSettings().maxCount(1));
    }

    @Override
    public void onTomeUsed(ServerPlayerEntity player, Entity other) {
        // Add NBT data
        ServerState state = ServerState.getServerState(player.getServer());
        state.devourMap.put(other.getUuid(), player.getUuid());
        ExampleMod.LOGGER.info(state.devourMap.toString());
        state.markDirty();

        // Call other on tome used things
        super.onTomeUsed(player, other);
    }
}
