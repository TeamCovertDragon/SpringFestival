/*
 * Copyright (c) 2018 CovertDragon Team.
 * Copyright (c) 2018 Contributors of SpringFestival.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package team.covertdragon.springfestival.module.fortune;

import net.minecraftforge.fml.common.registry.GameRegistry;
import team.covertdragon.springfestival.SpringFestivalConstants;
import team.covertdragon.springfestival.module.fortune.potion.PotionFortunate;
import team.covertdragon.springfestival.module.fortune.tools.DebugTool;
import team.covertdragon.springfestival.module.fortune.tools.FortuneStone;

@GameRegistry.ObjectHolder(SpringFestivalConstants.MOD_ID)
public class FortuneRegistry {
    static {
        fortuneStone = null;
        potionFortunate = null;
        debugTool = null;
    }

    @GameRegistry.ObjectHolder("fortune_stone")
    public static final FortuneStone fortuneStone;

    @GameRegistry.ObjectHolder("debug_tool")
    public static final DebugTool debugTool;

    @GameRegistry.ObjectHolder("fortunate")
    public static final PotionFortunate potionFortunate;
}
