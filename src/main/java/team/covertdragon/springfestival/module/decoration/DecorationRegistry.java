/*
 * Copyright (c) 2018 CovertDragon Team.
 * Copyright (c) 2018 Contributors of SpringFestival.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package team.covertdragon.springfestival.module.decoration;

import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.fml.common.registry.GameRegistry;
import team.covertdragon.springfestival.SpringFestivalConstants;
import team.covertdragon.springfestival.module.decoration.fudoor.BlockFuDoor;
import team.covertdragon.springfestival.module.decoration.redpillar.BlockLargeRedPillar;
import team.covertdragon.springfestival.module.decoration.redpillar.BlockLargeRedPillarAlt;

@GameRegistry.ObjectHolder(SpringFestivalConstants.MOD_ID)
public class DecorationRegistry {

    public static final Item FU;

    public static final ItemArmor RED_HAT;

    public static final ItemArmor RED_GOWN;

    public static final ItemArmor RED_TROUSERS;

    public static final ItemArmor RED_SHOES;

    @GameRegistry.ObjectHolder("fu_door")
    public static final BlockFuDoor FU_DOOR;

    @GameRegistry.ObjectHolder("large_red_pillar")
    public static final BlockLargeRedPillar blockLargeRedPillar;

    @GameRegistry.ObjectHolder("large_red_pillar")
    public static final ItemBlock itemLargeRedPillar;

    @GameRegistry.ObjectHolder("large_red_pillar_alt")
    public static final BlockLargeRedPillarAlt blockLargeRedPillarAlt;

    @GameRegistry.ObjectHolder("large_red_pillar_alt")
    public static final ItemBlock itemLargeRedPillarAlt;

    static {
        FU = null;
        RED_HAT = null;
        RED_GOWN = null;
        RED_TROUSERS = null;
        RED_SHOES = null;
        FU_DOOR = null;
        blockLargeRedPillar = null;
        itemLargeRedPillar = null;
        blockLargeRedPillarAlt = null;
        itemLargeRedPillarAlt = null;
    }
}
