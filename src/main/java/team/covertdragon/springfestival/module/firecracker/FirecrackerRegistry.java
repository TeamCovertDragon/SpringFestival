/*
 * Copyright (c) 2018 CovertDragon Team.
 * Copyright (c) 2018 Contributors of SpringFestival.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package team.covertdragon.springfestival.module.firecracker;

import net.minecraft.item.ItemBlock;
import net.minecraftforge.fml.common.registry.GameRegistry;
import team.covertdragon.springfestival.SpringFestivalConstants;
import team.covertdragon.springfestival.module.firecracker.entity.ItemFirecrackerEgg;
import team.covertdragon.springfestival.module.firecracker.firework.BlockFireworkBox;
import team.covertdragon.springfestival.module.firecracker.firework.ItemFireworkBox;
import team.covertdragon.springfestival.module.firecracker.hanging.BlockHangingFirecracker;

@GameRegistry.ObjectHolder(SpringFestivalConstants.MOD_ID)
public class FirecrackerRegistry {

    static {
        blockFirework = null;
        blockHangingFireCracker = null;
        itemFireWorkBox = null;
        itemHangingFirecracker = null;
        itemFirecrackerEgg = null;
    }

    @GameRegistry.ObjectHolder("firework_box")
    public static final BlockFireworkBox blockFirework;

    @GameRegistry.ObjectHolder("hanging_firecracker")
    public static final BlockHangingFirecracker blockHangingFireCracker;

    @GameRegistry.ObjectHolder("firework_box")
    public static final ItemFireworkBox itemFireWorkBox;

    @GameRegistry.ObjectHolder("hanging_firecracker")
    public static final ItemBlock itemHangingFirecracker;
    
    @GameRegistry.ObjectHolder("firecracker_egg")
    public static final ItemFirecrackerEgg itemFirecrackerEgg;
}
