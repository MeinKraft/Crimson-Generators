package crimsonfluff.crimsongenerators.init;

import crimsonfluff.crimsongenerators.CrimsonGenerators;

import crimsonfluff.crimsongenerators.tiles.TileCoalGen;
import crimsonfluff.crimsongenerators.tiles.TileLavaGen;
import crimsonfluff.crimsongenerators.tiles.TileWaterGen;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class tilesInit {
    public static final DeferredRegister<TileEntityType<?>> TILES =
            DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, CrimsonGenerators.MOD_ID);

    public static final RegistryObject<TileEntityType<TileCoalGen>> COAL_GEN_TILE = TILES.register("coal_gen_tile",
            ()-> TileEntityType.Builder.create(TileCoalGen::new, blocksInit.COAL_GEN_BLOCK.get()).build(null));

    public static final RegistryObject<TileEntityType<TileLavaGen>> LAVA_GEN_TILE = TILES.register("lava_gen_tile",
            ()-> TileEntityType.Builder.create(TileLavaGen::new, blocksInit.LAVA_GEN_BLOCK.get()).build(null));

    public static final RegistryObject<TileEntityType<TileWaterGen>> WATER_GEN_TILE = TILES.register("water_gen_tile",
            ()-> TileEntityType.Builder.create(TileWaterGen::new, blocksInit.WATER_GEN_BLOCK.get()).build(null));
}
