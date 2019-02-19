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
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraft.item.ItemSeeds;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import team.covertdragon.springfestival.SpringFestivalConstants;
import team.covertdragon.springfestival.module.AbstractSpringFestivalModule;
import team.covertdragon.springfestival.module.SpringFestivalModule;

/**
 * A module that holds shared materials, for other modules to use.
 */
@Mod.EventBusSubscriber(modid = SpringFestivalConstants.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
@SpringFestivalModule(name = "material")
public class ModuleMaterial extends AbstractSpringFestivalModule {

    @SubscribeEvent
    public static void onItemRegister(RegistryEvent.Register<Item> event) {
        event.getRegistry().registerAll(
                new ItemSeeds(MaterialRegistry.GLUTINOUS_RICE_CROP, new Item.Properties().group(SpringFestivalConstants.SPRING_GROUP))
                        .setRegistryName(SpringFestivalConstants.MOD_ID, "glutinous_rice_seed"),
                new Item(new Item.Properties().group(SpringFestivalConstants.SPRING_GROUP))
                        .setRegistryName(SpringFestivalConstants.MOD_ID, "glutinous_rice"),
                new Item(new Item.Properties().group(SpringFestivalConstants.SPRING_GROUP))
                        .setRegistryName(SpringFestivalConstants.MOD_ID, "red_paper"),
                new Item(new Item.Properties().group(SpringFestivalConstants.SPRING_GROUP))
                        .setRegistryName(SpringFestivalConstants.MOD_ID, "red_paper_broken")
        );
    }

    @SubscribeEvent
    public static void onBlockRegister(RegistryEvent.Register<Block> event) {
        event.getRegistry().register(new BlockGlutinousRice(Block.Properties.create(Material.PLANTS)
                .doesNotBlockMovement()
                .needsRandomTick()
                .hardnessAndResistance(0F)
                .sound(SoundType.PLANT))
                .setRegistryName(SpringFestivalConstants.MOD_ID, "glutinous_rice_crop"));
    }
}
