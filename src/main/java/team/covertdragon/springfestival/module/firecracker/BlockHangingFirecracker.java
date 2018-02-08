/*
 * Copyright (c) 2018 CovertDragon Team.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package team.covertdragon.springfestival.module.firecracker;

import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.event.ForgeEventFactory;

import javax.annotation.Nullable;

@SuppressWarnings("deprecation")
public class BlockHangingFirecracker extends Block {

    // TODO Can redstone signal affect this firecracker?

    public BlockHangingFirecracker() {
        super(Material.TNT, MapColor.RED);
    }

    @Nullable
    @Override
    public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos) {
        return NULL_AABB;
    }

    @Override
    public boolean canPlaceBlockOnSide(World worldIn, BlockPos pos, EnumFacing side) {
        return side == EnumFacing.DOWN;
    }

    @Override
    public int tickRate(World world) {
        return 5;
    }

    @Override
    public void onBlockDestroyedByExplosion(World worldIn, BlockPos pos, Explosion explosionIn) {
        if (!worldIn.isRemote) {
            // TODO Duplicated code, separate out into a single method
            Explosion explosion = new Explosion(worldIn, explosionIn.getExplosivePlacedBy(), pos.getX(), pos.getY(), pos.getZ(), 3.0F, true, false);
            // TODO Where is my sound effect
            if (ForgeEventFactory.onExplosionStart(worldIn, explosion)) {
                explosion.doExplosionA();
                explosion.doExplosionB(true);
            }
        }
    }

    @Override
    public void onEntityCollidedWithBlock(World worldIn, BlockPos pos, IBlockState state, Entity entityIn) {
        if (!worldIn.isRemote && entityIn.isBurning()) {
            Explosion explosion = new Explosion(worldIn, entityIn, pos.getX(), pos.getY(), pos.getZ(), 3.0F, true, false);
            // TODO Where is my sound effect
            if (ForgeEventFactory.onExplosionStart(worldIn, explosion)) {
                explosion.doExplosionA();
                explosion.doExplosionB(true);
            }
        }
    }
}
