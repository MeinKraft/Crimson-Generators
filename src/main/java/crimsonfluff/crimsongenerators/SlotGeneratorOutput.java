package crimsonfluff.crimsongenerators;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class SlotGeneratorOutput extends Slot {
    private static Item itemOutput;// = Items.AIR;

    public SlotGeneratorOutput(IInventory inventoryIn, int index, int xPosition, int yPosition, Item item) {
        super(inventoryIn, index, xPosition, yPosition);

        itemOutput = item;
    }

    @Override
    public boolean isItemValid(ItemStack stack) {
//        CrimsonChest.LOGGER.info("ItemValidate OUTPUT");
//        return false;

        return (stack.getItem() == itemOutput);
    }

/*    public int getItemStackLimit(ItemStack stack) {
        return 119;
    }

    // Shift Click into OutputSlot obeys this, UPTO 64 !
    public int getSlotStackLimit() {
        return Integer.MAX_VALUE;
    }*/
}
