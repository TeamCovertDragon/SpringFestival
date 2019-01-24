/*
 * Copyright (c) 2018 CovertDragon Team.
 * Copyright (c) 2018 Contributors of SpringFestival.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package team.covertdragon.springfestival.trick;

import team.covertdragon.springfestival.SpringFestival;
import team.covertdragon.springfestival.module.AbstractSpringFestivalModule;
import team.covertdragon.springfestival.module.SpringFestivalModule;

/**
 * The module that will crash the game when it's Spring Festival, forcing
 * player to spend more time with their families, rather than the virtual world.
 *
 * This module is disabled by default.
 */
@SpringFestivalModule(name = "trick")
public class ModuleTrick extends AbstractSpringFestivalModule {
    @Override
    public void onPreInit() {
        if (SpringFestival.proxy.isPhysicalClient() && SpringFestival.proxy.isDuringSpringFestivalSeason()) {
            throw new PleaseSpendMoreTimeWithYourFamilyException();
        }
    }
}
