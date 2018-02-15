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
import net.minecraft.block.BlockFence;
import net.minecraft.block.BlockGlass;
import net.minecraft.block.BlockPistonBase;
import net.minecraft.block.BlockStainedGlass;
import net.minecraft.block.BlockWall;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
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
import team.covertdragon.springfestival.SpringFestivalConstants;
import team.covertdragon.springfestival.internal.SpringFestivalUtil;
import team.covertdragon.springfestival.module.firecracker.FirecrackerRegistry;
import team.covertdragon.springfestival.module.material.MaterialRegistry;

@SuppressWarnings("deprecation")
public class BlockHangingFirecracker extends Block {
    
    protected static final AxisAlignedBB AABB = new AxisAlignedBB(0.125D, 0.0D, 0.125D, 0.875D, 1.0D, 0.875D);
    
    /**
     * 0 - unignited
     * 1 ~ 4 - waiting
     * 5 ~ 10 - ignited
     */
    public static final PropertyInteger COUNT = PropertyInteger.create("count", 0, 10);
    
    // TODO Can redstone signal affect this firecracker? No.

    public BlockHangingFirecracker() {
        super(Material.CACTUS, MapColor.RED);
        this.setCreativeTab(SpringFestivalConstants.CREATIVE_TAB);
        this.setUnlocalizedName(SpringFestivalConstants.MOD_ID + ".hanging_firecracker");
        this.setRegistryName(SpringFestivalConstants.MOD_ID, "hanging_firecracker");
        this.setDefaultState(this.blockState.getBaseState().withProperty(COUNT, 0));
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
            this.ignite(world, pos, player);
            
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
    
    public void ignite(World worldIn, BlockPos pos, EntityLivingBase igniter)
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
    public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand)
    {
        int count = state.getValue(COUNT);
        if (count < 10)
        {
            state.withProperty(COUNT, count + 1);
        }
        else
        {
            // TODO
        }
    }
    
    @Override
    public boolean hasTileEntity(IBlockState state) {
        return state.getValue(COUNT) != 0;
    }

    @Override
    public TileEntity createTileEntity(World world, IBlockState state) {
        return new TileHangingFirecracker();
    }

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune)
    {
        return state.getValue(COUNT) < 5 ? FirecrackerRegistry.itemHangingFirecracker : MaterialRegistry.itemRedPaperBroken;
    }
    
    @Override
    public int quantityDropped(IBlockState state, int fortune, Random random)
    {
        int count = state.getValue(COUNT);
        if (count < 5)
        {
            return 1;
        }
        else
        {
            return 10 - count; 
        }
    }
    
    @Override
    public void onBlockDestroyedByExplosion(World worldIn, BlockPos pos, Explosion explosionIn) {
        if (!worldIn.isRemote) {
            this.ignite(worldIn, pos, explosionIn.getExplosivePlacedBy());
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
                this.ignite(worldIn, pos, entityarrow.shootingEntity instanceof EntityLivingBase ? (EntityLivingBase)entityarrow.shootingEntity : null);
            }
            else if (entityIn instanceof EntityLivingBase)
            {
                this.ignite(worldIn, pos, (EntityLivingBase)entityIn);
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
    
    @Override
    public IBlockState getStateFromMeta(int meta)
    {
        if (meta > 10) meta = 0;
        return this.getDefaultState().withProperty(COUNT, meta);
    }

    @Override
    public int getMetaFromState(IBlockState state)
    {
        return state.getValue(COUNT);
    }
    
    @Override
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, COUNT);
    }
}
