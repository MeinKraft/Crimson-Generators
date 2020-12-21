package crimsonfluff.crimsongenerators.init;

import crimsonfluff.crimsongenerators.CrimsonGenerators;
import crimsonfluff.crimsongenerators.blocks.*;
import net.minecraft.block.Block;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class blocksInit {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, CrimsonGenerators.MOD_ID);

    public static final RegistryObject<Block> COBBLE_GEN_BLOCK = BLOCKS.register("cobble_gen", () -> new BlockGenCobble());
    public static final RegistryObject<Block> SAND_GEN_BLOCK = BLOCKS.register("sand_gen", () -> new BlockGenSand());
    public static final RegistryObject<Block> CLAY_GEN_BLOCK = BLOCKS.register("clay_gen", () -> new BlockGenClay());
    public static final RegistryObject<Block> DIRT_GEN_BLOCK = BLOCKS.register("dirt_gen", () -> new BlockGenDirt());
    public static final RegistryObject<Block> END_GEN_BLOCK = BLOCKS.register("end_gen", () -> new BlockGenEnd());
    public static final RegistryObject<Block> NETHER_GEN_BLOCK = BLOCKS.register("nether_gen", () -> new BlockGenNether());
    public static final RegistryObject<Block> OBSIDIAN_GEN_BLOCK = BLOCKS.register("obsidian_gen", () -> new BlockGenObsidian());
}
