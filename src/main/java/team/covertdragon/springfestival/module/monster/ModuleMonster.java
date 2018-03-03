/*
 * Copyright (c) 2018 CovertDragon Team.
 * Copyright (c) 2018 Contributors of SpringFestival.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package team.covertdragon.springfestival.module.monster;

import team.covertdragon.springfestival.module.AbstractSpringFestivalModule;
import team.covertdragon.springfestival.module.SpringFestivalModule;

@SpringFestivalModule(name = "monster")
public class ModuleMonster extends AbstractSpringFestivalModule {

    /*TODO: fix this thing
    @SubscribeEvent
    public void onLivingSpawn(EntityJoinWorldEvent event) {
        if (SpringFestival.proxy.isDuringSpringFestivalSeason() && event.getEntity() instanceof IMob) {
            event.setCanceled(true);
        }
    }
    */

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
