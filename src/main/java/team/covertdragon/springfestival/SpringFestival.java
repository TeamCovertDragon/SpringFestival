/*
 * Copyright (c) 2018 CovertDragon Team.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package team.covertdragon.springfestival;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.event.FMLServerStoppingEvent;

@Mod(modid = SpringFestivalConstants.MOD_ID, name = SpringFestivalConstants.NAME, version = "@VERSION_INJECT@", useMetadata = true)
public final class SpringFestival {

    private static final SpringFestival INSTANCE = new SpringFestival();

    @Mod.InstanceFactory
    public static SpringFestival getInstance() {
        return INSTANCE;
    }

    @SidedProxy(
            serverSide = "team.covertdragon.springfestival.internal.server.SpringFestivalProxyServer",
            clientSide = "team.covertdragon.springfestival.internal.client.SpringFestivalProxyClient"
    )
    public static SpringFestivalProxy proxy;

    private SpringFestival() {}

    @Mod.EventHandler
    public void onPreInit(FMLPreInitializationEvent event) {
        proxy.onPreInit(event);
    }

    @Mod.EventHandler
    public void onInit(FMLInitializationEvent event) {
        proxy.onInit(event);
    }

    @Mod.EventHandler
    public void onPostInit(FMLPostInitializationEvent event) {
        proxy.onPostInit(event);
    }

    @Mod.EventHandler
    public void onServerStarting(FMLServerStartingEvent event) {
        proxy.onServerStarting(event);
    }

    @Mod.EventHandler
    public void onServerStopping(FMLServerStoppingEvent event) {
        proxy.onServerStopping(event);
    }
}
