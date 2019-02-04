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
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.HashMap;
import java.util.Map;

@Config(modid = SpringFestivalConstants.MOD_ID)
@Mod.EventBusSubscriber(modid = SpringFestivalConstants.MOD_ID)
@SuppressWarnings("CanBeFinal")
public final class SpringFestivalConfig {

    @Config.Comment("Toggles of all modules available in Spring Festival mod.")
    @Config.Name("modules")
    public static final Map<String, Boolean> MODULES = new HashMap<>();

    static {
        MODULES.put("calligraphy", Boolean.TRUE);
        MODULES.put("decoration", Boolean.TRUE);
        MODULES.put("firecracker", Boolean.TRUE);
        MODULES.put("food", Boolean.TRUE);
        MODULES.put("material", Boolean.TRUE);
        MODULES.put("monster", Boolean.TRUE);
        MODULES.put("music", Boolean.TRUE);
        MODULES.put("redpacket", Boolean.TRUE);
        MODULES.put("fortune", Boolean.TRUE);
        MODULES.put("trick", Boolean.FALSE);
    }

    @Config.Comment("If true, the UTC +08:00 timezone, the traditional time zone used for calculating Chinese Lunar Calendar, will be used to determine whether today is in Spring Festival Season or not.")
    @Config.Name("Enforce UTC+0800 Timezone")
    public static boolean enforceChinaStandardTime = true;

    @Config.Comment("If true, the mod will consider any day in January or February as \"during Spring Festival season\".")
    public static boolean useFuzzySpringFestivalMatcher = false;

    @SubscribeEvent
    public static void syncConfig(ConfigChangedEvent.OnConfigChangedEvent event) {
        if (SpringFestivalConstants.MOD_ID.equals(event.getModID())) {
            ConfigManager.sync(SpringFestivalConstants.MOD_ID, Config.Type.INSTANCE);
        }
    }
}
