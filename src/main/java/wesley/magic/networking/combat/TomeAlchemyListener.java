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
import net.minecraft.util.math.BlockPos;
import wesley.magic.networking.DarkMagicNetworkingConstants;

public class TomeAlchemyListener {

    private static HashMap<String, TomeTransformHandler> _handlers = new HashMap<>();

    public static void addHandler(String tomeID, TomeTransformHandler handler) {
        // TODO: QoL checks... yada yada yada

        if (_handlers.containsKey(tomeID)) return;

        _handlers.put(tomeID, handler);
    }

    public static void register() {
        ServerPlayNetworking.registerGlobalReceiver(DarkMagicNetworkingConstants.TRANSFORM_TOME_PACKET_ID, (MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler handler, PacketByteBuf buf, PacketSender responseSender) -> {

            String tomeID = buf.readString();
            BlockPos pos = buf.readBlockPos();

            // TODO: warning message unknown tome
            if (!_handlers.containsKey(tomeID)) return;

            server.execute(() -> {
                _handlers.get(tomeID).onTransform(player, pos);
            });
        });
    }
}