package crimsonfluff.crimsongenerators.blocks;

import crimsonfluff.crimsongenerators.CrimsonGenerators;
import crimsonfluff.crimsongenerators.init.itemsInit;
import crimsonfluff.crimsongenerators.tiles.TileWaterGen;
import crimsonfluff.crimsongenerators.util.KeyboardHelper;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.potion.PotionUtils;
import net.minecraft.potion.Potions;
import net.minecraft.state.StateContainer;
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
        this.setDefaultState(this.stateContainer.getBaseState().with(CrimsonGenerators.TIER, 0));
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        super.fillStateContainer(builder);
        builder.add(CrimsonGenerators.TIER);
    }

    @Override
    public boolean hasTileEntity(BlockState state) { return true; }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) { return new TileWaterGen(); }

    @Override
    public void onReplaced(BlockState oldState, World worldIn, BlockPos pos, BlockState newState, boolean isMoving) {
        if (oldState.getBlock() != newState.getBlock()) worldIn.removeTileEntity(pos);
        super.onReplaced(oldState, worldIn, pos, newState, isMoving);
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

    @Override
    public ActionResultType onBlockActivated(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult rayTraceResult) {
        if (!world.isRemote) {
            ItemStack heldItem = player.getHeldItem(hand);
            TileEntity tileEntity = world.getTileEntity(pos);

            if (tileEntity != null) {
                LazyOptional<IFluidHandler> fluidHandlerCap = tileEntity.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY);

                if (fluidHandlerCap.isPresent()) {
                    IFluidHandler fluidHandler = fluidHandlerCap.orElseThrow(IllegalStateException::new);

                    if (heldItem.getItem() == Items.GLASS_BOTTLE) {
                        if (fluidHandler.drain(333, IFluidHandler.FluidAction.SIMULATE).getAmount() == 333) {
                            fluidHandler.drain(333, IFluidHandler.FluidAction.EXECUTE);

                            heldItem.shrink(1);
                            ItemStack itemPotion = PotionUtils.addPotionToItemStack(new ItemStack(Items.POTION), Potions.WATER);

                            if (!player.addItemStackToInventory(itemPotion)) {
                                spawnAsEntity(world, player.getPosition(), itemPotion);
                            }

                            return ActionResultType.SUCCESS;
                        }
                    } else if (heldItem.getItem() == Items.POTION && heldItem.getTag() != null) {
                        if (heldItem.getTag().getString("Potion").equals("minecraft:water")) {
                            if (fluidHandler.fill(new FluidStack(Fluids.WATER, 333), IFluidHandler.FluidAction.SIMULATE) == 333) {
                                fluidHandler.fill(new FluidStack(Fluids.WATER, 333), IFluidHandler.FluidAction.EXECUTE);

                                heldItem.shrink(1);
                                ItemStack itemBottle = new ItemStack(Items.GLASS_BOTTLE);

                                if (!player.addItemStackToInventory(itemBottle)) {
                                    spawnAsEntity(world, player.getPosition(), itemBottle);
                                }

                                return ActionResultType.SUCCESS;
                            }
                        }
                    } else {
                        if (!FluidUtil.interactWithFluidHandler(player, hand, fluidHandler)) {
                            //LOGGER.info("Interact.FAILED");
                            return ActionResultType.FAIL;
                        } else {
                            //LOGGER.info("Interact.SUCCESS");
                            return ActionResultType.SUCCESS;
                        }
                    }
                }

                //LOGGER.info("FAILED: " + heldItem.getItem().getTranslationKey());
            }

            //LOGGER.info("FAILED: Ever here");
        }

        return ActionResultType.SUCCESS;
    }
}
