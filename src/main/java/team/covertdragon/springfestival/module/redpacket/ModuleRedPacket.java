/*
 * Copyright (c) 2018 CovertDragon Team.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package team.covertdragon.springfestival.module.redpacket;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import team.covertdragon.springfestival.SpringFestivalConstants;
import team.covertdragon.springfestival.module.AbstractSpringFestivalModule;

@Mod.EventBusSubscriber(modid = SpringFestivalConstants.MOD_ID)
public class ModuleRedPacket extends AbstractSpringFestivalModule {

    public static final Item RED_PACKET = new ItemRedPacket()
            .setCreativeTab(SpringFestivalConstants.CREATIVE_TAB)
            .setUnlocalizedName("springfestival.redpacket")
            .setRegistryName("springfestival:redpacket");

    public static final int GUI_RED_PACKET = 0;

    @SubscribeEvent
    public static void onItemRegister(RegistryEvent.Register<Item> event) {
        event.getRegistry().register(
                RED_PACKET
        );
    }

    @SubscribeEvent
    public static void onModelRegister(ModelRegistryEvent event) {
        ModelLoader.setCustomModelResourceLocation(RED_PACKET, 0, new ModelResourceLocation(RED_PACKET.getRegistryName().toString(), "inventory"));
    }

    @Override
    public void onInit() {

    }

    @Override
    public void registryModels() {

    }
}
