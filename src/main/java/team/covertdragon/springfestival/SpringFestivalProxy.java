/*
 * Copyright (c) 2018 CovertDragon Team.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package team.covertdragon.springfestival;

import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import team.covertdragon.springfestival.internal.time.ISpringFestivalTimeProvider;

import java.util.ArrayList;
import java.util.List;

public abstract class SpringFestivalProxy {

    private static final List<ISpringFestivalTimeProvider> DATE_CHECKERS = new ArrayList<>();

    /**
     * Determine whether the current time is falling into the Spring Festival season, based on
     * current system time.
     * @return true if it is during Spring Festival; false for otherwise.
     */
    public boolean isDuringSpringFestivalSeason() {
        boolean queryResult = false;
        for (ISpringFestivalTimeProvider checker : DATE_CHECKERS) {
            queryResult |= checker.isDuringSpringFestival();
        }
        return queryResult;
    }

    public abstract void onPreInit(FMLPreInitializationEvent event);

    public abstract void onInit(FMLInitializationEvent event);

    public abstract void onPostInit(FMLPostInitializationEvent event);

}
