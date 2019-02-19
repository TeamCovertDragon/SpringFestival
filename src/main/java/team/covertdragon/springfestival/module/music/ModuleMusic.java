/*
 * Copyright (c) 2018 CovertDragon Team.
 * Copyright (c) 2018 Contributors of SpringFestival.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package team.covertdragon.springfestival.module.music;

import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import team.covertdragon.springfestival.SpringFestivalConstants;
import team.covertdragon.springfestival.module.AbstractSpringFestivalModule;
import team.covertdragon.springfestival.module.SpringFestivalModule;

@Mod.EventBusSubscriber(modid = SpringFestivalConstants.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
@SpringFestivalModule(name = "music")
public class ModuleMusic extends AbstractSpringFestivalModule {

    private static SoundEvent SPRING_FESTIVAL_TRACK_01;

    @SubscribeEvent
    public static void onItemRegistry(RegistryEvent.Register<Item> event) {
        if (SPRING_FESTIVAL_TRACK_01 == null) {
            SPRING_FESTIVAL_TRACK_01 = new SoundEvent(new ResourceLocation(SpringFestivalConstants.MOD_ID, "track.01"));
        }

        /*
         * To any music composers who are looking at this, or heard about these comments:
         * we do need one track of music with theme of Chinese Lunar New Year. If you are interested
         * in providing help on this, feel free to drop issue ticket or pull request to our repository.
         * Please also leave your e-mail if you do so (for credit purpose - we need to properly
         * attribute author in respective commit(s)).
         */

        event.getRegistry().register(
                new ItemSpringFestivalMusicRecord(88, SPRING_FESTIVAL_TRACK_01, new Item.Properties().group(SpringFestivalConstants.SPRING_GROUP))
                        .setRegistryName(SpringFestivalConstants.MOD_ID, "track_01")
        );
    }

    @SubscribeEvent
    public static void onSoundEventRegistry(RegistryEvent.Register<SoundEvent> event) {
        if (SPRING_FESTIVAL_TRACK_01 == null) {
            return;
        }
        event.getRegistry().register(SPRING_FESTIVAL_TRACK_01.setRegistryName(SpringFestivalConstants.MOD_ID, "track_01"));
    }

}
