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
        // client.getProfiler().push("pick");
        // client.targetedEntity = null;
        // double d = client.interactionManager.getReachDistance();
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
        return ProjectileUtil.raycast(camEntity, vec3d, vec3d3, box, entity -> !entity.isSpectator() && entity.canHit(), e);
        // if (entityHitResult != null) {
        //     Entity entity22 = entityHitResult.getEntity();
        //     Vec3d vec3d4 = entityHitResult.getPos();
        //     double g = vec3d.squaredDistanceTo(vec3d4);
        //     if (bl && g > 9.0) {
        //         client.crosshairTarget = BlockHitResult.createMissed(vec3d4, Direction.getFacing(vec3d2.x, vec3d2.y, vec3d2.z), new BlockPos(vec3d4));
        //     } else if (g < e || client.crosshairTarget == null) {
        //         client.crosshairTarget = entityHitResult;
        //         if (entity22 instanceof LivingEntity || entity22 instanceof ItemFrameEntity) {
        //             client.targetedEntity = entity22;
        //         }
        //     }
        // }
        // client.getProfiler().pop();
    }
}
