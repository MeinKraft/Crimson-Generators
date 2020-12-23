package crimsonfluff.crimsongenerators;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;

public class SlotGeneratorOutput extends Slot {
    //private static Item itemOutput;// = Items.AIR;

    public SlotGeneratorOutput(IInventory inventoryIn, int index, int xPosition, int yPosition) {
        super(inventoryIn, index, xPosition, yPosition);

        //itemOutput = item;
    }

//    public int getSlotStackLimit() {
//        return 79;
//    }
//
//    public int getItemStackLimit(ItemStack stack) {
//        return 64;
//    }


    // NOTE: If slot is empty then this is called
    public boolean isItemValid(ItemStack stack) {
        //CrimsonGenerators.LOGGER.info("SLOT ISITEMVALID: ");

        return false;

//        return (stack.getItem() == itemOutput);
    }
}
