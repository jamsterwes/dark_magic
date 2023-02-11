package wesley.magic.combat;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.entity.Entity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;

public class CombatNetworking {

    public class Packets {
        public static final Identifier DAMAGE_ENTITY = new Identifier("dark_magic", "damage_entity");
    }
    
    @Environment(EnvType.CLIENT)
    public static void damageEntity(Entity target, float damage) {
        // Prepare buffer {target: UUID, damage: float}
        PacketByteBuf buf = PacketByteBufs.create();
        buf.writeUuid(target.getUuid());
        buf.writeFloat(damage);

        // Send damage entity packet to server
        ClientPlayNetworking.send(Packets.DAMAGE_ENTITY, buf);
    }

    public static void register() {
        // Register DAMAGE_ENTITY handler
        ServerPlayNetworking.registerGlobalReceiver(Packets.DAMAGE_ENTITY, (MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler handler, PacketByteBuf buf, PacketSender responseSender) -> {
            // Unpack buffer {target: UUID, damage: float}
            Entity target = player.getWorld().getEntity(buf.readUuid());
            float damage = buf.readFloat();

            // Apply damage
            target.damage(DamageSource.player(player), damage);
        });
    }

}
