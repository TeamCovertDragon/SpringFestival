/*
 * Copyright (c) 2018 CovertDragon Team.
 * Copyright (c) 2018 Contributors of SpringFestival.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package team.covertdragon.springfestival;

import net.minecraft.entity.player.EntityPlayer;

import javax.annotation.Nullable;
import java.util.UUID;

public abstract class SpringFestivalProxy {

    /**
     * Helper method to determine whether this proxy is running on a physical server environment
     *
     * @return true if this proxy object is on a physical server; false otherwise.
     */
    public abstract boolean isPhysicalServer();

    /**
     * Helper method to determine whether this proxy is running on a physical client environment
     *
     * @return true if this proxy object is on a physical client; false otherwise.
     */
    public abstract boolean isPhysicalClient();

    @Nullable
    public abstract EntityPlayer getPlayerByUUID(UUID uuid);

    public abstract void scheduleTask(Runnable task);
}
