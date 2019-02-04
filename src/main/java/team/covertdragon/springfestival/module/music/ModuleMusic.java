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
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import team.covertdragon.springfestival.SpringFestivalConstants;
import team.covertdragon.springfestival.module.AbstractSpringFestivalModule;
import team.covertdragon.springfestival.module.SpringFestivalModule;

@SpringFestivalModule(name = "music")
public class ModuleMusic extends AbstractSpringFestivalModule {

    @GameRegistry.ObjectHolder("springfestival:track_01")
    public static SoundEvent SPRING_FESTIVAL_TRACK_01;

    @SubscribeEvent
    public void onItemRegistry(RegistryEvent.Register<Item> event) {
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
                new ItemSpringFestivalMusicRecord("track.01", SPRING_FESTIVAL_TRACK_01)
                        .setCreativeTab(SpringFestivalConstants.CREATIVE_TAB)
                        .setRegistryName(SpringFestivalConstants.MOD_ID, "track_01")
        );
    }

    @SubscribeEvent
    public void onSoundEventRegistry(RegistryEvent.Register<SoundEvent> event) {
        if (SPRING_FESTIVAL_TRACK_01 == null) {
            return;
        }
        event.getRegistry().register(SPRING_FESTIVAL_TRACK_01.setRegistryName(SpringFestivalConstants.MOD_ID, "track_01"));
    }
}
