/*
 * Copyright (c) 2018 CovertDragon Team.
 * Copyright (c) 2018 Contributors of SpringFestival.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package team.covertdragon.springfestival.monster;

import net.minecraft.entity.monster.IMob;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import team.covertdragon.springfestival.SpringFestival;
import team.covertdragon.springfestival.module.AbstractSpringFestivalModule;
import team.covertdragon.springfestival.module.SpringFestivalModule;

@SpringFestivalModule(name = "monster")
public class ModuleMonster extends AbstractSpringFestivalModule {

    @SubscribeEvent
    public void onLivingSpawn(LivingSpawnEvent event) {
        // Remove any entity that is classified as monster
        if (SpringFestival.proxy.isDuringSpringFestivalSeason()) {
            if (event.getEntityLiving() instanceof IMob) {
                // Remove the entity being ridden. This is for situations like Chicken Jockey and Skeleton Horseman.
                if (event.getEntityLiving().isRiding() && event.getEntityLiving().getRidingEntity() != null) {
                    event.getEntityLiving().dismountRidingEntity();
                    event.getEntityLiving().getRidingEntity().setDead();
                }
                event.setCanceled(true);
            }
        }
    }

    /*
    @SubscribeEvent
    public void onEntityRegister(RegistryEvent.Register<EntityEntry> event) {
        event.getRegistry().register(EntityEntryBuilder.<EntityNian>create()
                .entity(EntityNian.class)
                .id(new ResourceLocation(SpringFestivalConstants.MOD_ID, "nian"),0)
                .name("nian")
                .egg(0xCC1122, 0xCC66666)
                .build()
        );
    }*/
}
