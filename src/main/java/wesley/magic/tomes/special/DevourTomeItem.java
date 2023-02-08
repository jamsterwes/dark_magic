package wesley.magic.tomes.special;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.entity.Entity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.server.network.ServerPlayerEntity;
import wesley.magic.ExampleMod;
import wesley.magic.state.ServerState;
import wesley.magic.tomes.DarkMagicTomes;

public class DevourTomeItem extends EffectTomeItem {
    public DevourTomeItem(StatusEffect devour) {
        super(DarkMagicTomes.DEVOUR_PROPERTIES, devour, 20 * 5, 0, new FabricItemSettings().maxCount(1));
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
