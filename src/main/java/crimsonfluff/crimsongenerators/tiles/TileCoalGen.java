package crimsonfluff.crimsongenerators.tiles;

import crimsonfluff.crimsongenerators.init.tilesInit;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemHandlerHelper;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class TileCoalGen extends TileEntity implements ITickableTileEntity {
    private ItemStackHandler handler;
    private int ticks = 0;

    public TileCoalGen() { super(tilesInit.COAL_GEN_TILE.get()); }

    @Override
    public void tick() {
        ticks++;
        if (ticks == 20) {
            ticks = 0;

            ItemStack stack = new ItemStack(Blocks.COAL_ORE, 1);
            ItemHandlerHelper.insertItemStacked(getItemHandler(), stack, false);

            TileEntity tile = world.getTileEntity(pos.offset(Direction.UP));
            if (tile != null && tile.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, Direction.DOWN).isPresent()) {
                IItemHandler ihandler = tile.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, Direction.DOWN).orElse(null);

            // handler is my tile entity, and is never empty
                //if (handler.getStackInSlot(0) != ItemStack.EMPTY) {

                    ItemStack stack2 = handler.getStackInSlot(0).copy();
                    stack2.setCount(1);
                    ItemStack stack1 = ItemHandlerHelper.insertItem(ihandler, stack2, true);

                    if (stack1 == ItemStack.EMPTY || stack1.getCount() == 0) {
                        ItemHandlerHelper.insertItem(ihandler, handler.extractItem(0, 1, false), false);
                        markDirty();
                    }
                //}
            }
        }
    }

    private ItemStackHandler getItemHandler() {
        if (handler == null) { handler = new ItemStackHandler(1); }
        return handler;
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            // to disable DOWN direction
            //if (side != Direction.DOWN) return LazyOptional.of(() -> (T) getHandler());

            return LazyOptional.of(() -> (T) getItemHandler());
        }
        return super.getCapability(cap, side);
    }

    @Override
    public CompoundNBT write(CompoundNBT tag) {
        CompoundNBT compound = getItemHandler().serializeNBT();
        tag.put("inv", compound);
        return super.write(tag);
    }

    @Override
    public void read(BlockState state, CompoundNBT tag) {
        super.read(state, tag);
        if (tag.contains("inv")) {
            getItemHandler().deserializeNBT((CompoundNBT) tag.get("inv"));
        }
    }
}
