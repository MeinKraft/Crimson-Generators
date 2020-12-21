package crimsonfluff.crimsongenerators.tiles;

import crimsonfluff.crimsongenerators.CrimsonGenerators;
import crimsonfluff.crimsongenerators.blocks.BlockGenCobble;
import crimsonfluff.crimsongenerators.containers.GeneratorContainer;
import crimsonfluff.crimsongenerators.init.tilesInit;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.*;
import net.minecraft.util.IIntArray;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

public class GeneratorTileEntity extends LockableLootTileEntity implements ITickableTileEntity {
    private NonNullList<ItemStack> chestContents;
    protected int numPlayersUsing;

    private String customName="";

    private int ticks = 0;
    private int isBurning;              // changed to Int because of TrackIntArray thing
    private int itemsOutputted;         // number of items outputted so far
    private int itemsToOutput;          // total items to make for 1 fuel consumed
    public Item ItemToMake;

    private final IIntArray furnaceData = new IIntArray() {
        @Override
        public int get(int index) {
            switch (index) {
                case 0:
                    return GeneratorTileEntity.this.isBurning;
                case 1:
                    return GeneratorTileEntity.this.itemsOutputted;
                case 2:
                    return GeneratorTileEntity.this.itemsToOutput;
                default:
                    return 0;
            }
        }

        @Override
        public void set(int index, int value) {
            switch (index) {
                case 0:
                    GeneratorTileEntity.this.isBurning = value;
                    break;
                case 1:
                    GeneratorTileEntity.this.itemsOutputted = value;
                    break;
                case 2:
                    GeneratorTileEntity.this.itemsToOutput = value;
                    break;
            }
        }

        @Override
        public int size() {
            return 3;
        }
    };

    public static GeneratorTileEntity createCobble() {
        GeneratorTileEntity bb = new GeneratorTileEntity(tilesInit.GEN_BLOCK_TILE.get());
        bb.customName = "cobblestone";
        bb.ItemToMake = Items.COBBLESTONE;
        return bb;
    }

    public static GeneratorTileEntity createSand() {
        GeneratorTileEntity bb = new GeneratorTileEntity(tilesInit.SAND_BLOCK_TILE.get());
        bb.customName = "sand";
        bb.ItemToMake = Items.SAND;
        return bb;
    }

    public static GeneratorTileEntity createClay() {
        GeneratorTileEntity bb = new GeneratorTileEntity(tilesInit.CLAY_BLOCK_TILE.get());
        bb.customName = "clay";
        bb.ItemToMake = Items.CLAY_BALL;
        return bb;
    }

    public static GeneratorTileEntity createDirt() {
        GeneratorTileEntity bb = new GeneratorTileEntity(tilesInit.DIRT_BLOCK_TILE.get());
        bb.customName = "dirt";
        bb.ItemToMake = Items.DIRT;
        return bb;
    }
    public static GeneratorTileEntity createNether() {
        GeneratorTileEntity bb = new GeneratorTileEntity(tilesInit.NETHER_BLOCK_TILE.get());
        bb.customName = "nether";
        bb.ItemToMake = Items.NETHERRACK;
        return bb;
    }

    public static GeneratorTileEntity createEnd() {
        GeneratorTileEntity bb = new GeneratorTileEntity(tilesInit.END_BLOCK_TILE.get());
        bb.customName = "end";
        bb.ItemToMake = Items.END_STONE;
        return bb;
    }

    public static GeneratorTileEntity createObsidian() {
        GeneratorTileEntity bb = new GeneratorTileEntity(tilesInit.OBSIDIAN_BLOCK_TILE.get());
        bb.customName = "obsidian";
        bb.ItemToMake = Items.OBSIDIAN;
        return bb;
    }

    public GeneratorTileEntity(TileEntityType typeIn) {
        super(typeIn);
        this.chestContents = NonNullList.withSize(2, ItemStack.EMPTY);
    }

    @Override
    public int getSizeInventory() {
        return this.getItems().size();
    }

    @Override
    public boolean isEmpty() {
        for (ItemStack itemstack : this.chestContents) if (!itemstack.isEmpty()) return false;

        return true;
    }

    @Override
    protected ITextComponent getDefaultName() {
        return new TranslationTextComponent("container." + CrimsonGenerators.MOD_ID + "." + customName);
    }

    @Override
    public void read(BlockState state, CompoundNBT compound) {
        super.read(state, compound);

        this.chestContents = NonNullList.withSize(this.getSizeInventory(), ItemStack.EMPTY);
        if (!this.checkLootAndRead(compound)) ItemStackHelper.loadAllItems(compound, this.chestContents);

        this.isBurning = compound.getInt("smelting");
        this.itemsOutputted = compound.getInt("outputted");
        this.itemsToOutput = compound.getInt("output");

        if (compound.contains("Name")) this.customName = compound.getString("Name");
    }

    @Override
    public CompoundNBT write(CompoundNBT compound) {
        super.write(compound);

        if (!this.checkLootAndWrite(compound)) ItemStackHelper.saveAllItems(compound, this.chestContents);

        compound.putInt("smelting", this.isBurning);
        compound.putInt("outputted", this.itemsOutputted);
        compound.putInt("output", this.itemsToOutput);

        return compound;
    }

    public static int getNumberOfPlayersUsing(World worldIn, LockableTileEntity lockableTileEntity, int ticksSinceSync, int x, int y, int z, int numPlayersUsing) {
        if (!worldIn.isRemote && numPlayersUsing != 0 && (ticksSinceSync + x + y + z) % 200 == 0) {
            numPlayersUsing = getNumberOfPlayersUsing(worldIn, lockableTileEntity, x, y, z);
        }

        return numPlayersUsing;
    }

    public static int getNumberOfPlayersUsing(World world, LockableTileEntity lockableTileEntity, int x, int y, int z) {
        int i = 0;

        for (PlayerEntity playerentity : world.getEntitiesWithinAABB(PlayerEntity.class, new AxisAlignedBB((double) ((float) x - 5.0F), (double) ((float) y - 5.0F), (double) ((float) z - 5.0F), (double) ((float) (x + 1) + 5.0F), (double) ((float) (y + 1) + 5.0F), (double) ((float) (z + 1) + 5.0F)))) {
            if (playerentity.openContainer instanceof GeneratorContainer) {
                ++i;
            }
        }

        return i;
    }

    @Override
    public boolean receiveClientEvent(int id, int type) {
        if (id == 1) {
            this.numPlayersUsing = type;
            return true;
        }
        else {
            return super.receiveClientEvent(id, type);
        }
    }

    @Override
    public void openInventory(PlayerEntity player) {
        if (!player.isSpectator()) {
            if (this.numPlayersUsing < 0) this.numPlayersUsing = 0;

            ++this.numPlayersUsing;
            this.onOpenOrClose();
        }
    }

    @Override
    public void closeInventory(PlayerEntity player) {
        if (!player.isSpectator()) {
            --this.numPlayersUsing;
            this.onOpenOrClose();
        }
    }

    protected void onOpenOrClose() {
        Block block = this.getBlockState().getBlock();

        if (block instanceof BlockGenCobble) {
            this.world.addBlockEvent(this.pos, block, 1, this.numPlayersUsing);
            this.world.notifyNeighborsOfStateChange(this.pos, block);
        }
    }

    @Override
    public NonNullList<ItemStack> getItems() {
        return this.chestContents;
    }

    @Override
    public void setItems(NonNullList<ItemStack> itemsIn) {
        this.chestContents = NonNullList.<ItemStack>withSize(2, ItemStack.EMPTY);

        for (int i = 0; i < itemsIn.size(); i++) {
            if (i < this.chestContents.size()) {
                this.getItems().set(i, itemsIn.get(i));
            }
        }
    }

    public static int getPlayersUsing(IBlockReader reader, BlockPos posIn) {
        BlockState blockstate = reader.getBlockState(posIn);
        if (blockstate.hasTileEntity()) {
            TileEntity tileentity = reader.getTileEntity(posIn);
            if (tileentity instanceof GeneratorTileEntity) return ((GeneratorTileEntity) tileentity).numPlayersUsing;
        }

        return 0;
    }

    @Override
    protected Container createMenu(int windowId, PlayerInventory playerInventory) {
        return new GeneratorContainer(windowId, playerInventory, this, this.furnaceData);
    }

    @Override
    public void tick() {
        if (this.world.isRemote()) return;

        ticks++;
        if (ticks == 20) {
            ticks = 0;

//            BlockState state = this.getBlockState();
//            BlockPos pos = this.pos;
//            World world = this.getWorld();
            //CrimsonChest.LOGGER.info("COBBLE: Block is ticking");

            if (isBurning == 0) {
                if (!this.getStackInSlot(0).isEmpty() || (this.getStackInSlot(0).getCount() != 0)) {
                    isBurning = 1;
                    itemsToOutput = net.minecraftforge.common.ForgeHooks.getBurnTime(this.getStackInSlot(0)) / 200;

                    this.getStackInSlot(0).shrink(1);
                    this.world.setBlockState(this.pos, this.world.getBlockState(this.pos).with(CrimsonGenerators.GENERATOR_PROPERTY_LIT, true), 3);
                }
            }

            if (isBurning !=0 ) {
                if ((itemsOutputted) == itemsToOutput) {
                    if (this.getStackInSlot(0).isEmpty() || (this.getStackInSlot(0).getCount() == 0)) {        // out of fuel so reset everything
                        isBurning = 0;
                        this.world.setBlockState(this.pos, this.world.getBlockState(this.pos).with(CrimsonGenerators.GENERATOR_PROPERTY_LIT, false), 3);

                        itemsToOutput = 0;
                        itemsOutputted = 0;

                        return;

                    } else {
                        itemsToOutput = net.minecraftforge.common.ForgeHooks.getBurnTime(this.getStackInSlot(0)) / 200;
                        itemsOutputted = 0;

                        this.getStackInSlot(0).shrink(1);
                    }
                }

                if (!this.getStackInSlot(1).isEmpty())
                    this.getStackInSlot(1).grow(1);
                else
                    this.setInventorySlotContents(1, new ItemStack(ItemToMake));

                this.markDirty();
                itemsOutputted++;
            }
        }
    }

    // called from an inventory trying to insert STACK into GeneratorInventory
    // like Hopper items into the GeneratorBlock
    @Override
    public boolean isItemValidForSlot(int index, ItemStack stack) {
        // if index=0 then input slot, only allow fuels
        if (index == 0) {
            return net.minecraftforge.common.ForgeHooks.getBurnTime(stack) > 0;
            //return AbstractFurnaceTileEntity.isFuel(stack);
        }

        // if index=1 then Output slot, return false to NOT allow anything to be inserted
        return false;
    }

    // Don't alloy extraction from Slot(0) - Fuel Input
    // prevents Hoppers pulling fuel out - NOTE: May cause problems with Mods wanting to pull fuel out
    @Override
    public ItemStack decrStackSize(int index, int count) {
        if (index==0) return ItemStack.EMPTY;

        this.fillWithLoot((PlayerEntity)null);
        ItemStack itemstack = ItemStackHelper.getAndSplit(this.getItems(), index, count);
        if (!itemstack.isEmpty()) this.markDirty();

        return itemstack;
    }
}

