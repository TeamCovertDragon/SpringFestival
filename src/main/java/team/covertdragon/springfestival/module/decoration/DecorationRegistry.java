/*
 * Copyright (c) 2018 CovertDragon Team.
 * Copyright (c) 2018 Contributors of SpringFestival.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package team.covertdragon.springfestival.module.decoration;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.GameRegistry;
import team.covertdragon.springfestival.SpringFestivalConstants;

@GameRegistry.ObjectHolder(SpringFestivalConstants.MOD_ID)
public class DecorationRegistry {

    public static final Item FU = Items.AIR;

    public static final Item RED_HAT = Items.AIR;

    public static final Item RED_GOWN = Items.AIR;

    public static final Item RED_TROUSERS = Items.AIR;

    public static final Item RED_SHOES = Items.AIR;

    public static final Block FU_DOOR = Blocks.AIR;

    public static final Block LARGE_RED_PILLAR = Blocks.AIR;

    public static final Block LARGE_RED_PILLAR_ALT = Blocks.AIR;

}
