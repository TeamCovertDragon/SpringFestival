/*
 * Copyright (c) 2018 CovertDragon Team.
 * Copyright (c) 2018 Contributors of SpringFestival.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package team.covertdragon.springfestival;

import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import team.covertdragon.springfestival.module.material.MaterialRegistry;

public class SpringFestivalConstants {

    public static final String MOD_ID = "springfestival";

    public static final String NAME = "SpringFestival";

    public static final ItemGroup SPRING_GROUP = new ItemGroup("spring_festival") {
        @Override
        public ItemStack createIcon() {
            return new ItemStack(MaterialRegistry.RED_PAPER); // Yeah... we have hard-dep on this one
        }
    };

    public static org.apache.logging.log4j.Logger logger;
}
