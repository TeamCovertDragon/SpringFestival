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
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import team.covertdragon.springfestival.internal.SpringFestivalUtil;

@SuppressWarnings("deprecation")
public class BlockHangingFirecracker extends Block {
    
    protected static final AxisAlignedBB AABB = new AxisAlignedBB(0.125D, 0.0D, 0.125D, 0.875D, 1.0D, 0.875D);

    // TODO Can redstone signal affect this firecracker?

    public BlockHangingFirecracker() {
        super(Material.TNT, MapColor.RED);
    }

    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
    {
        return AABB;
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
    public boolean isPassable(IBlockAccess worldIn, BlockPos pos)
    {
        return true;
    }
    
    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
        ItemStack itemstack = player.getHeldItem(hand);
        
        if (!itemstack.isEmpty() && (
                itemstack.getItem() == Items.FLINT_AND_STEEL
                ||
                itemstack.getItem() == Items.FIRE_CHARGE
                ||
                itemstack.getItem() == Item.getItemFromBlock(Blocks.TORCH)
                )
            )
        {
            this.explode(world, pos, player);
            
            if (itemstack.getItem() == Items.FLINT_AND_STEEL)
            {
                itemstack.damageItem(1, player);
            }
            return true;
        }
        else
        {
            return super.onBlockActivated(world, pos, state, player, hand, facing, hitX, hitY, hitZ);
        }
    }
    
    public void explode(World worldIn, BlockPos pos, EntityLivingBase igniter)
    {
        if (!worldIn.isRemote)
        {
            SpringFestivalUtil.createNonDestructiveExplosion(worldIn, pos, 3.0F, igniter);
            worldIn.setBlockState(pos, Blocks.AIR.getDefaultState(), 11);
        }
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
            return block.isLeaves(state, worldIn, pos) || block instanceof BlockHangingFirecracker || block instanceof BlockFence || block instanceof BlockGlass || block instanceof BlockWall || block instanceof BlockStainedGlass;
        }
    }

    @Override
    public int tickRate(World world) {
        return 5;
    }

    @Override
    public void onBlockDestroyedByExplosion(World worldIn, BlockPos pos, Explosion explosionIn) {
        if (!worldIn.isRemote) {
            this.explode(worldIn, pos, explosionIn.getExplosivePlacedBy());
        }
    }

    @Override
    public void onEntityCollidedWithBlock(World worldIn, BlockPos pos, IBlockState state, Entity entityIn)
    {
        if (!worldIn.isRemote && entityIn.isBurning())
        {
            if (entityIn instanceof EntityArrow)
            {
                EntityArrow entityarrow = (EntityArrow)entityIn;
                this.explode(worldIn, pos, entityarrow.shootingEntity instanceof EntityLivingBase ? (EntityLivingBase)entityarrow.shootingEntity : null);
            }
            else if (entityIn instanceof EntityLivingBase)
            {
                this.explode(worldIn, pos, (EntityLivingBase)entityIn);
            }
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

    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getBlockLayer()
    {
        return BlockRenderLayer.CUTOUT;
    }
    
    public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face)
    {
        return BlockFaceShape.UNDEFINED;
    }
}
