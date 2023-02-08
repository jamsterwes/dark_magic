package wesley.magic.tomes.special;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;
import wesley.magic.tomes.BlockTomeItem;
import wesley.magic.tomes.TomeProperties;

public class FlockTomeItem extends BlockTomeItem {
    public FlockTomeItem(TomeProperties props, Settings settings) {
        super(props, settings);
    }

    @Override
    public boolean canTomeTransform(Block block) {
        return Blocks.GRASS_BLOCK == block;
    }

    @Override
    public void onTomeUsed(ServerPlayerEntity player, BlockPos pos) {
        // Give hunger
        player.getHungerManager().add(2, 1.0f);

        // Set block state
        BlockState bs = Blocks.DIRT.getDefaultState();
        player.getWorld().setBlockState(pos, bs);
    }
}
