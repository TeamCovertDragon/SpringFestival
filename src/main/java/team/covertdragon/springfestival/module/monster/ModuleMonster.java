/*
 * Copyright (c) 2018 CovertDragon Team.
 * Copyright (c) 2018 Contributors of SpringFestival.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package team.covertdragon.springfestival.module.monster;

import net.minecraft.entity.monster.EntityMob;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import team.covertdragon.springfestival.SpringFestival;
import team.covertdragon.springfestival.module.AbstractSpringFestivalModule;
import team.covertdragon.springfestival.module.SpringFestivalModule;

@SpringFestivalModule(name = "monster", dependencies = {})
public class ModuleMonster extends AbstractSpringFestivalModule {

    @SubscribeEvent
    public void onLivingSpawn(LivingSpawnEvent event) {
        if (SpringFestival.proxy.isDuringSpringFestivalSeason() && event.getEntity() instanceof EntityMob) {
            event.setCanceled(true);
        }
    }
}
