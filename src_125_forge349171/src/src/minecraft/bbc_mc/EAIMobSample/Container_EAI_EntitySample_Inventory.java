package bbc_mc.EAIMobSample;

import net.minecraft.src.Container;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.ItemStack;
import net.minecraft.src.Slot;

public class Container_EAI_EntitySample_Inventory extends Container {
    EAI_EntitySample entity;
    EntityPlayer player;
    
    public Container_EAI_EntitySample_Inventory(EAI_EntitySample entity, EntityPlayer player) {
        this.entity = entity;
        this.player = player;
        
        for (int j = 0; j < 4; j++) {
            for (int i1 = 0; i1 < 9; i1++) {
                addSlot(new Slot(entity.inventory, i1 + j * 9, 8 + i1 * 18, 18 + j * 18));
            }
        }
        
        int var3;
        
        for (var3 = 0; var3 < 3; ++var3) {
            for (int var4 = 0; var4 < 9; ++var4) {
                this.addSlot(new Slot(player.inventory, var4 + var3 * 9 + 9, 8 + var4 * 18, 103 + var3 * 18));
            }
        }
        
        for (var3 = 0; var3 < 9; ++var3) {
            this.addSlot(new Slot(player.inventory, var3, 8 + var3 * 18, 161));
        }
    }
    
    @Override
    public boolean canInteractWith(EntityPlayer var1) {
        return true;
    }
    
    @Override
    public ItemStack transferStackInSlot(int i) {
        ItemStack itemstack = null;
        Slot slot = (Slot) inventorySlots.get(i);
        
        if (slot != null && slot.getHasStack()) {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();
            
            if (i < entity.inventory.getSizeInventory()) {
                if (!mergeItemStack(itemstack1, entity.inventory.getSizeInventory(), inventorySlots.size(), true)) {
                    return null;
                }
            } else if (!mergeItemStack(itemstack1, 0, 10, false)) {
                return null;
            }
            
            if (itemstack1.stackSize == 0) {
                slot.putStack(null);
            } else {
                slot.onSlotChanged();
            }
        }
        
        return itemstack;
    }
}
