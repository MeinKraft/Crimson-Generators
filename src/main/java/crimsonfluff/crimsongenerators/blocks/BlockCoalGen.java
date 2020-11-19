package crimsonfluff.crimsongenerators.blocks;

import crimsonfluff.crimsongenerators.CrimsonGenerators;
import crimsonfluff.crimsongenerators.tiles.TileCoalGen;
import crimsonfluff.crimsongenerators.util.KeyboardHelper;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.items.CapabilityItemHandler;

import javax.annotation.Nullable;
import java.util.List;

public class BlockCoalGen extends Block {
    public BlockCoalGen() {
        super(Properties.create(Material.ROCK)
                .hardnessAndResistance(5.0f, 6.0f)
                .sound(SoundType.STONE)
                .harvestLevel(0)
                .harvestTool(ToolType.PICKAXE)
                .setRequiresTool());
    }

    @Override
    public boolean hasTileEntity(BlockState state) { return true; }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) { return new TileCoalGen(); }

    @Override
    public void onReplaced(BlockState oldState, World worldIn, BlockPos pos, BlockState newState, boolean isMoving) {
        if (oldState.getBlock() != newState.getBlock()) {
            // if Multiple Slots then...
            /*             worldIn.getTileEntity(pos).getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent(h -> {
                for (int i = 0; i < h.getSlots(); i++) {
                    spawnAsEntity(worldIn, pos, h.getStackInSlot(i));
                }
            });*/

            worldIn.getTileEntity(pos).getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent(h -> {
                    spawnAsEntity(worldIn, pos, h.getStackInSlot(0)); });
        }
        super.onReplaced(oldState, worldIn, pos, newState, isMoving);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void addInformation(ItemStack stack, @Nullable IBlockReader worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        tooltip.add((new TranslationTextComponent("tip."+ CrimsonGenerators.MOD_ID+".coal_gen_block").mergeStyle(TextFormatting.GREEN)));

        if (KeyboardHelper.isHoldingShift())
            tooltip.add((new TranslationTextComponent("No Upgrades Installed").mergeStyle(TextFormatting.GREEN)));
        else
            tooltip.add((new TranslationTextComponent("tip."+ CrimsonGenerators.MOD_ID+".hold_shift")));
    }

    @Override
    public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult rayTraceResult) {
        ItemStack heldItem = player.getHeldItem(hand);
        TileEntity tileEntity = worldIn.getTileEntity(pos);

        if (tileEntity != null) {
            if (heldItem.isEmpty() || heldItem.getItem() == Items.COAL_ORE) {
                if (KeyboardHelper.isHoldingShift()) {
                    worldIn.getTileEntity(pos).getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent(h -> {
                        player.addItemStackToInventory(h.getStackInSlot(0));
                    });
                } else {
                    worldIn.getTileEntity(pos).getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent(h -> {
                        player.addItemStackToInventory(new ItemStack(h.getStackInSlot(0).getItem()));
                    });
                }
            }
        }

        return ActionResultType.SUCCESS;
    }
}
