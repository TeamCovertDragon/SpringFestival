/*
 * Copyright (c) 2018 CovertDragon Team.
 * Copyright (c) 2018 Contributors of SpringFestival.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package team.covertdragon.springfestival;

import net.minecraftforge.common.config.Config;

@Config(modid = SpringFestivalConstants.MOD_ID)
@SuppressWarnings("CanBeFinal")
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
            "redpacket",
            "fortune"
    };

    @Config.Comment("If true, the UTC +08:00 timezone, the traditional time zone used for calculating Chinese Lunar Calendar, will be used to determine whether today is in Spring Festival Season or not.")
    @Config.Name("Enforce UTC+0800 Timezone")
    public static boolean enforceChinaStandardTime = true;

    @Config.Comment("If true, the mod will consider any day in January or February as \"during Spring Festival season\".")
    public static boolean useFuzzySpringFestivalMatcher = false;
}
