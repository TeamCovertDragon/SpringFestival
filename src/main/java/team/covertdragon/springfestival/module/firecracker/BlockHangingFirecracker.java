/*
 * Copyright (c) 2018 CovertDragon Team.
 * Copyright (c) 2018 Contributors of SpringFestival.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package team.covertdragon.springfestival.module.firecracker;

import javax.annotation.Nullable;

import net.minecraft.block.Block;
import net.minecraft.block.BlockFence;
import net.minecraft.block.BlockGlass;
import net.minecraft.block.BlockStainedGlass;
import net.minecraft.block.BlockWall;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import team.covertdragon.springfestival.internal.SpringFestivalUtil;

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
    public boolean isFullCube(IBlockState state)
    {
        return false;
    }

    @Override
    public boolean isOpaqueCube(IBlockState state)
    {
        return false;
    }
    
    @Override
    public boolean canPlaceBlockAt(World worldIn, BlockPos pos)
    {
        return super.canPlaceBlockAt(worldIn, pos) ? this.canBlockStay(worldIn, pos) : false;
    }
    
    public boolean canBlockStay(World worldIn, BlockPos pos)
    {
        pos = pos.up();
        IBlockState state = worldIn.getBlockState(pos);
        Block block = state.getBlock();
        if (state.isSideSolid(worldIn, pos, EnumFacing.DOWN) || state.getBlockFaceShape(worldIn, pos, EnumFacing.DOWN) == BlockFaceShape.SOLID)
        {
            return block != Blocks.END_GATEWAY;
        }
        else
        {
            return block instanceof BlockHangingFirecracker || block instanceof BlockFence || block instanceof BlockGlass || block instanceof BlockWall || block instanceof BlockStainedGlass;
        }
    }

    @Override
    public int tickRate(World world) {
        return 5;
    }

    @Override
    public void onBlockDestroyedByExplosion(World worldIn, BlockPos pos, Explosion explosionIn) {
        if (!worldIn.isRemote) {
            SpringFestivalUtil.createNonDestructiveExplosion(worldIn, pos, 3.0F);
        }
    }

    @Override
    public void onEntityCollidedWithBlock(World worldIn, BlockPos pos, IBlockState state, Entity entityIn) {
        if (!worldIn.isRemote && entityIn.isBurning()) {
            SpringFestivalUtil.createNonDestructiveExplosion(worldIn, pos, 3.0F, entityIn);
        }
    }
    
    @Override
    public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos)
    {
        if (!this.canBlockStay(worldIn, pos))
        {
            worldIn.destroyBlock(pos, true);
        }
    }

}
