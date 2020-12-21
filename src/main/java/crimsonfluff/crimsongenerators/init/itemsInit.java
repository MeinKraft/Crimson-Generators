package crimsonfluff.crimsongenerators.init;

import crimsonfluff.crimsongenerators.CrimsonGenerators;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class itemsInit {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, CrimsonGenerators.MOD_ID);

    public static final RegistryObject<Item> COBBLE_GEN_BLOCK_ITEM = ITEMS.register("cobble_gen",
            () -> new BlockItem(blocksInit.COBBLE_GEN_BLOCK.get(), new Item.Properties().group(ItemGroup.MISC)));

    public static final RegistryObject<Item> SAND_GEN_BLOCK_ITEM = ITEMS.register("sand_gen",
            () -> new BlockItem(blocksInit.SAND_GEN_BLOCK.get(), new Item.Properties().group(ItemGroup.MISC)));

    public static final RegistryObject<Item> CLAY_GEN_BLOCK_ITEM = ITEMS.register("clay_gen",
            () -> new BlockItem(blocksInit.CLAY_GEN_BLOCK.get(), new Item.Properties().group(ItemGroup.MISC)));

    public static final RegistryObject<Item> DIRT_GEN_BLOCK_ITEM = ITEMS.register("dirt_gen",
            () -> new BlockItem(blocksInit.DIRT_GEN_BLOCK.get(), new Item.Properties().group(ItemGroup.MISC)));

    public static final RegistryObject<Item> END_GEN_BLOCK_ITEM = ITEMS.register("end_gen",
            () -> new BlockItem(blocksInit.END_GEN_BLOCK.get(), new Item.Properties().group(ItemGroup.MISC)));

    public static final RegistryObject<Item> NETHER_GEN_BLOCK_ITEM = ITEMS.register("nether_gen",
            () -> new BlockItem(blocksInit.NETHER_GEN_BLOCK.get(), new Item.Properties().group(ItemGroup.MISC)));

    public static final RegistryObject<Item> OBSIDIAN_GEN_BLOCK_ITEM = ITEMS.register("obsidian_gen",
            () -> new BlockItem(blocksInit.OBSIDIAN_GEN_BLOCK.get(), new Item.Properties().group(ItemGroup.MISC)));
}
