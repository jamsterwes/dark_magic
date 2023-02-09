package wesley.magic.tomes.special;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.entity.Entity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.server.network.ServerPlayerEntity;
import wesley.magic.state.ServerState;
import wesley.magic.state.DevourState;
import wesley.magic.tomes.TomeProperties;

public class DevourTomeItem extends EffectTomeItem {
    private boolean _feeding;

    public DevourTomeItem(TomeProperties props, StatusEffect devour, boolean feeding) {
        super(props, devour, 20 * 5, 0, new FabricItemSettings().maxCount(1));

        this._feeding = feeding;
    }

    @Override
    public void onTomeUsed(ServerPlayerEntity player, Entity other) {
        // Add NBT data
        ServerState state = ServerState.getServerState(player.getServer());
        state.devourMap.put(other.getUuid(), new DevourState(player.getUuid(), _feeding));
        state.markDirty();

        // Call other on tome used things
        super.onTomeUsed(player, other);
    }
}
