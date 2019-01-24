/*
 * Copyright (c) 2019 CovertDragon Team.
 * Copyright (c) 2019 Contributors of SpringFestival.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package team.covertdragon.springfestival.fortune.utils;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.fml.common.FMLCommonHandler;
import team.covertdragon.springfestival.fortune.machines.AbstractTileFVMachine;

public class TEDefinition implements INBTSerializable<NBTTagCompound> {
    private BlockPos pos;
    private int dim;

    public TEDefinition() {
    }

    public TEDefinition(TileEntity te) {
        this.pos = te.getPos();
        this.dim = te.getWorld().provider.getDimension();
    }

    public boolean available() {
        TileEntity te = world().getTileEntity(pos);
        if (te == null) return false;
        return te instanceof AbstractTileFVMachine && !te.isInvalid();
    }

    public boolean shouldClean() {
        return world().getTileEntity(pos) == null || !(world().getTileEntity(pos) instanceof AbstractTileFVMachine);
    }

    public TileEntity getTE() {
        return world().getTileEntity(pos);
    }

    @Override
    public NBTTagCompound serializeNBT() {
        NBTTagCompound nbt = new NBTTagCompound();
        nbt.setLong("pos", pos.toLong());
        nbt.setInteger("dim", dim);
        return nbt;
    }

    @Override
    public void deserializeNBT(NBTTagCompound nbt) {
        this.dim = nbt.getInteger("dim");
        this.pos = BlockPos.fromLong(nbt.getLong("pos"));
    }

    private World world() {
        return FMLCommonHandler.instance().getMinecraftServerInstance().getWorld(dim);
    }

    public static TEDefinition fromNBT(NBTTagCompound nbt) {
        TEDefinition def = new TEDefinition();
        def.deserializeNBT(nbt);
        return def;
    }
}
