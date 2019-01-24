/*
 * Copyright (c) 2018 CovertDragon Team.
 * Copyright (c) 2018 Contributors of SpringFestival.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package team.covertdragon.springfestival.redpacket;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.SlotItemHandler;

public class ContainerRedPacket extends Container {

    public ContainerRedPacket(InventoryPlayer playerInv, IItemHandlerModifiable inv) {
        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 9; ++j) {
                this.addSlotToContainer(new Slot(playerInv, j + i * 9 + 9, 9 + 8 + j * 18, 10 + 84 + i * 18));
            }
        }
        for (int i = 0; i < 9; ++i) {
            this.addSlotToContainer(new Slot(playerInv, i, 9 + 8 + i * 18, 142 + 10));
        }
        for (int i = 0; i < 8; ++i) {
            this.addSlotToContainer(new SlotItemHandler(inv, i, 26 + i * 18, 65));
        }
    }

    @Override
    public boolean canInteractWith(EntityPlayer playerIn) {
        return playerIn.getHeldItemMainhand().getItem() == ModuleRedPacket.RED_PACKET || playerIn.getHeldItemOffhand().getItem() == ModuleRedPacket.RED_PACKET;
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer playerIn, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.inventorySlots.get(index);

        if (slot != null && slot.getHasStack()) {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();

            if (index <= 8) {
                if (!this.mergeItemStack(itemstack1, 8, 27 + 8, true)) {
                    return ItemStack.EMPTY;
                }
            } else if (index < 27 + 8) {
                if (!this.mergeItemStack(itemstack1, this.inventorySlots.size() - 9, this.inventorySlots.size(), false)) {
                    return ItemStack.EMPTY;
                }
            }

            if (itemstack1.getCount() == 0) {
                slot.putStack(ItemStack.EMPTY);
            } else {
                slot.onSlotChanged();
            }

            if (itemstack1.getCount() == itemstack.getCount()) {
                return ItemStack.EMPTY;
            }

            slot.onTake(playerIn, itemstack1);
        }

        return itemstack;
    }
}
