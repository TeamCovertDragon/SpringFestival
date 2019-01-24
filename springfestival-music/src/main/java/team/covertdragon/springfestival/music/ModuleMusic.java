/*
 * Copyright (c) 2018 CovertDragon Team.
 * Copyright (c) 2018 Contributors of SpringFestival.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package team.covertdragon.springfestival.music;

import net.minecraft.util.SoundEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import team.covertdragon.springfestival.module.AbstractSpringFestivalModule;

public class ModuleMusic extends AbstractSpringFestivalModule {

    // TODO sound.json, actual music & their discs


    @SubscribeEvent
    public void onSoundEventRegistry(RegistryEvent.Register<SoundEvent> event) {

    }
}
