/*
 * Copyright (c) 2018 CovertDragon Team.
 * Copyright (c) 2018 Contributors of SpringFestival.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package team.covertdragon.springfestival.module.firecracker.hanging;

import javax.annotation.Nonnull;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityFireworkRocket;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Items;
import net.minecraft.item.ItemDye;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import team.covertdragon.springfestival.module.firecracker.FirecrackerRegistry;

public class TileHangingFirecracker extends TileEntity {
    private EntityLivingBase igniter;

    public TileHangingFirecracker() {
    }

    @Override
    @Nonnull
    public NBTTagCompound writeToNBT(@Nonnull NBTTagCompound tag) {
        super.writeToNBT(tag);
        tag.setUniqueId("igniter", igniter.getUniqueID());
        return tag;
    }

    @Override
    public void readFromNBT(@Nonnull NBTTagCompound tag) {
        super.readFromNBT(tag);
        this.igniter.setUniqueId(tag.getUniqueId("igniter"));
    }

    public void setIgniter(EntityLivingBase igniter) {
        this.igniter = igniter;
    }
    
    public EntityLivingBase getIgniter() {
        return this.igniter;
    }
}
