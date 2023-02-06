package wesley.magic.tomes;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.world.World;

public abstract class BaseTomeItem extends Item {

    private double _maxUseDistance;
    private SoundEvent _useSound;

    // TODO: abstract away Settings?
    public BaseTomeItem(double maxUseDistance, SoundEvent useSound, Settings settings) {
        // Initialize Item
        super(settings);

        // Save tome-specific properties
        this._maxUseDistance = maxUseDistance;
        this._useSound = useSound;
    }

    @Override
    @Environment(EnvType.CLIENT)
    public TypedActionResult<ItemStack> use(World world, PlayerEntity playerEntity, Hand hand) {
        // TODO: does this code fail on server?
        // Raycast to see if target
        MinecraftClient client = MinecraftClient.getInstance();
        HitResult hit = client.crosshairTarget;

        // If hit an entity?
        if (hit.getType() == HitResult.Type.ENTITY) {
            // Cast to entity hit
            EntityHitResult entityHit = (EntityHitResult)hit;

            // Measure distance first
            double dist = hit.getPos().distanceTo(playerEntity.getEyePos());

            // If distance in range?
            if (dist <= _maxUseDistance) {
                // Play sound
                playerEntity.playSound(_useSound, 1.0f, 1.0f);

                // Call subclass effect
                onTomeUsed(playerEntity, entityHit.getEntity());
            }
        }

        // TODO: potentially don't succeed if no raycast hit
        // Don't affect anything in-hand
        return TypedActionResult.success(playerEntity.getStackInHand(hand));
    }

    // Called by tomes to apply their effects
    // public abstract static void onTomeUsed(PlayerEntity player, Entity other);
}
