/*
 * Copyright (c) 2018 CovertDragon Team.
 * Copyright (c) 2018 Contributors of SpringFestival.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package team.covertdragon.springfestival.module.firecracker.hanging;

import javax.annotation.Nullable;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.tileentity.TileEntity;

public class TileHangingFirecracker extends TileEntity {
    @Nullable
    private EntityLivingBase igniter;

    public TileHangingFirecracker() {
    }

    public void setIgniter(EntityLivingBase igniter) {
        this.igniter = igniter;
    }
    
    public EntityLivingBase getIgniter() {
        return this.igniter;
    }
}
