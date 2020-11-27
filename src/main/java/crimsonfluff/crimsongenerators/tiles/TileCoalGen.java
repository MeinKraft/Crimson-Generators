package crimsonfluff.crimsongenerators.tiles;

import crimsonfluff.crimsongenerators.CrimsonGenerators;
import crimsonfluff.crimsongenerators.init.tilesInit;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.Constants;
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
    private ITextComponent customName;

    public TileCoalGen() { super(tilesInit.COAL_GEN_TILE.get()); }

    @Override
    public void tick() {
        if (!world.isRemote) {

            ticks++;
            if (ticks == 20) {
                ticks = 0;

                BlockState state = this.getBlockState();
                int AMP = 0;

            // Needs 'break;' else it kept defaulting to last statement (AMP 64)
            // weird
                switch(state.get(CrimsonGenerators.TIER)) {
                    case 0:
                        AMP = 1;
                        break;
                    case 1:
                        AMP = 10;
                        break;
                    case 2:
                        AMP = 32;
                        break;
                    case 3:
                        AMP = 64;
                }

                ItemStack stack = new ItemStack(Blocks.COAL_ORE, AMP);
                ItemHandlerHelper.insertItemStacked(getItemHandler(), stack, false);

                TileEntity tile = world.getTileEntity(pos.offset(Direction.UP));
                if (tile != null && tile.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, Direction.DOWN).isPresent()) {
                    IItemHandler ihandler = tile.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, Direction.DOWN).orElse(null);

                    // handler is my tile entity
                    if (handler.getStackInSlot(0) != ItemStack.EMPTY) {
                        ItemStack stack2 = handler.getStackInSlot(0).copy();
                        stack2.setCount(AMP);
                        ItemStack stack1 = ItemHandlerHelper.insertItem(ihandler, stack2, true);

                        if (stack1 == ItemStack.EMPTY || stack1.getCount() == 0) {
                            ItemHandlerHelper.insertItem(ihandler, handler.extractItem(0, AMP, false), false);
                            markDirty();
                        }
                    }
                }
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
        tag.put("inv", getItemHandler().serializeNBT());
        if (this.customName!=null) {
            tag.putString("CustomName", ITextComponent.Serializer.toJson(this.customName));
        }
        return super.write(tag);
    }

    @Override
    public void read(BlockState state, CompoundNBT tag) {
        super.read(state, tag);
        if (tag.contains("inv")) {
            getItemHandler().deserializeNBT((CompoundNBT) tag.get("inv"));
        }
        if (tag.contains("CustomName", Constants.NBT.TAG_STRING)) {
            this.customName = ITextComponent.Serializer.getComponentFromJson(tag.getString("CustomName"));
        }
    }

    public void setCustomName(ITextComponent name) {
        this.customName = name;
    }

    public ITextComponent getName() {
        return this.customName !=null ? this.customName : this.getDefaultName();
    }

    private ITextComponent getDefaultName() {
        return new TranslationTextComponent("item."+CrimsonGenerators.MOD_ID+".coal_gen_block");
    }

/*    @Override
    public ITextComponent getDisplayName() {
        return this.getName();
    }*/

    @Nullable
    public ITextComponent getCustomName() {
        return this.customName;
    }
}
