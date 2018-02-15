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
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.event.ForgeEventFactory;
import team.covertdragon.springfestival.module.firecracker.FirecrackerRegistry;

public final class SpringFestivalUtil {

    private SpringFestivalUtil() {}

    public static void createNonDestructiveExplosion(World world, BlockPos pos, float power, EntityLivingBase source) {
        Explosion explosion = new Explosion(world, source, pos.getX(), pos.getY(), pos.getZ(), power, false, false);
        if (!ForgeEventFactory.onExplosionStart(world, explosion)) {
            explosion.doExplosionA();
            world.playSound((EntityPlayer)null, pos.getX(), pos.getY(), pos.getZ(), FirecrackerRegistry.soundFirecrackerExplode, SoundCategory.NEUTRAL, 4.0F, (1.0F + (world.rand.nextFloat() - world.rand.nextFloat()) * 0.2F) * 0.7F);
            world.spawnParticle(EnumParticleTypes.EXPLOSION_LARGE, pos.getX(), pos.getY(), pos.getZ(), 1.0D, 0.0D, 0.0D);
        }
        if(world instanceof WorldServer) {
            ((WorldServer) world).spawnParticle(EnumParticleTypes.EXPLOSION_NORMAL, true, pos.getX(), pos.getY(), pos.getZ(), 2, 0, 0, 0, 0d);
            ((WorldServer) world).spawnParticle(EnumParticleTypes.SMOKE_LARGE, true, pos.getX(), pos.getY(), pos.getZ(), 1, 0, 0, 0, 0d);
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
