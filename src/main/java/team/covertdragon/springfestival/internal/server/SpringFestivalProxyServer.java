/*
 * Copyright (c) 2018 CovertDragon Team.
 * Copyright (c) 2018 Contributors of SpringFestival.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package team.covertdragon.springfestival.internal.server;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.FMLCommonHandler;
import team.covertdragon.springfestival.SpringFestivalProxy;

import javax.annotation.Nullable;
import java.util.UUID;

public final class SpringFestivalProxyServer extends SpringFestivalProxy {

    @Override
    public boolean isPhysicalServer() {
        return true;
    }

    @Override
    public boolean isPhysicalClient() {
        return false;
    }

    @Nullable
    @Override
    public EntityPlayerMP getPlayerByUUID(UUID uuid) {
        return FMLCommonHandler.instance().getMinecraftServerInstance().getPlayerList().getPlayerByUUID(uuid);
    }

    @Override
    public void scheduleTask(Runnable task) {
        FMLCommonHandler.instance().getMinecraftServerInstance().addScheduledTask(task);
    }
}
