/*
 * Copyright (c) 2018 CovertDragon Team.
 * Copyright (c) 2018 Contributors of SpringFestival.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package team.covertdragon.springfestival.module.material;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraftforge.registries.ObjectHolder;
import team.covertdragon.springfestival.SpringFestivalConstants;

@ObjectHolder(SpringFestivalConstants.MOD_ID)
public class MaterialRegistry {

    public static final Item RED_PAPER = Items.AIR;

    public static final Item RED_PAPER_BROKEN = Items.AIR;

    public static final Block GLUTINOUS_RICE_CROP = Blocks.AIR;
    
    public static final Item GLUTINOUS_RICE = Items.AIR;
    
    public static final Item GLUTINOUS_RICE_SEED = Items.AIR;
    
}