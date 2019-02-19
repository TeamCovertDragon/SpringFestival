/*
 * Copyright (c) 2018 CovertDragon Team.
 * Copyright (c) 2018 Contributors of SpringFestival.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package team.covertdragon.springfestival.module.decoration.fudoor;

import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;

import javax.annotation.Nullable;

public class TileFuDoor extends TileEntity {

    public static final TileEntityType<TileFuDoor> TYPE_TOKEN = new TileEntityType<TileFuDoor>(TileFuDoor::new, null);

    private IBlockState originalBlockStateUpper;
    private IBlockState originalBlockStateLower;

    public TileFuDoor() {
        super(TYPE_TOKEN);
    }

    public ItemStack getOriginalDoor() {
        /*if (originalBlockStateLower == null || originalBlockStateUpper == null)
            return ItemStack.EMPTY;

        ItemStack stack = new ItemStack(originalBlockStateLower.getBlock().getItemDropped(originalBlockStateLower, new Random(), 0));
        if (stack.isEmpty())
            return new ItemStack(originalBlockStateUpper.getBlock().getItemDropped(originalBlockStateUpper, new Random(), 0));
        return stack;*/
        return ItemStack.EMPTY; // TODO (3TUSK): FIX ME
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
    public NBTTagCompound write(NBTTagCompound compound) {
        compound.put("upper", NBTUtil.writeBlockState(originalBlockStateUpper));
        compound.put("lower", NBTUtil.writeBlockState(originalBlockStateLower));
        return super.write(compound);
    }

    @Override
    public void read(NBTTagCompound compound) {
        super.read(compound);
        this.originalBlockStateUpper = NBTUtil.readBlockState(compound.getCompound("upper"));
        this.originalBlockStateLower = NBTUtil.readBlockState(compound.getCompound("lower"));
    }

    @Override
    public boolean hasFastRenderer() {
        return true;
    }
}
