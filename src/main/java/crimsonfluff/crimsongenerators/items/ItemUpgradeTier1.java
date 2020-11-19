package crimsonfluff.crimsongenerators.items;

import crimsonfluff.crimsongenerators.CrimsonGenerators;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.world.World;

public class ItemUpgradeTier1 extends Item {
    public ItemUpgradeTier1() {
        super(new Properties().group(CrimsonGenerators.TAB));
    }

/*    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
        ItemStack stack = playerIn.getHeldItem(handIn);

        playerIn.world.playSound(null, playerIn.getPosX(), playerIn.getPosY(), playerIn.getPosZ(), SoundEvents.BLOCK_ANVIL_USE, SoundCategory.PLAYERS, 1f, 1f);

        return new ActionResult<>(ActionResultType.SUCCESS, stack);
    }*/
}
