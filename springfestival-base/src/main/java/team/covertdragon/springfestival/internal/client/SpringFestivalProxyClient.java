/*
 * Copyright (c) 2018 CovertDragon Team.
 * Copyright (c) 2018 Contributors of SpringFestival.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package team.covertdragon.springfestival.internal.client;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import team.covertdragon.springfestival.SpringFestivalProxy;

import java.util.UUID;

@SideOnly(Side.CLIENT)
public final class SpringFestivalProxyClient extends SpringFestivalProxy {

    @Override
    public boolean isPhysicalServer() {
        return false;
    }

    @Override
    public boolean isPhysicalClient() {
        return true;
    }

    // TODO This implementation is potentially wrong, needs double check
    @Override
    public EntityPlayer getPlayerByUUID(UUID uuid) {
        if (Minecraft.getMinecraft().isSingleplayer()) {
            return Minecraft.getMinecraft().getIntegratedServer().getPlayerList().getPlayerByUUID(uuid);
        } else {
            return FMLClientHandler.instance().getServer().getPlayerList().getPlayerByUUID(uuid);
        }
    }

    @Override
    public void scheduleTask(Runnable task) {
        Minecraft.getMinecraft().addScheduledTask(task);
    }


}
