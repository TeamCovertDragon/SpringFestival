/*
 * Copyright (c) 2018 CovertDragon Team.
 * Copyright (c) 2018 Contributors of SpringFestival.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package team.covertdragon.springfestival;

import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import net.minecraftforge.common.ForgeConfigSpec;

import java.nio.file.Paths;

@SuppressWarnings("CanBeFinal")
public final class SpringFestivalConfig {
/*
    @Config.Comment("Toggles of all modules available in Spring Festival mod.")
    @Config.LangKey("config.springfestival.modules")
    @Config.Name("modules")
    @Config.RequiresMcRestart
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
    }*/

    public static final ForgeConfigSpec CONFIG_SPEC;

    public static final TimeSetting TIME_SETTING;

    public static final class TimeSetting {
        public final ForgeConfigSpec.BooleanValue enforceChinaStandardTime;
        public final ForgeConfigSpec.BooleanValue useFuzzySpringFestivalMatcher;
        TimeSetting(ForgeConfigSpec.Builder config) {
            config.push("time");
            enforceChinaStandardTime = config
                    .comment("If true, the UTC +08:00 timezone, the traditional time zone used for calculating Chinese Lunar Calendar, will be used to determine whether today is in Spring Festival Season or not.")
                    .define("Enforce UTC+0800 Timezone", true);
            useFuzzySpringFestivalMatcher = config
                    .comment("If true, the mod will consider any day in January or February as \"during Spring Festival season\".")
                    .define("Use Fuzzy Spring Festival Time Checker", false);
        }
    }

    static {
        ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();
        TIME_SETTING = new TimeSetting(builder);
        (CONFIG_SPEC = builder.build()).setConfig(CommentedFileConfig.of(Paths.get("config", SpringFestivalConstants.MOD_ID + ".toml")));

    }

    private SpringFestivalConfig() {
        throw new UnsupportedOperationException("You just don't have the instance.");
    }
}
