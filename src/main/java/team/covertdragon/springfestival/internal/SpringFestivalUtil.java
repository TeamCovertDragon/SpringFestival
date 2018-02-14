/*
 * Copyright (c) 2018 CovertDragon Team.
 * Copyright (c) 2018 Contributors of SpringFestival.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package team.covertdragon.springfestival.internal;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import net.minecraftforge.event.ForgeEventFactory;

import java.util.Collections;

public final class SpringFestivalUtil {

    private SpringFestivalUtil() {}

    public static void createNonDestructiveExplosion(World world, BlockPos pos, float power, EntityLivingBase source) {
        Explosion explosion = new Explosion(world, source, pos.getX(), pos.getY(), pos.getZ(), power, true, false, Collections.emptyList());
        // TODO Where is my sound effect
        if (ForgeEventFactory.onExplosionStart(world, explosion)) {
            explosion.doExplosionA();
            explosion.doExplosionB(true);
        }
    }

    public static void createNonDestructiveExplosion(World world, BlockPos pos, float power) {
        createNonDestructiveExplosion(world, pos, power, null);
    }

    /**
     * Calculates the damage should be taken.
     * @param maxDamage The max damage of an item.
     * @return The damage should be taken.
     */
    public static int getDamage(int maxDamage){
        // TODO: return the damage should be taken as time goes on
        return 0;
    }
}
