package wesley.magic.networking.combat;

import net.minecraft.server.network.ServerPlayerEntity;

@FunctionalInterface
public interface TomeUseHandler {
    void onUse(ServerPlayerEntity player);
}
