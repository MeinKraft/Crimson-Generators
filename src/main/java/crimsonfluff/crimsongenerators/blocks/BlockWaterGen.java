package crimsonfluff.crimsongenerators.blocks;

import crimsonfluff.crimsongenerators.CrimsonGenerators;
import crimsonfluff.crimsongenerators.tiles.TileWaterGen;
import crimsonfluff.crimsongenerators.util.KeyboardHelper;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.potion.PotionUtils;
import net.minecraft.potion.Potions;
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
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;

import javax.annotation.Nullable;
import java.util.List;

public class BlockWaterGen extends Block {
    public BlockWaterGen() {
        super(Properties.create(Material.ROCK)
                .hardnessAndResistance(5.0f, 6.0f)
                .sound(SoundType.NETHER_BRICK)
                .harvestLevel(1)
                .harvestTool(ToolType.PICKAXE)
                .setRequiresTool());
    }

    @Override
    public boolean hasTileEntity(BlockState state) { return true; }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) { return new TileWaterGen(); }

    @Override
    public ActionResultType onBlockActivated(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult rayTraceResult) {
        ItemStack heldItem = player.getHeldItem(hand);
        TileEntity tileEntity = world.getTileEntity(pos);

        if (tileEntity != null) {
            LazyOptional<IFluidHandler> fluidHandlerCap = tileEntity.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY);
            if (!fluidHandlerCap.isPresent()) {
                //spawnParticles(world, pos, state);
            }
            else
            {
                IFluidHandler fluidHandler = fluidHandlerCap.orElseThrow(IllegalStateException::new);

                if (!FluidUtil.interactWithFluidHandler(player, hand, fluidHandler)) {
                    // Special case for bottles, they can hold 1/3 of a bucket

                    if (heldItem.getItem() == Items.BUCKET) {
                        FluidStack simulated = fluidHandler.drain(1000, IFluidHandler.FluidAction.SIMULATE);

                        if (simulated.getAmount() == 1000) {
                            fluidHandler.drain(1000, IFluidHandler.FluidAction.EXECUTE);

                            if (player.addItemStackToInventory(new ItemStack(Items.WATER_BUCKET))) {
                                heldItem.shrink(1);
                            }
                        }
                    } else {
                        if (heldItem.getItem() == Items.GLASS_BOTTLE) {
                            FluidStack simulated = fluidHandler.drain(333, IFluidHandler.FluidAction.SIMULATE);

                            if (simulated.getAmount() == 333) {
                                fluidHandler.drain(333, IFluidHandler.FluidAction.EXECUTE);

                                if (player.addItemStackToInventory(PotionUtils.addPotionToItemStack(new ItemStack(Items.POTION), Potions.WATER))) {
                                    heldItem.shrink(1);
                                }
                            }
                        }
                    }
                }
            }
        }

        return ActionResultType.SUCCESS;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void addInformation(ItemStack stack, @Nullable IBlockReader worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        tooltip.add((new TranslationTextComponent("tip."+ CrimsonGenerators.MOD_ID+".water_gen_block").mergeStyle(TextFormatting.GREEN)));

        if (KeyboardHelper.isHoldingShift())
            tooltip.add((new TranslationTextComponent("No Upgrades Installed").mergeStyle(TextFormatting.GREEN)));
        else
            tooltip.add((new TranslationTextComponent("tip."+ CrimsonGenerators.MOD_ID+".hold_shift")));
    }
}
