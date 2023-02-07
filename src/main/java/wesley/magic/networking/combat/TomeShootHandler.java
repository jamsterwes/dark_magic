package wesley.magic.networking.combat;

import net.minecraft.entity.Entity;
import net.minecraft.server.network.ServerPlayerEntity;

@FunctionalInterface
public interface TomeShootHandler {
    void onShoot(ServerPlayerEntity user, Entity other);
}
