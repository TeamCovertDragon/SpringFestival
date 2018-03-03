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
import net.minecraftforge.fml.common.event.*;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import team.covertdragon.springfestival.internal.SpringFestivalGuiHandler;
import team.covertdragon.springfestival.internal.time.ISpringFestivalTimeProvider;
import team.covertdragon.springfestival.internal.time.SpringFestivalTimeProviderFuzzyMatch;
import team.covertdragon.springfestival.module.ISpringFestivalModule;
import team.covertdragon.springfestival.module.ModuleLoader;

import javax.annotation.Nullable;
import javax.annotation.OverridingMethodsMustInvokeSuper;
import java.util.*;

public abstract class SpringFestivalProxy {

    private static final List<ISpringFestivalTimeProvider> DATE_CHECKERS = new ArrayList<>();
    private Map<String, ISpringFestivalModule> modules = new HashMap<>();
    private boolean isDuringSpringFestival = false, hasQueriedTime = false;

    /**
     * Determine whether the current time is falling into the Spring Festival season, based on
     * current system time.
     *
     * @return true if it is during Spring Festival; false for otherwise.
     */
    public final boolean isDuringSpringFestivalSeason() {
        if (hasQueriedTime) {
            return isDuringSpringFestival;
        }
        for (ISpringFestivalTimeProvider checker : DATE_CHECKERS) {
            this.isDuringSpringFestival |= checker.isDuringSpringFestival();
        }
        this.hasQueriedTime = true;
        return isDuringSpringFestival;
    }

    public final boolean isModuleLoaded(final String module) {
        //TODO: Here's your map, remove this after reading.
        return modules.containsKey(module);
    }

    public final ISpringFestivalModule getModule(final String module) {
        return modules.get(module);
    }

    public final void onConstruct(FMLConstructionEvent event) {
        ModuleLoader.readASMDataTable(event.getASMHarvestedData()).forEach(mod -> this.modules.put(ModuleLoader.getNameByInstance(mod), mod));
        modules.forEach((K, V) -> MinecraftForge.EVENT_BUS.register(V));
    }

    @OverridingMethodsMustInvokeSuper
    public void onPreInit(FMLPreInitializationEvent event) {
        SpringFestivalConstants.logger = event.getModLog();
        modules.forEach((K, V) -> V.onPreInit());
        DATE_CHECKERS.add(ISpringFestivalTimeProvider.fromURL("http://covertdragon.team/sfapi.html", "SpringFestival-DateQuerying"));
        DATE_CHECKERS.add(ISpringFestivalTimeProvider.impossible());
        if (SpringFestivalConfig.useFuzzySpringFestivalMatcher) {
            DATE_CHECKERS.add(SpringFestivalTimeProviderFuzzyMatch.INSTANCE);
        }
    }

    @OverridingMethodsMustInvokeSuper
    public void onInit(FMLInitializationEvent event) {
        NetworkRegistry.INSTANCE.registerGuiHandler(SpringFestival.getInstance(), new SpringFestivalGuiHandler());
        modules.forEach((K, V) -> V.onInit());
    }

    @OverridingMethodsMustInvokeSuper
    public void onServerStarting(FMLServerStartingEvent event) {
        SpringFestivalConstants.server = event.getServer();
        modules.forEach((K, V) -> V.onServerStarting());
    }

    @OverridingMethodsMustInvokeSuper
    public void onServerStopping(FMLServerStoppingEvent event) {
        modules.forEach((K, V) -> V.onServerStopping());
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
