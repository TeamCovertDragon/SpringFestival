/*
 * Copyright (c) 2018 CovertDragon Team.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package team.covertdragon.springfestival.module.food;

import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber(modid = "springfestival")
public class ModuleFood {

    @SubscribeEvent
    public static void onItemRegister(RegistryEvent.Register<Item> event) {
        event.getRegistry().registerAll(
                new ItemSpringFestivalFood(2, 1.0F).setUnlocalizedName("springfestival.dumpling").setRegistryName("springfestival:dumpling"),
                new ItemSpringFestivalFood(2, 1.0F).setUnlocalizedName("springfestival.niangao").setRegistryName("niangao")
        );
    }
}
