package wesley.magic.state;

import java.util.HashMap;
import java.util.UUID;

import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.PersistentState;
import net.minecraft.world.PersistentStateManager;
import net.minecraft.world.World;

public class ServerState extends PersistentState {
    
    public HashMap<UUID, UUID> devourMap = new HashMap<>();

    @Override
    public NbtCompound writeNbt(NbtCompound nbt) {
        // Create devour map
        NbtCompound devourMapCompound = new NbtCompound();
        devourMap.forEach((UUID entity, UUID player) -> {
            devourMapCompound.putString(String.valueOf(entity), String.valueOf(player));
        });
        nbt.put("devourMap", devourMapCompound);
        return nbt;
    }

    public static ServerState createFromNbt(NbtCompound tag) {
        ServerState serverState = new ServerState();

        NbtCompound devourMapTag = tag.getCompound("devourMap");
        devourMapTag.getKeys().forEach(key -> {
            // Get entity
            UUID entity = UUID.fromString(key);

            // Get player
            UUID player = UUID.fromString(devourMapTag.getString(key));

            // Add to map
            serverState.devourMap.put(entity, player);
        });

        return serverState;
    }
 
    public static ServerState getServerState(MinecraftServer server) {
        // First we get the persistentStateManager for the OVERWORLD
        PersistentStateManager persistentStateManager = server
                .getWorld(World.OVERWORLD).getPersistentStateManager();
 
        // Calling this reads the file from the disk if it exists, or creates a new one and saves it to the disk
        // You need to use a unique string as the key. You should already have a MODID variable defined by you somewhere in your code. Use that.
        ServerState serverState = persistentStateManager.getOrCreate(
                ServerState::createFromNbt,
                ServerState::new,
                "dark_magic");
 
        serverState.markDirty(); // YOU MUST DO THIS!!!! Or data wont be saved correctly.
 
        return serverState;
    }

}
