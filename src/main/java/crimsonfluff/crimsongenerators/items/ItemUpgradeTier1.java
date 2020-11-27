package crimsonfluff.crimsongenerators.items;

import crimsonfluff.crimsongenerators.CrimsonGenerators;
import crimsonfluff.crimsongenerators.blocks.BlockCoalGen;
import crimsonfluff.crimsongenerators.blocks.BlockLavaGen;
import crimsonfluff.crimsongenerators.blocks.BlockWaterGen;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;

public class ItemUpgradeTier1 extends Item {
    public ItemUpgradeTier1() {
        super(new Properties().group(CrimsonGenerators.TAB));
    }

    public ActionResultType onItemUse(ItemUseContext context) {
        PlayerEntity player = context.getPlayer();
        BlockState state = player.getEntityWorld().getBlockState(context.getPos());

        int pass=0;
        if (state.getBlock() instanceof BlockCoalGen) pass=1;
        if (state.getBlock() instanceof BlockLavaGen) pass=1;
        if (state.getBlock() instanceof BlockWaterGen) pass=1;

        if (pass==0) return ActionResultType.PASS;

        if (state.get(CrimsonGenerators.TIER) == 0) {
            player.world.playSound(null, player.getPosition(), SoundEvents.BLOCK_ANVIL_USE, SoundCategory.PLAYERS, 1f, 1f);
            player.world.setBlockState(context.getPos(), state.with(CrimsonGenerators.TIER, state.get(CrimsonGenerators.TIER) + 1));
        }

        return ActionResultType.PASS;
    }
}
