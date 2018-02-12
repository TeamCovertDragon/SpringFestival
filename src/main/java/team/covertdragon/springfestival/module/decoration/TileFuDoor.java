/*
 * Copyright (c) 2018 CovertDragon Team.
 * Copyright (c) 2018 Contributors of SpringFestival.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package team.covertdragon.springfestival.module.decoration;

import jline.internal.Nullable;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.tileentity.TileEntity;

import javax.annotation.Nonnull;
import java.util.Random;

public class TileFuDoor extends TileEntity {
    IBlockState originalBlockStateUpper;
    IBlockState originalBlockStateLower;

    public ItemStack getOriginalDoor() {
        ItemStack stack = new ItemStack(originalBlockStateLower.getBlock().getItemDropped(originalBlockStateLower, new Random(), 0));
        if (stack.isEmpty())
            return new ItemStack(originalBlockStateUpper.getBlock().getItemDropped(originalBlockStateUpper, new Random(), 0));
        return stack;
    }

    @Nullable
    public IBlockState getOriginalBlockStateUpper() {
        return originalBlockStateUpper;
    }

    @Nullable
    public IBlockState getOriginalBlockStateLower() {
        return originalBlockStateLower;
    }

    public void setOriginalBlockStateUpper(IBlockState state) {
        this.originalBlockStateUpper = state;
    }

    public void setOriginalBlockStateLower(IBlockState state) {
        this.originalBlockStateLower = state;
    }

    @Override
    @Nonnull
    public NBTTagCompound writeToNBT(@Nonnull NBTTagCompound compound) {
        super.writeToNBT(compound);
        NBTTagCompound stateUpper = new NBTTagCompound();
        NBTTagCompound stateLower = new NBTTagCompound();
        NBTUtil.writeBlockState(stateUpper, originalBlockStateUpper);
        NBTUtil.writeBlockState(stateLower, originalBlockStateLower);
        compound.setTag("upper", stateUpper);
        compound.setTag("lower", stateLower);
        return compound;
    }

    @Override
    public void readFromNBT(@Nonnull NBTTagCompound compound) {
        super.readFromNBT(compound);
        this.originalBlockStateUpper = NBTUtil.readBlockState(compound.getCompoundTag("upper"));
        this.originalBlockStateLower = NBTUtil.readBlockState(compound.getCompoundTag("lower"));
    }
}
