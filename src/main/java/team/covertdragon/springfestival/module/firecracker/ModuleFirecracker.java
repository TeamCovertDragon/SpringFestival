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
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import team.covertdragon.springfestival.SpringFestivalConstants;
import team.covertdragon.springfestival.internal.model.ModelUtil;
import team.covertdragon.springfestival.module.AbstractSpringFestivalModule;
import team.covertdragon.springfestival.module.SpringFestivalModule;
import team.covertdragon.springfestival.module.firecracker.firework.BlockFireworkBox;
import team.covertdragon.springfestival.module.firecracker.firework.ItemFireworkBox;
import team.covertdragon.springfestival.module.firecracker.firework.TileFireworkBox;

@SpringFestivalModule(name = "firecracker", dependencies = {"material"})
public class ModuleFirecracker extends AbstractSpringFestivalModule {
    @SubscribeEvent
    public void onBlockRegister(RegistryEvent.Register<Block> event) {
        GameRegistry.registerTileEntity(TileFireworkBox.class, "tile_firework_box");

        event.getRegistry().registerAll(
                new BlockHangingFirecracker()
                        .setCreativeTab(SpringFestivalConstants.CREATIVE_TAB)
                        .setUnlocalizedName(SpringFestivalConstants.MOD_ID + ".hanging_firecracker")
                        .setRegistryName(SpringFestivalConstants.MOD_ID, "hanging_firecracker"),
                new BlockFireworkBox()
        );
    }

    @SubscribeEvent
    public void onItemRegister(RegistryEvent.Register<Item> event) {
        event.getRegistry().registerAll(
                new ItemBlock(FirecrackerRegistry.blockHangingFireCracker)
                        .setRegistryName(SpringFestivalConstants.MOD_ID, "hanging_firecracker"),
                new ItemFireworkBox()
        );
    }

    @SubscribeEvent
    public void onModelRegister(ModelRegistryEvent event) {
        ModelUtil.mapItemModel(FirecrackerRegistry.itemFireWorkBox);
        ModelUtil.mapItemModel(FirecrackerRegistry.itemHangingFirecracker);
    }
}
