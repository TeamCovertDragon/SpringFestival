/*
 * Copyright (c) 2018 CovertDragon Team.
 * Copyright (c) 2018 Contributors of SpringFestival.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package team.covertdragon.springfestival.module;

public interface ISpringFestivalModule {

    /**
     * Called when FMLInitializationEvent is dispatched.
     */
    void onInit();


    /**
     *  Called when FMLPreInitializationEvent is dispatched
     */
    default void onPreInit() {}

    /**
     * Called when FMLServerStartingEvent is dispatched.
     */
    default void onServerStarting() {}

    default void onServerStopping() {}
}
