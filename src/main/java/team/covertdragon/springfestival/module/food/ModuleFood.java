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
import team.covertdragon.springfestival.module.AbstractSpringFestivalModule;

@Mod.EventBusSubscriber(modid = "springfestival")
public class ModuleFood extends AbstractSpringFestivalModule{
    public static final Item dumpling = new ItemSpringFestivalFood(2, 1.0F)
            .setUnlocalizedName("springfestival.dumpling")
            .setRegistryName("springfestival:dumpling");

    public static final Item niangao = new ItemSpringFestivalFood(2, 1.0F)
            .setUnlocalizedName("springfestival.niangao")
            .setRegistryName("springffestival:niangao");


    @SubscribeEvent
    public static void onItemRegister(RegistryEvent.Register<Item> event) {
        event.getRegistry().registerAll(
                dumpling,
                niangao
        );
    }

}
