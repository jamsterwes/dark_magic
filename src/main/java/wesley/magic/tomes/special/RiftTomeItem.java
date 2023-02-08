package wesley.magic.tomes.special;

import net.minecraft.entity.Entity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.event.GameEvent;
import wesley.magic.tomes.BaseTomeItem;
import wesley.magic.tomes.TomeProperties;

public class RiftTomeItem extends BaseTomeItem {

    public RiftTomeItem(TomeProperties props, Settings settings) {
        super(props, settings);
    }

    @Override
    public void onTomeUsed(ServerPlayerEntity player, Entity other) {
        double d = other.getX();
        double e = other.getY();
        double f = other.getZ();
        for (int i = 0; i < 16; ++i) {

            // Get new teleport pos
            double g = other.getX() + (player.getRandom().nextDouble() - 0.5) * 16.0;
            double h = MathHelper.clamp(other.getY() + (double)(player.getRandom().nextInt(16) - 8), (double)other.world.getBottomY(), (double)(other.world.getBottomY() + ((ServerWorld)other.world).getLogicalHeight() - 1));
            double j = other.getZ() + (player.getRandom().nextDouble() - 0.5) * 16.0;

            // Force other to stop riding vehicle (see baby zombie chicken)
            if (other.hasVehicle()) {
                other.stopRiding();
            }
            
            // Try to teleport, see if space empty
            other.requestTeleport(g, h, j);
            if (!other.world.isSpaceEmpty(other)) {
                other.requestTeleport(d, e, f);
                continue;
            }

            // Finalize teleport
            other.world.emitGameEvent(GameEvent.TELEPORT, new Vec3d(d, e, f), GameEvent.Emitter.of(other));
            break;
        }
    }
}
