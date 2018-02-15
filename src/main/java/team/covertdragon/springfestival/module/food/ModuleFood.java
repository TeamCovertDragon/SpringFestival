/*
 * Copyright (c) 2018 CovertDragon Team.
 * Copyright (c) 2018 Contributors of SpringFestival.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package team.covertdragon.springfestival.module.food;

import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import team.covertdragon.springfestival.SpringFestivalConstants;
import team.covertdragon.springfestival.internal.model.ModelUtil;
import team.covertdragon.springfestival.module.AbstractSpringFestivalModule;
import team.covertdragon.springfestival.module.SpringFestivalModule;

@SpringFestivalModule(name = "food", dependencies = {"material"})
public class ModuleFood extends AbstractSpringFestivalModule{
    private static final Item DUMPLING = new ItemSpringFestivalFood(3, 1.5F)
            .setUnlocalizedName(SpringFestivalConstants.MOD_ID + ".dumpling")
            .setRegistryName(SpringFestivalConstants.MOD_ID, "dumpling");

    private static final Item NIANGAO = new ItemSpringFestivalFood(2, 3.0F)
            .setUnlocalizedName(SpringFestivalConstants.MOD_ID + ".niangao")
            .setRegistryName(SpringFestivalConstants.MOD_ID, "niangao");

    // TODO What else do we eat during spring festival???

    @SubscribeEvent
    public void onItemRegister(RegistryEvent.Register<Item> event) {
        event.getRegistry().registerAll(
                DUMPLING,
                NIANGAO
        );
    }

    @SubscribeEvent
    public void onModelRegister(ModelRegistryEvent event) {
        ModelUtil.mapItemModel(DUMPLING);
        ModelUtil.mapItemModel(NIANGAO);
    }

}
