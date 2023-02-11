package wesley.magic.combat;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import wesley.magic.DarkMagicMod;

public class CombatNetworking {

    public class Packets {
        public static final Identifier DAMAGE_ENTITY = new Identifier("dark_magic", "damage_entity");
        public static final Identifier TRANSFORM_ITEM = new Identifier("dark_magic", "transform_item");
    }
    
    @Environment(EnvType.CLIENT)
    public static void damageEntity(Entity target, float damage) {
        // Prepare buffer {target: UUID, damage: float}
        PacketByteBuf buf = PacketByteBufs.create();
        buf.writeInt(target.getId());
        buf.writeFloat(damage);

        // Send damage entity packet to server
        ClientPlayNetworking.send(Packets.DAMAGE_ENTITY, buf);
    }
    
    @Environment(EnvType.CLIENT)
    public static void transformItem(ItemEntity target, ItemStack newStack) {
        // Prepare buffer {target: UUID, damage: float}
        PacketByteBuf buf = PacketByteBufs.create();
        buf.writeInt(target.getId());
        buf.writeItemStack(newStack);

        // Send damage entity packet to server
        ClientPlayNetworking.send(Packets.TRANSFORM_ITEM, buf);
    }

    @Environment(EnvType.CLIENT)
    public static void register() {
        // Register DAMAGE_ENTITY handler
        ServerPlayNetworking.registerGlobalReceiver(Packets.DAMAGE_ENTITY, (MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler handler, PacketByteBuf buf, PacketSender responseSender) -> {
            // Unpack buffer {target: int (ID), damage: float}
            int targetID = buf.readInt();
            Entity target = player.getWorld().getEntityById(targetID);

            if (target == null) return;

            float damage = buf.readFloat();

            // Apply damage
            target.damage(DamageSource.player(player), damage);
        });

        // Register DAMAGE_ENTITY handler
        ServerPlayNetworking.registerGlobalReceiver(Packets.TRANSFORM_ITEM, (MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler handler, PacketByteBuf buf, PacketSender responseSender) -> {
            // Unpack buffer {target: int (ID), newStack: ItemStack}
            int targetID = buf.readInt();
            ItemEntity target = (ItemEntity)player.getWorld().getEntityById(targetID);

            if (target == null) return;

            ItemStack newStack = buf.readItemStack();
            target.setStack(newStack);
        });
    }

}
