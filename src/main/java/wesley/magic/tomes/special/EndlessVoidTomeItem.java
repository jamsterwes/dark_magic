package wesley.magic.tomes.special;

import net.minecraft.block.NetherPortalBlock;
import net.minecraft.client.render.DimensionEffects.Overworld;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ChunkTicketType;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionTypes;
import net.minecraft.world.poi.PointOfInterestStorage;
import wesley.magic.tomes.TomeProperties;
import wesley.magic.tomes.UseTomeItem;
import wesley.magic.ExampleMod;

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

    private void teleportToDimension(ServerPlayerEntity player, RegistryKey<World> dimension) {
        // Get original position
        Vec3d origPos = player.getPos();

        // Get target world
        ServerWorld targetWorld = player.getServer().getWorld(dimension);

        // Get target position
        float scaleFactor = currentWorld.getDimension().coordinateScale() / targetWorld.getDimension().coordinateScale();
        Vec3d newPos = origPos.multiply(scaleFactor, 1.0f, scaleFactor);

        // Teleport player
        TeleportTarget target = new TeleportTarget(newPos, Vec3d.ZERO, 0.0f, 0.0f);
        FabricDimensions.teleport(player, targetWorld, target);
    }
}
