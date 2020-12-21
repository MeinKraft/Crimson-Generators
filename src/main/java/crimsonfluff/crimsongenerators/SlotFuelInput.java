package crimsonfluff.crimsongenerators;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

public class SlotFuelInput extends Slot {
    public SlotFuelInput(IInventory inventoryIn, int index, int xPosition, int yPosition) {
        super(inventoryIn, index, xPosition, yPosition);
    }

    /**
     * Check if the stack is allowed to be placed in this slot, used for armor slots as well as furnace fuel.
     */
    public boolean isItemValid(ItemStack stack) { return net.minecraftforge.common.ForgeHooks.getBurnTime(stack)>0 || isBucket(stack); }

    public int getItemStackLimit(ItemStack stack) {
        return isBucket(stack) ? 1 : super.getItemStackLimit(stack);
    }

    public static boolean isBucket(ItemStack stack) { return stack.getItem() == Items.BUCKET; }
}
