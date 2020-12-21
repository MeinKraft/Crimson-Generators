package crimsonfluff.crimsongenerators.blocks;

import crimsonfluff.crimsongenerators.tiles.GeneratorTileEntity;
import net.minecraft.block.BlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockReader;

public class BlockGenCobble extends BlockGenBase {
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return GeneratorTileEntity.createCobble();
    }
}
