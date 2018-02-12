/*
 * Copyright (c) 2018 CovertDragon Team.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package team.covertdragon.springfestival;

import net.minecraftforge.common.config.Config;

@Config(modid = SpringFestivalConstants.MOD_ID)
public final class SpringFestivalConfig {

    @Config.Comment("A list of module ids that are expected to be loaded.")
    public static String[] modules = new String[] {
            "calligraphy",
            "decoration",
            "firecracker",
            "food",
            "material",
            "monster",
            "music",
            "redpacket"
    };
}
