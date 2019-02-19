/*
 * Copyright (c) 2018 CovertDragon Team.
 * Copyright (c) 2018 Contributors of SpringFestival.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package team.covertdragon.springfestival.module.firecracker.hanging;

import java.util.Random;

import javax.annotation.Nullable;

import net.minecraft.block.Block;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@SuppressWarnings("deprecation")
public class BlockHangingFirecracker extends Block implements IItemProvider {
    
    protected static final AxisAlignedBB AABB = new AxisAlignedBB(0.125D, 0.0D, 0.125D, 0.875D, 1.0D, 0.875D);
    
    /**
     * 0 - unignited
     * 1 - waiting
     * 2 ~ 5 - ignited
     */
    public static final IntegerProperty COUNT = IntegerProperty.create("count", 0, 5);
    
    // TODO Can redstone signal affect this firecracker? No.

    public BlockHangingFirecracker(Properties proeprties) {
        super(proeprties);
        this.setDefaultState(this.getStateContainer().getBaseState().with(COUNT, 0));
    }
/*
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockReader source, BlockPos pos)
    {
        return AABB;
    }

    @Nullable
    @Override
    public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockReader worldIn, BlockPos pos) {
        return NULL_AABB;
    }*/
    
    @Override
    public boolean isFullCube(IBlockState state)
    {
        return false;
    }

    // TODO (3TUSK): We are going to use VoxelShape.
    /*public boolean isOpaqueCube(IBlockState state)
    {
        return false;
    }*/

    @Override
    public boolean onBlockActivated(IBlockState state, World world, BlockPos pos, EntityPlayer player, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ) {
        if (state.get(COUNT) > 0) {
            return super.onBlockActivated(state, world, pos,  player, hand, side, hitX, hitY, hitZ);
        }
        
        ItemStack itemstack = player.getHeldItem(hand);
        if (!itemstack.isEmpty() && (itemstack.getItem() == Items.FLINT_AND_STEEL ||
                itemstack.getItem() == Items.FIRE_CHARGE || itemstack.getItem() == Blocks.TORCH.asItem())) {
            this.ignite(world, pos, state, false, player);
            
            if (itemstack.getItem() == Items.FLINT_AND_STEEL) {
                itemstack.damageItem(1, player);
            }
            return true;
        } else {
            return super.onBlockActivated(state, world, pos, player, hand, side, hitX, hitY, hitZ);
        }
    }
    
    public void ignite(World world, BlockPos pos, IBlockState state, Boolean instant, EntityLivingBase igniter) {
        if (!world.isRemote) {
            world.setBlockState(pos, state.with(COUNT, 1), 3);
            if (instant) {
                TileEntity tileentity = world.getTileEntity(pos);
                if (tileentity instanceof TileHangingFirecracker) {
                    ((TileHangingFirecracker)tileentity).tick = 61;
                }
            }
        }
    }

    /* // TODO (3TUSK): Need to find replacement
    @Override
    public boolean canPlaceBlockAt(World worldIn, BlockPos pos)
    {
        return super.canPlaceBlockAt(worldIn, pos) && this.canBlockStay(worldIn, pos);
    }
    
    public boolean canBlockStay(World worldIn, BlockPos pos)
    {
        pos = pos.up();
        IBlockState state = worldIn.getBlockState(pos);
        Block block = state.getBlock();
        if (state.isSideSolid(worldIn, pos, EnumFacing.DOWN) || state.getBlockFaceShape(worldIn, pos, EnumFacing.DOWN) == BlockFaceShape.SOLID)
        {
            return block != Blocks.END_GATEWAY && !(block instanceof BlockPistonBase);
        }
        else if (block instanceof BlockHangingFirecracker)
        {
            return state.getValue(COUNT) == 0;
        }
        else
        {
            return block.isLeaves(state, worldIn, pos) || block instanceof BlockFence || block instanceof BlockGlass || block instanceof BlockWall || block instanceof BlockStainedGlass;
        }
    }*/
    
    @Override
    public boolean hasTileEntity(IBlockState state) {
        return state.get(COUNT) != 0;
    }

    @Override
    public TileEntity createTileEntity(IBlockState state, IBlockReader blockReader) {
        return new TileHangingFirecracker();
    }

    @Override
    public int quantityDropped(IBlockState state, Random random) {
        return state.get(COUNT) == 0 ? 1 : 0;
    }
/*  // TODO (3TUSK): Find proper replacement
    @Override
    public void breakBlock(World worldIn, BlockPos pos, IBlockState state)
    {
        TileEntity tileentity = worldIn.getTileEntity(pos);
        if (tileentity instanceof TileHangingFirecracker)
        {
            ((TileHangingFirecracker)tileentity).dropItems();
        }
        super.breakBlock(worldIn, pos, state);
    }*/
    
    @Override
    public void onExplosionDestroy(World worldIn, BlockPos pos, Explosion explosionIn) {
        if (!worldIn.isRemote) {
            this.ignite(worldIn, pos, worldIn.getBlockState(pos), false, explosionIn.getExplosivePlacedBy());
        }
    }

    @Override
    public void onEntityCollision(IBlockState state, World worldIn, BlockPos pos, Entity entityIn) {
        super.onEntityCollision(state, worldIn, pos, entityIn);
        if (!worldIn.isRemote && entityIn.isBurning()) {
            if (entityIn instanceof EntityArrow) {
                EntityArrow arrow = (EntityArrow)entityIn;
                this.ignite(worldIn, pos, state, false, worldIn.getServer().getPlayerList().getPlayerByUUID(arrow.shootingEntity));
            } else if (entityIn instanceof EntityLivingBase) {
                this.ignite(worldIn, pos, state, false, (EntityLivingBase)entityIn);
            }
        }
    }
    
    @Override
    public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
        /*if (!this.canBlockStay(worldIn, pos)) {
            worldIn.destroyBlock(pos, true);
        }*/ // TODO (3TUSK): Find replacement
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public BlockRenderLayer getRenderLayer() {
        return BlockRenderLayer.CUTOUT;
    }

    @Override
    public BlockFaceShape getBlockFaceShape(IBlockReader worldIn, IBlockState state, BlockPos pos, EnumFacing face) {
        return BlockFaceShape.UNDEFINED;
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, IBlockState> builder) {
        builder.add(COUNT);
    }
}
