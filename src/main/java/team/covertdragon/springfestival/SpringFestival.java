/*
 * Copyright (c) 2018 CovertDragon Team.
 * Copyright (c) 2018 Contributors of SpringFestival.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package team.covertdragon.springfestival;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.event.server.FMLServerStoppingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(SpringFestivalConstants.MOD_ID)
public final class SpringFestival {

    private static SpringFestival INSTANCE;

    public static SpringFestival getInstance() {
        return INSTANCE;
    }

    public SpringFestival() {
        INSTANCE = this;
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::onServerStarting);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::onServerStopping);
    }

    private void setup(FMLCommonSetupEvent event) {
        SpringFestivalModuleController.INSTANCE.onConstruct(event);
        //SpringFestivalConstants.logger = event.getModLog(); // TODO (3TUSK): We need a different approach.
        //NetworkRegistry.INSTANCE.registerGuiHandler(INSTANCE, new SpringFestivalGuiHandler()); // TODO (3TUSK): Awaiting new GUIHandler stuff to be stable
    }

    private void onServerStarting(FMLServerStartingEvent event) {
        SpringFestivalModuleController.INSTANCE.onServerStarting(event);
    }

    public void onServerStopping(FMLServerStoppingEvent event) {
        SpringFestivalModuleController.INSTANCE.onServerStopping(event);
    }
}
