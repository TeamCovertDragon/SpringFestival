/*
 * Copyright (c) 2019 CovertDragon Team.
 * Copyright (c) 2019 Contributors of SpringFestival.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package team.covertdragon.springfestival.module.fortune.utils;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionType;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.fml.server.ServerLifecycleHooks;
import team.covertdragon.springfestival.module.fortune.machines.AbstractTileFVMachine;

public class TEDefinition implements INBTSerializable<NBTTagCompound> {
    private BlockPos pos;
    private int dim;

    public TEDefinition() {
    }

    public TEDefinition(TileEntity te) {
        this.pos = te.getPos();
        this.dim = te.getWorld().dimension.getType().getId(); // TODO (3TUSK/SeraphJack): Check if it's correct
    }

    public boolean available() {
        TileEntity te = world().getTileEntity(pos);
        if (te == null) return false;
        return te instanceof AbstractTileFVMachine && !te.isRemoved();
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
        nbt.putLong("pos", pos.toLong());
        nbt.putInt("dim", dim);
        return nbt;
    }

    @Override
    public void deserializeNBT(NBTTagCompound nbt) {
        this.dim = nbt.getInt("dim");
        this.pos = BlockPos.fromLong(nbt.getLong("pos"));
    }

    private World world() {
        // TODO (3TUSK): Check if it's correct
        return ServerLifecycleHooks.getCurrentServer().getWorld(DimensionType.getById(dim));
        //return FMLCommonHandler.instance().getMinecraftServerInstance().getWorld(dim);
    }

    public static TEDefinition fromNBT(NBTTagCompound nbt) {
        TEDefinition def = new TEDefinition();
        def.deserializeNBT(nbt);
        return def;
    }
}
