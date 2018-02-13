/*
 * Copyright (c) 2018 CovertDragon Team.
 * Copyright (c) 2018 Contributors of SpringFestival.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package team.covertdragon.springfestival.module.decoration;

import net.minecraft.item.ItemArmor;
import net.minecraftforge.fml.common.registry.GameRegistry;
import team.covertdragon.springfestival.SpringFestivalConstants;
import team.covertdragon.springfestival.module.decoration.fudoor.BlockFuDoor;
import team.covertdragon.springfestival.module.decoration.fudoor.ItemFuDoor;

@GameRegistry.ObjectHolder(SpringFestivalConstants.MOD_ID)
public class DecorationRegistry {
    @GameRegistry.ObjectHolder("red_hat")
    public static final ItemArmor red_hat = null;

    @GameRegistry.ObjectHolder("red_gown")
    public static final ItemArmor red_gown = null;

    @GameRegistry.ObjectHolder("red_trousers")
    public static final ItemArmor red_trousers = null;

    @GameRegistry.ObjectHolder("red_shoes")
    public static final ItemArmor red_shoes = null;

    @GameRegistry.ObjectHolder("fu_door")
    public static final BlockFuDoor blockFuDoor = null;

    @GameRegistry.ObjectHolder("fu_door")
    public static final ItemFuDoor itemFuDoor = null;
}
