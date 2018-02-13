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
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import team.covertdragon.springfestival.SpringFestival;
import team.covertdragon.springfestival.SpringFestivalConstants;
import team.covertdragon.springfestival.internal.model.ModelUtil;
import team.covertdragon.springfestival.module.AbstractSpringFestivalModule;
import team.covertdragon.springfestival.module.SpringFestivalModule;

@SpringFestivalModule(name = "firecracker", dependencies = {"material"})
public class ModuleFirecracker extends AbstractSpringFestivalModule {

    private static final Block BLOCK_HANGING_FIRECRACKER = new BlockHangingFirecracker()
            .setCreativeTab(SpringFestivalConstants.CREATIVE_TAB)
            .setUnlocalizedName(SpringFestivalConstants.MOD_ID + ".hanging_firecracker")
            .setRegistryName(SpringFestivalConstants.MOD_ID, "hanging_firecracker");

    private static final Item ITEM_HANGING_FIRECRACKER = new ItemBlock(BLOCK_HANGING_FIRECRACKER)
            .setRegistryName(SpringFestivalConstants.MOD_ID, "hanging_firecracker");

    public void onInit() {
        EntityRegistry.registerModEntity(new ResourceLocation(SpringFestivalConstants.MOD_ID, "firecracker"), EntityFirecracker.class, "Firecracker", 0, SpringFestival.getInstance(), 80, 3, true);
    }
    
    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public void onModelRegister(ModelRegistryEvent event) {
        ModelUtil.mapItemModel(ITEM_HANGING_FIRECRACKER);
//        RenderingRegistry.loadEntityRenderers(manager, renderMap);
    }
    
    @SubscribeEvent
    public void onBlockRegister(RegistryEvent.Register<Block> event) {
        
        event.getRegistry().register(BLOCK_HANGING_FIRECRACKER);
    }

    @SubscribeEvent
    public void onItemRegister(RegistryEvent.Register<Item> event) {
        event.getRegistry().register(ITEM_HANGING_FIRECRACKER);
    }
}
