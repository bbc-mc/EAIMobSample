package bbc_mc.EAIMobSample;

import net.minecraft.src.EntityItem;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.IInventory;
import net.minecraft.src.ItemFood;
import net.minecraft.src.ItemStack;
import net.minecraft.src.NBTTagCompound;
import net.minecraft.src.YoujoInventory;

public class Inventory_EAI_EntitySample implements IInventory {
    
    public ItemStack Contents[];
    private EAI_EntitySample entity;
    
    public Inventory_EAI_EntitySample(EAI_EntitySample entity, ItemStack[] i) {
        this.Contents = i;
        this.entity = entity;
    }
    
    @Override
    public int getSizeInventory() {
        return 36;
    }
    
    @Override
    public ItemStack getStackInSlot(int i) {
        return Contents[i];
    }
    
    @Override
    public ItemStack decrStackSize(int i, int j) {
        if (Contents[i] != null) {
            if (Contents[i].stackSize <= j) {
                ItemStack itemstack = Contents[i];
                Contents[i] = null;
                onInventoryChanged();
                return itemstack;
            }
            
            ItemStack itemstack1 = Contents[i].splitStack(j);
            
            if (Contents[i].stackSize == 0) {
                Contents[i] = null;
            }
            
            onInventoryChanged();
            return itemstack1;
        } else {
            return null;
        }
    }
    
    @Override
    public ItemStack getStackInSlotOnClosing(int i) {
        if (Contents[i] != null) {
            ItemStack itemstack = Contents[i];
            Contents[i] = null;
            return itemstack;
        } else {
            return null;
        }
    }
    
    @Override
    public void setInventorySlotContents(int i, ItemStack itemstack) {
        Contents[i] = itemstack;
        
        if (itemstack != null && itemstack.stackSize > getInventoryStackLimit()) {
            itemstack.stackSize = getInventoryStackLimit();
        }
        
        onInventoryChanged();
    }
    
    @Override
    public String getInvName() {
        return "Entity's Inventory";
    }
    
    @Override
    public int getInventoryStackLimit() {
        return 64;
    }
    
    @Override
    public void onInventoryChanged() {
    }
    
    @Override
    public boolean isUseableByPlayer(EntityPlayer entityplayer) {
        return entityplayer.getDistanceSq((double) entity.posX + 0.5D, (double) entity.posX + 0.5D, (double) entity.posX + 0.5D) <= 64D
                && !entity.isDead;
    }
    
    @Override
    public void openChest() {
    }
    
    @Override
    public void closeChest() {
    }
    
    public int getInventorySlotContainItem(int par1) {
        for (int i = 0; i < Contents.length; i++) {
            if (Contents[i] != null && Contents[i].itemID == par1) {
                return i;
            }
        }
        
        return -1;
    }
    
    public int storeItemStack(ItemStack par1ItemStack) {
        for (int i = 0; i < Contents.length; i++) {
            if (Contents[i] != null && Contents[i].itemID == par1ItemStack.itemID && Contents[i].isStackable()
                    && Contents[i].stackSize < Contents[i].getMaxStackSize() && Contents[i].stackSize < getInventoryStackLimit()
                    && (!Contents[i].getHasSubtypes() || Contents[i].getItemDamage() == par1ItemStack.getItemDamage())
                    && ItemStack.func_46154_a(Contents[i], par1ItemStack)) {
                return i;
            }
        }
        
        return -1;
    }
    
    public int getFirstEmptyStack() {
        for (int i = 0; i < Contents.length; i++) {
            if (Contents[i] == null) {
                return i;
            }
        }
        
        return -1;
    }
    
    public int storePartialItemStack(ItemStack par1ItemStack) {
        int i = par1ItemStack.itemID;
        int j = par1ItemStack.stackSize;
        
        if (par1ItemStack.getMaxStackSize() == 1) {
            int k = getFirstEmptyStack();
            
            if (k < 0) {
                return j;
            }
            
            if (Contents[k] == null) {
                Contents[k] = ItemStack.copyItemStack(par1ItemStack);
            }
            
            return 0;
        }
        
        int l = storeItemStack(par1ItemStack);
        
        if (l < 0) {
            l = getFirstEmptyStack();
        }
        
        if (l < 0) {
            return j;
        }
        
        if (Contents[l] == null) {
            Contents[l] = new ItemStack(i, 0, par1ItemStack.getItemDamage());
            
            if (par1ItemStack.hasTagCompound()) {
                Contents[l].setTagCompound((NBTTagCompound) par1ItemStack.getTagCompound().copy());
            }
        }
        
        int i1 = j;
        
        if (i1 > Contents[l].getMaxStackSize() - Contents[l].stackSize) {
            i1 = Contents[l].getMaxStackSize() - Contents[l].stackSize;
        }
        
        if (i1 > getInventoryStackLimit() - Contents[l].stackSize) {
            i1 = getInventoryStackLimit() - Contents[l].stackSize;
        }
        
        if (i1 == 0) {
            return j;
        } else {
            j -= i1;
            Contents[l].stackSize += i1;
            Contents[l].animationsToGo = 5;
            return j;
        }
    }
    
    public boolean consumeInventoryItem(int par1) {
        int i = getInventorySlotContainItem(par1);
        
        if (i < 0) {
            return false;
        }
        
        if (--Contents[i].stackSize <= 0) {
            Contents[i] = null;
        }
        
        return true;
    }
    
    public boolean hasItem(int par1) {
        int i = getInventorySlotContainItem(par1);
        return i >= 0;
    }
    
    public boolean addItemStackToInventory(ItemStack par1ItemStack) {
        if (!par1ItemStack.isItemDamaged()) {
            int i;
            
            do {
                i = par1ItemStack.stackSize;
                par1ItemStack.stackSize = storePartialItemStack(par1ItemStack);
            } while (par1ItemStack.stackSize > 0 && par1ItemStack.stackSize < i);
            return par1ItemStack.stackSize < i;
        }
        
        int j = getFirstEmptyStack();
        
        if (j >= 0) {
            Contents[j] = ItemStack.copyItemStack(par1ItemStack);
            Contents[j].animationsToGo = 5;
            par1ItemStack.stackSize = 0;
            return true;
        }
        return false;
    }
    
    public boolean removeItemStackToInventory(ItemStack par1ItemStack) {
        int size = par1ItemStack.stackSize;
        int contentsStack = 0;
        for (ItemStack item : Contents) {
            if (item != null && item.isItemEqual(par1ItemStack)) {
                contentsStack += item.stackSize;
            }
        }
        
        if (contentsStack < size) {
            return false;
        } else {
            for (int i = 0; i < Contents.length; i++) {
                if (Contents[i] != null && Contents[i].isItemEqual(par1ItemStack)) {
                    if (Contents[i].stackSize <= size) {
                        size -= Contents[i].stackSize;
                        Contents[i] = null;
                    } else if (Contents[i].stackSize > size) {
                        Contents[i].stackSize -= size;
                        break;
                    } else {
                        break;
                    }
                }
            }
            return true;
        }
    }
    
    public boolean hasItemStack(ItemStack par1ItemStack) {
        
        for (int j = 0; j < Contents.length; j++) {
            if (Contents[j] != null && Contents[j].isStackEqual(par1ItemStack)) {
                return true;
            }
        }
        
        return false;
    }
    
    public void copyInventory(YoujoInventory par1InventoryYoujo) {
        for (int i = 0; i < Contents.length; i++) {
            Contents[i] = ItemStack.copyItemStack(par1InventoryYoujo.Contents[i]);
        }
    }
    
    public boolean hasFood(ItemStack item) {
        return item.getItem() instanceof ItemFood;
    }
    
    public void dropAll() {
        for (int i = 0; i < Contents.length; i++) {
            itemDrop(i);
        }
    }
    
    public void itemDrop(int i) {
        if (i < Contents.length && i >= 0 && Contents[i] != null) {
            entity.worldObj.spawnEntityInWorld(new EntityItem(entity.worldObj, entity.posX, entity.posY, entity.posZ, Contents[i]));
            Contents[i] = null;
        }
    }
    
    public int getItemStackSize(ItemStack item) {
        int count = 0;
        for (int i = 0; i < Contents.length; i++) {
            if (Contents[i] != null && Contents[i].isItemEqual(item)) {
                count += Contents[i].stackSize;
            }
        }
        return count;
    }
}
