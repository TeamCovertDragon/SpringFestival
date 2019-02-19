/*
 * Copyright (c) 2018 CovertDragon Team.
 * Copyright (c) 2018 Contributors of SpringFestival.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package team.covertdragon.springfestival.internal.capabilities;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Arrays;

public class ItemStackInventoryProvider
        implements IItemHandler, IItemHandlerModifiable, ICapabilityProvider, ICapabilitySerializable<NBTTagCompound> {

    private final int maxSize;
    private final ItemStack[] inv;

    public ItemStackInventoryProvider(final int size) {
        this.maxSize = size;
        this.inv = new ItemStack[this.maxSize];
        Arrays.fill(inv, ItemStack.EMPTY);
    }

    @Override
    public void setStackInSlot(int slot, @Nonnull ItemStack stack) {
        if (slot < this.maxSize) {
            inv[slot] = stack;
        }
    }

    @Override
    public int getSlots() {
        return this.maxSize;
    }

    @Nonnull
    @Override
    public ItemStack getStackInSlot(int slot) {
        return slot < maxSize ? inv[slot] : ItemStack.EMPTY;
    }

    @Nonnull
    @Override // TODO Check if this implementation is correct
    public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate) {
        if (slot < this.maxSize) {
            if (simulate) {
                return inv[slot].isEmpty() ? ItemStack.EMPTY : inv[slot].isItemEqual(stack) ? stack.copy().split(stack.getCount() + inv[slot].getCount() - stack.getMaxStackSize()) : ItemStack.EMPTY;
            } else {
                return inv[slot].isEmpty() ? ItemStack.EMPTY : inv[slot].isItemEqual(stack) ? stack.split(stack.getCount() + inv[slot].getCount() - stack.getMaxStackSize()) : ItemStack.EMPTY;
            }
        } else {
            return stack;
        }
    }

    @Nonnull
    @Override
    public ItemStack extractItem(int slot, int amount, boolean simulate) {
        if (slot < this.maxSize) {
            if (simulate) {
                return inv[slot].copy().split(amount);
            } else {
                return inv[slot].split(amount);
            }
        } else {
            return ItemStack.EMPTY;
        }
    }

    @Override
    public int getSlotLimit(int slot) {
        return slot < this.maxSize ? inv[slot].getMaxStackSize() : 0;
    }

    @Nullable
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> capability, @Nullable EnumFacing facing) {
        return capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY ?
                LazyOptional.of(() -> (T)this) : null;
    }

    @Override
    public NBTTagCompound serializeNBT() {
        // TODO (3TUSK): avoid casting
        return (NBTTagCompound)CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.writeNBT(this, null);
    }

    @Override
    public void deserializeNBT(NBTTagCompound nbt) {
        CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.readNBT(this, null, nbt);
    }
}
