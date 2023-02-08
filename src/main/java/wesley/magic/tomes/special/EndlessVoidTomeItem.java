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

        // File a chunk ticket

        ServerWorld currentWorld = player.getWorld();
        ServerWorld targetWorld = player.getServer().getWorld(dimension);
        // TODO: fix scale?

        Vec3d newPos = origPos.multiply(currentWorld.getDimension().coordinateScale() / targetWorld.getDimension().coordinateScale());
        BlockPos newPosBlock = new BlockPos((int)newPos.x, (int)newPos.y, (int)newPos.z);
        ChunkPos newPosChunk = new ChunkPos(newPosBlock);
        
        // PointOfInterestStorage pointOfInterestStorage = targetWorld.getPointOfInterestStorage();
        // pointOfInterestStorage.preloadChunks(targetWorld, newPosBlock, 16 * 3);
        
        ExampleMod.LOGGER.info("Loading chunk @ " + newPosChunk.toString());

        targetWorld.getChunkManager().addTicket(
            ChunkTicketType.PORTAL,
            newPosChunk,
            3,
            newPosBlock
        );

        player = (ServerPlayerEntity)player.moveToWorld(targetWorld);
        player.setYaw(0.0f);
        player.setPitch(0.0f);
        player.refreshPositionAfterTeleport(newPos);
    }
}
