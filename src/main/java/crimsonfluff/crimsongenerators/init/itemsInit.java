package crimsonfluff.crimsongenerators.init;

import crimsonfluff.crimsongenerators.CrimsonGenerators;
import crimsonfluff.crimsongenerators.items.ItemCoalGen;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.List;

public class itemsInit {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, CrimsonGenerators.MOD_ID);

    // Block Items
    public static final RegistryObject<Item> COAL_GEN_BLOCK = ITEMS.register("coal_gen_block",
            () -> new BlockItem(blocksInit.COAL_GEN_BLOCK.get(), new Item.Properties().group(CrimsonGenerators.TAB)) {
                @OnlyIn(Dist.CLIENT)
                @Override
                public void addInformation(ItemStack stack, World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
                    tooltip.add((new TranslationTextComponent("tip."+CrimsonGenerators.MOD_ID+".item")));

                    super.addInformation(stack, worldIn, tooltip, flagIn);
                }
            });
}
