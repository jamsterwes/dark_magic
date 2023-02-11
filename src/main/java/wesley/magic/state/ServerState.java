package wesley.magic.state;

import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.PersistentState;
import net.minecraft.world.PersistentStateManager;
import net.minecraft.world.World;

public class ServerState extends PersistentState {

    @Override
    public NbtCompound writeNbt(NbtCompound nbt) {
        return nbt;
    }

    public static ServerState createFromNbt(NbtCompound tag) {
        ServerState serverState = new ServerState();
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
