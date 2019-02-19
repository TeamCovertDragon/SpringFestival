/*
 * Copyright (c) 2018 CovertDragon Team.
 * Copyright (c) 2018 Contributors of SpringFestival.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package team.covertdragon.springfestival.module.firecracker;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.registries.ObjectHolder;
import team.covertdragon.springfestival.SpringFestivalConstants;

@ObjectHolder(SpringFestivalConstants.MOD_ID)
public class FirecrackerRegistry {

    public static final Block FIREWORK_BOX = Blocks.AIR;

    public static final Block HANGING_FIRECRACKER = Blocks.AIR;

    public static final Item FIRECRACKER_EGG = Items.AIR;

    public static final SoundEvent FIRECRACKER_THROW = SoundEvents.ENTITY_EGG_THROW;
    
    public static final SoundEvent FIRECRACKER_EXPLODE = SoundEvents.ENTITY_GENERIC_EXPLODE;
}
