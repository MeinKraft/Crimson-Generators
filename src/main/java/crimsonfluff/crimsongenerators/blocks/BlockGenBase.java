package crimsonfluff.crimsongenerators.blocks;

import crimsonfluff.crimsongenerators.CrimsonGenerators;
import crimsonfluff.crimsongenerators.tiles.GeneratorTileEntity;
import crimsonfluff.crimsongenerators.util.KeyboardHelper;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.AbstractFurnaceTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.DimensionType;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.ToolType;
import org.jline.terminal.Size;

import javax.annotation.Nullable;
import javax.swing.*;
import java.util.Random;

public class BlockGenBase extends Block {
    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;

    public BlockGenBase() {
        super(Properties.create(Material.ROCK)
            .hardnessAndResistance(5.0f, 6.0f)
            .sound(SoundType.STONE)
            .harvestLevel(1)
            .harvestTool(ToolType.PICKAXE)
            .setRequiresTool());
        this.setDefaultState(this.stateContainer.getBaseState().with(FACING, Direction.NORTH).with(CrimsonGenerators.GENERATOR_PROPERTY_LIT, false).with(CrimsonGenerators.GENERATOR_PROPERTY_SOUL, false));
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Override
    public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        if (!worldIn.isRemote) {
            INamedContainerProvider inamedcontainerprovider = this.getContainer(state, worldIn, pos);
            if (inamedcontainerprovider != null) player.openContainer(inamedcontainerprovider);
        }
        return ActionResultType.SUCCESS;
    }

    @Override
    @Nullable
    public INamedContainerProvider getContainer(BlockState state, World world, BlockPos pos) {
        TileEntity tileentity = world.getTileEntity(pos);
        return tileentity instanceof INamedContainerProvider ? (INamedContainerProvider) tileentity : null;
    }

    @Override
    public boolean eventReceived(BlockState state, World worldIn, BlockPos pos, int id, int param) {
        super.eventReceived(state, worldIn, pos, id, param);
        TileEntity tileentity = worldIn.getTileEntity(pos);
        return tileentity == null ? false : tileentity.receiveClientEvent(id, param);
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        super.fillStateContainer(builder);
        builder.add(FACING, CrimsonGenerators.GENERATOR_PROPERTY_LIT, CrimsonGenerators.GENERATOR_PROPERTY_SOUL);
    }

    @Override
    public BlockState mirror(BlockState state, Mirror mirrorIn) {
        return state.rotate(mirrorIn.toRotation(state.get(FACING)));
    }

    @Override
    public BlockState rotate(BlockState state, Rotation rot) {
        return state.with(FACING, rot.rotate(state.get(FACING)));
    }

    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        return this.getDefaultState().with(FACING, context.getPlacementHorizontalFacing().getOpposite());
    }

    @Override
    public boolean hasComparatorInputOverride(BlockState state) { return true; }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void animateTick(BlockState stateIn, World worldIn, BlockPos pos, Random rand) {
        if (stateIn.get(CrimsonGenerators.GENERATOR_PROPERTY_LIT)) {
            double d0 = (double) pos.getX() + 0.5D;
            double d1 = pos.getY();
            double d2 = (double) pos.getZ() + 0.5D;

            if (rand.nextDouble() < 0.1D)
                worldIn.playSound(d0, d1, d2, SoundEvents.BLOCK_FURNACE_FIRE_CRACKLE, SoundCategory.BLOCKS, 1.0F, 1.0F, false);

            Direction direction = stateIn.get(FACING);
            Direction.Axis direction$axis = direction.getAxis();
            double d4 = rand.nextDouble() * 0.6D - 0.3D;
            double d5 = direction$axis == Direction.Axis.X ? (double) direction.getXOffset() * 0.52D : d4;
            double d6 = rand.nextDouble() * 6.0D / 16.0D;
            double d7 = direction$axis == Direction.Axis.Z ? (double) direction.getZOffset() * 0.52D : d4;

            worldIn.addParticle(ParticleTypes.SMOKE, d0 + d5, d1 + d6, d2 + d7, 0.0D, 0.0D, 0.0D);
            // SoulFire excl to Soul Sand Valley and naturally on Soul Soil
            //if (worldIn.getBiome(pos).getRegistryName().toString() == "soul_sand_valley")
            //if (worldIn.getDimensionKey() == World.THE_NETHER)
            if (worldIn.getBlockState(pos.offset(Direction.DOWN)).getBlock() == Blocks.SOUL_SOIL)
                worldIn.addParticle(ParticleTypes.SOUL_FIRE_FLAME, d0 + d5, d1 + d6, d2 + d7, 0.0D, 0.0D, 0.0D);
            else
                worldIn.addParticle(ParticleTypes.FLAME, d0 + d5, d1 + d6, d2 + d7, 0.0D, 0.0D, 0.0D);
        }
    }

    @Override
    public void onReplaced(BlockState state, World worldIn, BlockPos pos, BlockState newState, boolean isMoving) {
        GeneratorTileEntity tile = (GeneratorTileEntity) worldIn.getTileEntity(pos);
        if (tile != null && state.getBlock() != newState.getBlock()) {

            // if GeneratorBlock is broken then explode Fuel and Cobble all over the place
            if (tile.getStackInSlot(0).getCount() != 0) worldIn.addEntity(new ItemEntity(worldIn, pos.getX(), pos.getY(), pos.getZ(), tile.getStackInSlot(0)));
            if (tile.getStackInSlot(1).getCount() != 0) worldIn.addEntity(new ItemEntity(worldIn, pos.getX(), pos.getY(), pos.getZ(), tile.getStackInSlot(1)));

            worldIn.updateComparatorOutputLevel(pos, this);
            worldIn.removeTileEntity(pos);
        }
    }

    public void onBlockPlacedBy(World worldIn, BlockPos pos, BlockState state, LivingEntity placer, ItemStack stack) {
        if (stack.hasDisplayName()) {
            TileEntity tile = worldIn.getTileEntity(pos);
            if (tile instanceof GeneratorTileEntity) ((GeneratorTileEntity) tile).setCustomName(stack.getDisplayName());
        }

        if (worldIn.getBlockState(pos.offset(Direction.DOWN)).getBlock()==Blocks.SOUL_SOIL) {
            worldIn.setBlockState(pos, state.with(CrimsonGenerators.GENERATOR_PROPERTY_SOUL, true), 3);
        }
    }

    @Override
    public void onBlockClicked(BlockState state, World worldIn, BlockPos pos, PlayerEntity playerIn) {
        if (!worldIn.isRemote) {
            GeneratorTileEntity tile = (GeneratorTileEntity) worldIn.getTileEntity(pos);
            if (tile == null) return;
            if (tile.getStackInSlot(1).getCount() == 0) return;

            int SizeToTake;
            if (KeyboardHelper.isHoldingShift()) SizeToTake = Integer.min(64, tile.getStackInSlot(1).getCount());
            else SizeToTake = 1;

            ItemStack stack = new ItemStack (tile.getStackInSlot(1).getItem());
            stack.setCount(SizeToTake);

            // stack is automatically shrunk
            // if returns false then whole stack could not be moved. GetCount will be the remainder
            if (!playerIn.addItemStackToInventory(stack)) ForgeHooks.onPlayerTossEvent(playerIn, stack, false);

            if (!playerIn.isCreative()) tile.getStackInSlot(1).shrink(SizeToTake);
        }
    }
}
