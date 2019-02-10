/*
 * Copyright (c) 2018 CovertDragon Team.
 * Copyright (c) 2018 Contributors of SpringFestival.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package team.covertdragon.springfestival.module.monster;

import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.monster.IMob;
import net.minecraft.init.Biomes;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.fml.common.registry.EntityEntryBuilder;
import team.covertdragon.springfestival.SpringFestivalConstants;
import team.covertdragon.springfestival.internal.time.SpringFestivalTimeChecker;
import team.covertdragon.springfestival.module.AbstractSpringFestivalModule;
import team.covertdragon.springfestival.module.SpringFestivalModule;

@SpringFestivalModule(name = "monster")
public final class ModuleMonster extends AbstractSpringFestivalModule {

    @SubscribeEvent
    public void onLivingSpawn(LivingSpawnEvent.SpecialSpawn event) {
        // Remove any entity that is classified as monster when it's spring festival season
        if (SpringFestivalTimeChecker.INSTANCE.isDuringSpringFestivalSeason()) {
            // except the monster Nian.
            if (event.getEntityLiving() instanceof IMob && event.getEntityLiving().getClass() != Nian.class) {
                event.setCanceled(true);
            }
        }
    }

    @SubscribeEvent
    public void onEntityRegister(RegistryEvent.Register<EntityEntry> event) {
        event.getRegistry().register(EntityEntryBuilder.create()
                .entity(Nian.class)
                .id(new ResourceLocation(SpringFestivalConstants.MOD_ID, "nian"), 1) // 0 is for Firecracker from Firecracker module.
                .name("nian")
                .egg(0xCC1122, 0xCC66666)
                .spawn(EnumCreatureType.MONSTER, 10, 1, 4, Biomes.DESERT, Biomes.ICE_PLAINS, Biomes.PLAINS, Biomes.SAVANNA)
                .tracker(80, 5, true)
                .build()
        );
    }
}
