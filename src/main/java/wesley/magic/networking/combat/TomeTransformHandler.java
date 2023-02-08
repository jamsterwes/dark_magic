package wesley.magic.networking.combat;

import net.minecraft.entity.Entity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;

@FunctionalInterface
public interface TomeTransformHandler {
    void onTransform(ServerPlayerEntity user, BlockPos other);
}
