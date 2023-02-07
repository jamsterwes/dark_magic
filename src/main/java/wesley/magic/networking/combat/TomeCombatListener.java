package wesley.magic.networking.combat;

import java.util.HashMap;
import java.util.UUID;

import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.entity.Entity;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import wesley.magic.networking.DarkMagicNetworkingConstants;

public class TomeCombatListener {

    private static HashMap<String, TomeShootHandler> _handlers = new HashMap<>();

    public static void addHandler(String tomeID, TomeShootHandler handler) {
        // TODO: QoL checks... yada yada yada

        if (_handlers.containsKey(tomeID)) return;

        _handlers.put(tomeID, handler);
    }

    public static void register() {
        ServerPlayNetworking.registerGlobalReceiver(DarkMagicNetworkingConstants.SHOOT_TOME_PACKET_ID, (MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler handler, PacketByteBuf buf, PacketSender responseSender) -> {
            // TODO: TODO

            String tomeID = buf.readString();
            UUID uuid = buf.readUuid();

            Entity entity = server.getOverworld().getEntity(uuid);
            double dist = player.getEyePos().distanceTo(entity.getPos());
            // TODO: actually read Tome stats
            if (dist > 7.5f) return;

            // TODO: warning message unknown tome
            if (!_handlers.containsKey(tomeID)) return;

            server.execute(() -> {
                _handlers.get(tomeID).onShoot(player, entity);
            });
        });
    }
}