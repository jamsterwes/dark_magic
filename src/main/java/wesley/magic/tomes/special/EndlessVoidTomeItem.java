package wesley.magic.tomes.special;

import net.minecraft.block.NetherPortalBlock;
import net.minecraft.client.render.DimensionEffects.Overworld;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionTypes;
import wesley.magic.tomes.TomeProperties;
import wesley.magic.tomes.UseTomeItem;

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
            player.moveToWorld(player.getServer().getWorld(endlessVoidDim));
        } else if (player.getWorld().getRegistryKey() == endlessVoidDim) {
            // Teleport back into overworld
            player.moveToWorld(player.getServer().getOverworld());
        }
    }

    private void teleportToDimension(ServerPlayerEntity player, RegistryKey<World> dimension) {
        // Get original position
        Vec3d origPos = player.getPos();
        // TODO: fix scale?
        Vec3d newPos = origPos.multiply(1.0, 1.0, 1.0);

        player.moveToWorld(player.getServer().getWorld(dimension));
        player.setYaw(0.0f);
        player.setPitch(0.0f);
        player.refreshPositionAfterTeleport(newPos);
    }
}
