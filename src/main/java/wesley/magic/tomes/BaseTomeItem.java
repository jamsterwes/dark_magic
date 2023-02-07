package wesley.magic.tomes;

import java.util.UUID;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.world.World;
import wesley.magic.networking.DarkMagicNetworkingConstants;
import wesley.magic.networking.combat.TomeCombatListener;

public abstract class BaseTomeItem extends Item {

    private double _maxUseDistance;
    private SoundEvent _useSound;
    private String _tomeID;

    // TODO: abstract away Settings?
    public BaseTomeItem(String tomeID, double maxUseDistance, SoundEvent useSound, Settings settings) {
        // Initialize Item
        super(settings);

        // Save tome-specific properties
        this._tomeID = tomeID;
        this._maxUseDistance = maxUseDistance;
        this._useSound = useSound;

        // Register this tome with networking
        TomeCombatListener.addHandler(tomeID, (ServerPlayerEntity player, Entity other) -> onTomeUsed(player, other));
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        // Verify we are processing the use action on the logical client
        if (!world.isClient()) return super.use(world, user, hand);

        // Raycast to see if target
        MinecraftClient client = MinecraftClient.getInstance();
        HitResult hit = client.crosshairTarget;

        // If hit an entity?
        if (hit.getType() == HitResult.Type.ENTITY) {
            // Cast to entity hit
            EntityHitResult entityHit = (EntityHitResult)hit;

            // Measure distance first
            double dist = hit.getPos().distanceTo(user.getEyePos());

            // If distance in range?
            if (dist <= _maxUseDistance) {
                // Play sound
                user.playSound(_useSound, 1.0f, 1.0f);

                // Call subclass effect
                // TODO: make this packet more robust
                PacketByteBuf buf = PacketByteBufs.create();
                UUID uuid = entityHit.getEntity().getUuid();

                buf.writeString(_tomeID);
                buf.writeUuid(uuid);

                ClientPlayNetworking.send(DarkMagicNetworkingConstants.SHOOT_TOME_PACKET_ID, buf);
            }
        }

        // TODO: potentially don't succeed if no raycast hit
        // Don't affect anything in-hand
        return TypedActionResult.success(user.getStackInHand(hand));
    }

    // Called by tomes to apply their effects
    public abstract void onTomeUsed(ServerPlayerEntity player, Entity other);
}
