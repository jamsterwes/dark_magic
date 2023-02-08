package wesley.magic.tomes.special;

import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import wesley.magic.ExampleMod;
import wesley.magic.tomes.TomeProperties;
import wesley.magic.tomes.UseTomeItem;

import net.minecraft.world.TeleportTarget;
import net.fabricmc.fabric.api.dimension.v1.FabricDimensions;

public class EndlessVoidTomeItem extends UseTomeItem {

    private RegistryKey<World> endlessVoidDim;

    public EndlessVoidTomeItem(TomeProperties props, Settings settings) {
        super(props, settings);

        endlessVoidDim = RegistryKey.of(RegistryKeys.WORLD, new Identifier("dark_magic", "endless_void"));
    }

    @Override
    public void onTomeUsed(ServerPlayerEntity player) {
        // Check what dimension we are in
        if (player.getWorld().getRegistryKey() == World.OVERWORLD) {
            // Teleport into endless void
            teleportToDimension(player, endlessVoidDim);
        } else if (player.getWorld().getRegistryKey() == endlessVoidDim) {
            // Teleport back into overworld
            teleportToDimension(player, World.OVERWORLD);
        }
    }

    // TODO: fix height issue here, maybe find first available height to come back?
    private void teleportToDimension(ServerPlayerEntity player, RegistryKey<World> dimension) {
        // Get original position
        Vec3d origPos = player.getPos();

        // Get current & target world
        ServerWorld currentWorld = player.getWorld();
        ServerWorld targetWorld = player.getServer().getWorld(dimension);
        int targetMinY = targetWorld.getDimension().minY();
        int targetHeight = targetWorld.getDimension().height();

        // Get target position
        double scaleFactor = currentWorld.getDimension().coordinateScale() / targetWorld.getDimension().coordinateScale();
        ExampleMod.LOGGER.info("Coord scale factor: " + Double.toString(scaleFactor));
        Vec3d newPos = origPos.multiply(scaleFactor, 0.0f, scaleFactor);

        // Teleport player
        // FIND suitable Y to teleport to
        Chunk c = targetWorld.getChunk(new BlockPos(newPos));
        for (int y = targetMinY + targetHeight + 16; y >= targetMinY; y--) {
            if (!targetWorld.isAir(new BlockPos(newPos.add(0, y - 1, 0)))) {
                ExampleMod.LOGGER.info("New pos: " + newPos.add(0, y, 0).toString());
                TeleportTarget target = new TeleportTarget(newPos.add(0, y, 0), Vec3d.ZERO, 0.0f, 0.0f);
                FabricDimensions.teleport(player, targetWorld, target);
                break;
            }
        }

        // Otherwise, oops we failed
        // TODO: return false here?
    }
}
