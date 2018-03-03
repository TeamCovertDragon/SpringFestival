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
import net.minecraft.item.Item;
import net.minecraft.item.ItemSeeds;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import team.covertdragon.springfestival.SpringFestivalConstants;
import team.covertdragon.springfestival.internal.model.ModelUtil;
import team.covertdragon.springfestival.module.AbstractSpringFestivalModule;
import team.covertdragon.springfestival.module.SpringFestivalModule;

/**
 * A module that holds shared materials, for other modules to use.
 */
@SpringFestivalModule(name = "material")
public class ModuleMaterial extends AbstractSpringFestivalModule {

    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public void onModelRegister(ModelRegistryEvent event) {
        ModelUtil.mapItemModel(MaterialRegistry.itemRedPaper);
        ModelUtil.mapItemModel(MaterialRegistry.itemRedPaperBroken);
    }

    @SubscribeEvent
    public void onItemRegister(RegistryEvent.Register<Item> event) {
        event.getRegistry().registerAll(
                new ItemSeeds(MaterialRegistry.GLUTINOUS_RICE_CROP, Blocks.DIRT)
                        .setCreativeTab(SpringFestivalConstants.CREATIVE_TAB)
                        .setUnlocalizedName(SpringFestivalConstants.MOD_ID + ".glutinous_rice_seed")
                        .setRegistryName(SpringFestivalConstants.MOD_ID, "glutinous_rice_seed"),
                new Item()
                        .setCreativeTab(SpringFestivalConstants.CREATIVE_TAB)
                        .setUnlocalizedName(SpringFestivalConstants.MOD_ID + ".glutinous_rice")
                        .setRegistryName(SpringFestivalConstants.MOD_ID, "glutinous_rice"),
                new Item()
                        .setCreativeTab(SpringFestivalConstants.CREATIVE_TAB)
                        .setUnlocalizedName(SpringFestivalConstants.MOD_ID + ".red_paper")
                        .setRegistryName(SpringFestivalConstants.MOD_ID, "red_paper"),
                new Item()
                        .setCreativeTab(SpringFestivalConstants.CREATIVE_TAB)
                        .setUnlocalizedName(SpringFestivalConstants.MOD_ID + ".red_paper_broken")
                        .setRegistryName(SpringFestivalConstants.MOD_ID, "red_paper_broken")
        );
    }

    @SubscribeEvent
    public void onBlockRegister(RegistryEvent.Register<Block> event) {
        event.getRegistry().register(new BlockGlutinousRice().setRegistryName(SpringFestivalConstants.MOD_ID, "glutinous_rice_crop"));
    }
}
