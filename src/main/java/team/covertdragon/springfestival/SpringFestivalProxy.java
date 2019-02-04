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
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLConstructionEvent;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.event.FMLServerStoppingEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import team.covertdragon.springfestival.internal.SpringFestivalGuiHandler;
import team.covertdragon.springfestival.internal.time.SpringFestivalTimeChecker;
import team.covertdragon.springfestival.module.ISpringFestivalModule;
import team.covertdragon.springfestival.module.ModuleLoader;

import javax.annotation.Nullable;
import javax.annotation.OverridingMethodsMustInvokeSuper;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public abstract class SpringFestivalProxy {

    private final Map<String, ISpringFestivalModule> modules = new HashMap<>();
    private List<ISpringFestivalModule> moduleArrayList;

    /**
     * Determine whether the current time is falling into the Spring Festival season, based on
     * current system time.
     *
     * @return true if it is during Spring Festival; false for otherwise.
     *
     * @deprecated Use {@link SpringFestivalTimeChecker#isDuringSpringFestivalSeason()} directly.
     */
    //@Deprecated
    public final boolean isDuringSpringFestivalSeason() {
        return SpringFestivalTimeChecker.INSTANCE.isDuringSpringFestivalSeason();
    }

    public final boolean isModuleLoaded(final String module) {
        return modules.containsKey(module);
    }

    public final ISpringFestivalModule getModule(final String module) {
        return modules.get(module);
    }

    public final void onConstruct(FMLConstructionEvent event) {
        moduleArrayList = ModuleLoader.readASMDataTable(event.getASMHarvestedData());
        moduleArrayList.forEach(mod -> this.modules.put(ModuleLoader.getNameByInstance(mod), mod));
        moduleArrayList.forEach(MinecraftForge.EVENT_BUS::register);
        SpringFestivalTimeChecker.INSTANCE.reset();
    }

    @OverridingMethodsMustInvokeSuper
    public void onPreInit(FMLPreInitializationEvent event) {
        SpringFestivalConstants.logger = event.getModLog();
        moduleArrayList.forEach(ISpringFestivalModule::onPreInit);
    }

    @OverridingMethodsMustInvokeSuper
    public void onInit(FMLInitializationEvent event) {
        NetworkRegistry.INSTANCE.registerGuiHandler(SpringFestival.getInstance(), new SpringFestivalGuiHandler());
        moduleArrayList.forEach(ISpringFestivalModule::onInit);
    }

    @OverridingMethodsMustInvokeSuper
    public void onServerStarting(FMLServerStartingEvent event) {
        moduleArrayList.forEach(ISpringFestivalModule::onServerStarting);
    }

    @OverridingMethodsMustInvokeSuper
    public void onServerStopping(FMLServerStoppingEvent event) {
        moduleArrayList.forEach(ISpringFestivalModule::onServerStopping);
    }

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
