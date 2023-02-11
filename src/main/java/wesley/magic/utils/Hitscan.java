package wesley.magic.utils;

import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileUtil;
import net.minecraft.util.hit.*;
import net.minecraft.util.math.*;

public class Hitscan {
    // Perform hitscan from player crosshair in look direction to hit first entity
    public static EntityHitResult playerHitscan(PlayerEntity player, double maxDistance) {
        MinecraftClient client = MinecraftClient.getInstance();
        Entity camEntity = client.getCameraEntity();
        if (camEntity == null) {
            return null;
        }
        if (client.world == null) {
            return null;
        }
        client.crosshairTarget = camEntity.raycast(maxDistance, 1.0f, false);
        Vec3d vec3d = camEntity.getCameraPosVec(1.0f);
        double e = maxDistance;
        e *= e;
        if (client.crosshairTarget != null) {
            e = client.crosshairTarget.getPos().squaredDistanceTo(vec3d);
        }
        Vec3d vec3d2 = camEntity.getRotationVec(1.0f);
        Vec3d vec3d3 = vec3d.add(vec3d2.x * maxDistance, vec3d2.y * maxDistance, vec3d2.z * maxDistance);
        Box box = camEntity.getBoundingBox().stretch(vec3d2.multiply(maxDistance)).expand(1.0, 1.0, 1.0);
        return ProjectileUtil.raycast(camEntity, vec3d, vec3d3, box, entity -> !entity.isSpectator(), e);
    }
}
