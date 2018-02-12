/*
 * Copyright (c) 2018 CovertDragon Team.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package team.covertdragon.springfestival;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.event.*;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import team.covertdragon.springfestival.internal.SpringFestivalGuiHandler;
import team.covertdragon.springfestival.internal.time.ISpringFestivalTimeProvider;
import team.covertdragon.springfestival.internal.time.SpringFestivalTimeProviderImpossible;
import team.covertdragon.springfestival.internal.time.SpringFestivalTimeProviderLocal;
import team.covertdragon.springfestival.internal.time.SpringFestivalTimeProviderQuerying;
import team.covertdragon.springfestival.module.AbstractSpringFestivalModule;
import team.covertdragon.springfestival.module.ISpringFestivalModule;
import team.covertdragon.springfestival.module.ModuleLoader;
import team.covertdragon.springfestival.module.SpringFestivalModule;
import team.covertdragon.springfestival.module.redpacket.RedPacketDispatchingController;

import javax.annotation.OverridingMethodsMustInvokeSuper;
import java.util.*;

public abstract class SpringFestivalProxy {

    private static final List<ISpringFestivalTimeProvider> DATE_CHECKERS = new ArrayList<>();
    private List<? extends ISpringFestivalModule> modules = Collections.emptyList();

    static {
        FluidRegistry.enableUniversalBucket();
        DATE_CHECKERS.add(SpringFestivalTimeProviderQuerying.INSTANCE);
        DATE_CHECKERS.add(SpringFestivalTimeProviderLocal.INSTANCE);
        DATE_CHECKERS.add(SpringFestivalTimeProviderImpossible.INSTANCE);
    }

    private boolean isDuringSpringFestival = false, hasQueriedTime = false;

    /**
     * Determine whether the current time is falling into the Spring Festival season, based on
     * current system time.
     * @return true if it is during Spring Festival; false for otherwise.
     */
    public boolean isDuringSpringFestivalSeason() {
        if (hasQueriedTime) {
            return isDuringSpringFestival;
        }
        for (ISpringFestivalTimeProvider checker : DATE_CHECKERS) {
            this.isDuringSpringFestival |= checker.isDuringSpringFestival();
        }
        this.hasQueriedTime = true;
        return isDuringSpringFestival;
    }

    public void onConstruct(FMLConstructionEvent event) {
        modules = ModuleLoader.readASMDataTable(event.getASMHarvestedData());
        modules.forEach(MinecraftForge.EVENT_BUS::register);
    }

    @OverridingMethodsMustInvokeSuper
    public void onPreInit(FMLPreInitializationEvent event) {
        SpringFestivalConstants.logger = event.getModLog();
    }

    @OverridingMethodsMustInvokeSuper
    public void onInit(FMLInitializationEvent event) {
        NetworkRegistry.INSTANCE.registerGuiHandler(SpringFestival.getInstance(), new SpringFestivalGuiHandler());
    }

    public abstract void onPostInit(FMLPostInitializationEvent event);

    public void onServerStarting(FMLServerStartingEvent event) {
        modules.forEach(ISpringFestivalModule::onServerStarting);
    }

    public void onServerStopping(FMLServerStoppingEvent event) {
        modules.forEach(ISpringFestivalModule::onServerStopping);
    }

}
