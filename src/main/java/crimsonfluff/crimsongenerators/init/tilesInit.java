package crimsonfluff.crimsongenerators.init;

import crimsonfluff.crimsongenerators.CrimsonGenerators;
import crimsonfluff.crimsongenerators.tiles.GeneratorTileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class tilesInit {
    public static final DeferredRegister<TileEntityType<?>> TILES = DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, CrimsonGenerators.MOD_ID);

    public static final RegistryObject<TileEntityType<GeneratorTileEntity>> GEN_BLOCK_TILE = TILES.register(
            "cobble_gen_block", () -> TileEntityType.Builder.create(GeneratorTileEntity::createCobble, blocksInit.COBBLE_GEN_BLOCK.get()).build(null));

    public static final RegistryObject<TileEntityType<GeneratorTileEntity>> SAND_BLOCK_TILE = TILES.register(
            "sand_gen_block", () -> TileEntityType.Builder.create(GeneratorTileEntity::createSand, blocksInit.SAND_GEN_BLOCK.get()).build(null));

    public static final RegistryObject<TileEntityType<GeneratorTileEntity>> CLAY_BLOCK_TILE = TILES.register(
            "clay_gen_block", () -> TileEntityType.Builder.create(GeneratorTileEntity::createClay, blocksInit.CLAY_GEN_BLOCK.get()).build(null));

    public static final RegistryObject<TileEntityType<GeneratorTileEntity>> DIRT_BLOCK_TILE = TILES.register(
            "dirt_gen_block", () -> TileEntityType.Builder.create(GeneratorTileEntity::createDirt, blocksInit.DIRT_GEN_BLOCK.get()).build(null));

    public static final RegistryObject<TileEntityType<GeneratorTileEntity>> END_BLOCK_TILE = TILES.register(
            "end_gen_block", () -> TileEntityType.Builder.create(GeneratorTileEntity::createEnd, blocksInit.END_GEN_BLOCK.get()).build(null));

    public static final RegistryObject<TileEntityType<GeneratorTileEntity>> NETHER_BLOCK_TILE = TILES.register(
            "nether_gen_block", () -> TileEntityType.Builder.create(GeneratorTileEntity::createNether, blocksInit.NETHER_GEN_BLOCK.get()).build(null));

    public static final RegistryObject<TileEntityType<GeneratorTileEntity>> OBSIDIAN_BLOCK_TILE = TILES.register(
            "obsidian_gen_block", () -> TileEntityType.Builder.create(GeneratorTileEntity::createObsidian, blocksInit.OBSIDIAN_GEN_BLOCK.get()).build(null));
}
