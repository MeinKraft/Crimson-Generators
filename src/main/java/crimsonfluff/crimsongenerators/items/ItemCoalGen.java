package crimsonfluff.crimsongenerators.items;

import crimsonfluff.crimsongenerators.CrimsonGenerators;
import crimsonfluff.crimsongenerators.util.KeyboardHelper;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.List;

public class ItemCoalGen extends Item {
    public ItemCoalGen() {
        super(new Item.Properties().group(CrimsonGenerators.TAB));
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void addInformation(ItemStack stack, World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        tooltip.add((new TranslationTextComponent("item."+ CrimsonGenerators.MOD_ID+".ruby.tip1").mergeStyle(TextFormatting.GREEN)));
        tooltip.add((new TranslationTextComponent("item."+ CrimsonGenerators.MOD_ID+".ruby.tip2").mergeStyle(TextFormatting.YELLOW)));

        if (KeyboardHelper.isHoldingShift())
            tooltip.add(new TranslationTextComponent("item."+ CrimsonGenerators.MOD_ID+".ruby.tip3"));

        else
            tooltip.add((new TranslationTextComponent("tip."+ CrimsonGenerators.MOD_ID+".hold_shift")));

        super.addInformation(stack, worldIn, tooltip, flagIn);
    }
}
