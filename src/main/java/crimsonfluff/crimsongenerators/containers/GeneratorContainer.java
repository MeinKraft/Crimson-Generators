package crimsonfluff.crimsongenerators.containers;

import crimsonfluff.crimsongenerators.SlotFuelInput;
import crimsonfluff.crimsongenerators.SlotGeneratorOutput;
import crimsonfluff.crimsongenerators.init.containersInit;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.AbstractFurnaceTileEntity;
import net.minecraft.util.IIntArray;
import net.minecraft.util.IntArray;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class GeneratorContainer extends Container {
    public GeneratorContainer (int windowId, PlayerInventory playerInventory) {
       this(containersInit.GENERATOR.get(), windowId, playerInventory, new Inventory(2), new IntArray(4));
    }

    public GeneratorContainer (int windowId, PlayerInventory playerInventory, IInventory inventory, IIntArray p_i50104_6_) {
        this(containersInit.GENERATOR.get(), windowId, playerInventory, inventory, p_i50104_6_);
    }

    private final IInventory inventory;
    private final IIntArray array;

    protected GeneratorContainer(ContainerType<?> containerTypeIn, int windowId, PlayerInventory playerInventory, IInventory inventory, IIntArray array) {
        super(containerTypeIn, windowId);
        assertInventorySize(inventory, 2);

        assertIntArraySize(array, 4);
        this.array = array;

        this.inventory = inventory;
        inventory.openInventory(playerInventory.player);


        // GeneratorBlock Inventory
        this.addSlot(new SlotFuelInput(inventory, 0, 56, 35));
        this.addSlot(new SlotGeneratorOutput(inventory, 1, 117, 35));


        // Player Inventory
        for (int chestRow = 0; chestRow < 3; chestRow++) {
            for (int chestCol = 0; chestCol < 9; chestCol++) {
                this.addSlot(new Slot(playerInventory, 9 + (chestRow * 9) + chestCol, 8 + chestCol * 18, 84 + chestRow * 18));
            }
        }

        // Hot Bar
        for (int chestCol = 0; chestCol < 9; chestCol++) {
            this.addSlot(new Slot(playerInventory, chestCol, 8 + chestCol * 18, 166 - 24));
        }

        this.trackIntArray(array);
    }

    @Override
    public boolean canInteractWith(PlayerEntity playerIn) {
        return this.inventory.isUsableByPlayer(playerIn);
    }

    @Override
    public ItemStack transferStackInSlot(PlayerEntity playerIn, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.inventorySlots.get(index);

        //CrimsonGenerators.LOGGER.info("SLOT CHANGE: " + index + " SLOT: " + slot);

        if (slot != null && slot.getHasStack()) {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();

            if (index < 2) {
                if (!this.mergeItemStack(itemstack1, 2, this.inventorySlots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            }
            else if (!this.mergeItemStack(itemstack1, 0, 2, false)) {
                return ItemStack.EMPTY;
            }

            // Shift clicking FROM GeneratorInventory TO PlayerInventory
            // if Click FuelSlot (0) or Click OutputSlot (1) then Merge with PlayerInventory
            if (index <= 1) {
                if (!this.mergeItemStack(itemstack1, 2, this.inventorySlots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else {
                // if its FROM PlayerInventory
                // if its a fuel then try to merge with FuelSlot (0)
                if (AbstractFurnaceTileEntity.isFuel(itemstack1)) {
                    if (!this.mergeItemStack(itemstack1, 0, 1, false)) {
                        return ItemStack.EMPTY;
                    }
                }

                // if FROM PlayerInventory then merge with HOTBAR
                if (index >= 2 && index < 30) {
                    if (!this.mergeItemStack(itemstack1, 30, 38, false)) {
                        return ItemStack.EMPTY;
                    }
                }

                // if FROM HOTBAR then merge with PlayerInventory
                if (index >= 30 && index < 38 && !this.mergeItemStack(itemstack1, 2, 30, false)) {
                    return ItemStack.EMPTY;
                }
            }
        }

        return itemstack;
    }

    @Override
    public void onContainerClosed(PlayerEntity playerIn) {
        super.onContainerClosed(playerIn);
        this.inventory.closeInventory(playerIn);
    }


// TrackIntArray Stuff
// Track certain variables so we can use them to update the GUI in GeneratorChestScreen
    @OnlyIn(Dist.CLIENT)
    public boolean isBurning() {
        return this.array.get(0)>0;
    }

    @OnlyIn(Dist.CLIENT)
    public int itemsOutputted() { return this.array.get(1); }

    @OnlyIn(Dist.CLIENT)
    public int itemsToOutput() {
        return this.array.get(2) ;
    }

    @OnlyIn(Dist.CLIENT)
    public int isSoulFire() { return this.array.get(3) ; }
}