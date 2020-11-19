package crimsonfluff.crimsongenerators.init;

import crimsonfluff.crimsongenerators.CrimsonGenerators;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class itemsInit {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, CrimsonGenerators.MOD_ID);

    // Block Items
    public static final RegistryObject<Item> COAL_GEN_BLOCK = ITEMS.register("coal_gen_block",
        () -> new BlockItem(blocksInit.COAL_GEN_BLOCK.get(), new Item.Properties().group(CrimsonGenerators.TAB)));

    public static final RegistryObject<Item> LAVA_GEN_BLOCK = ITEMS.register("lava_gen_block",
        () -> new BlockItem(blocksInit.LAVA_GEN_BLOCK.get(), new Item.Properties().group(CrimsonGenerators.TAB)));

    public static final RegistryObject<Item> WATER_GEN_BLOCK = ITEMS.register("water_gen_block",
        () -> new BlockItem(blocksInit.WATER_GEN_BLOCK.get(), new Item.Properties().group(CrimsonGenerators.TAB)));
}
