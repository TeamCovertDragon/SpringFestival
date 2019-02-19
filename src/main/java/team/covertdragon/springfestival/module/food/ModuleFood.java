/*
 * Copyright (c) 2018 CovertDragon Team.
 * Copyright (c) 2018 Contributors of SpringFestival.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package team.covertdragon.springfestival.module.food;

import net.minecraft.init.MobEffects;
import net.minecraft.item.Item;
import net.minecraft.potion.PotionEffect;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import team.covertdragon.springfestival.SpringFestivalConstants;
import team.covertdragon.springfestival.module.AbstractSpringFestivalModule;
import team.covertdragon.springfestival.module.SpringFestivalModule;

@Mod.EventBusSubscriber(modid = SpringFestivalConstants.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
@SpringFestivalModule(name = "food", dependencies = {"material"})
public class ModuleFood extends AbstractSpringFestivalModule{

    // TODO What else do we eat during spring festival???

    @SubscribeEvent
    public static void onItemRegister(RegistryEvent.Register<Item> event) {
        event.getRegistry().registerAll(
                new ItemSpringFestivalFood(0, 1F, new Item.Properties().group(SpringFestivalConstants.SPRING_GROUP))
                        .setPotionEffect(new PotionEffect(MobEffects.SATURATION, 120), 0.06F)
                        .setAlwaysEdible()
                        .setRegistryName(SpringFestivalConstants.MOD_ID, "dumpling"),
                new ItemSpringFestivalFood(1, 5.0F, 88, new Item.Properties().group(SpringFestivalConstants.SPRING_GROUP))
                        .setRegistryName(SpringFestivalConstants.MOD_ID, "niangao"),
                new ItemSpringFestivalFood(2, 3.0F, 88, new Item.Properties().group(SpringFestivalConstants.SPRING_GROUP))
                        .setRegistryName(SpringFestivalConstants.MOD_ID, "fried_niangao"),
                new ItemSpringFestivalFood(1, 1.0F, new Item.Properties().group(SpringFestivalConstants.SPRING_GROUP))
                        .setRegistryName(SpringFestivalConstants.MOD_ID, "sunflower_seed"),
                new ItemSpringFestivalFood(6, 4.0F, 66, new Item.Properties().group(SpringFestivalConstants.SPRING_GROUP))
                        .setRegistryName(SpringFestivalConstants.MOD_ID, "tanghulu")
        );
    }

}
