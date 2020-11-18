package crimsonfluff.crimsongenerators.init;

import crimsonfluff.crimsongenerators.CrimsonGenerators;
import crimsonfluff.crimsongenerators.blocks.BlockCoalGen;
import crimsonfluff.crimsongenerators.blocks.BlockLavaGen;
import crimsonfluff.crimsongenerators.blocks.BlockWaterGen;
import net.minecraft.block.Block;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class blocksInit {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, CrimsonGenerators.MOD_ID);

    public static final RegistryObject<Block> COAL_GEN_BLOCK = BLOCKS.register("coal_gen_block", BlockCoalGen::new);
    public static final RegistryObject<Block> LAVA_GEN_BLOCK = BLOCKS.register("lava_gen_block", BlockLavaGen::new);
    public static final RegistryObject<Block> WATER_GEN_BLOCK = BLOCKS.register("water_gen_block", BlockWaterGen::new);
}
