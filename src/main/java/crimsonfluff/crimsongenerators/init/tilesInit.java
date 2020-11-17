package crimsonfluff.crimsongenerators.init;

import crimsonfluff.crimsongenerators.CrimsonGenerators;

import crimsonfluff.crimsongenerators.tiles.TileCoalGen;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class tilesInit {
    public static final DeferredRegister<TileEntityType<?>> TILE_ENTITY_TYPES =
            DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, CrimsonGenerators.MOD_ID);

    public static final RegistryObject<TileEntityType<TileCoalGen>> COAL_GEN_TILE = TILE_ENTITY_TYPES.register("coal_gen_tile",
                    ()-> TileEntityType.Builder.create(TileCoalGen::new, blocksInit.COAL_GEN_BLOCK.get()).build(null));
}
