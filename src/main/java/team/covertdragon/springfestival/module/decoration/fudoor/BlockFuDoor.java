/*
 * Copyright (c) 2018 CovertDragon Team.
 * Copyright (c) 2018 Contributors of SpringFestival.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package team.covertdragon.springfestival.module.decoration.fudoor;

import net.minecraft.block.BlockDoor;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.state.properties.DoubleBlockHalf;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraftforge.common.ToolType;
import team.covertdragon.springfestival.module.decoration.DecorationRegistry;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BlockFuDoor extends BlockDoor {

    public BlockFuDoor(Properties properties) {
        super(properties);
    }

    @Nullable
    @Override
    public ToolType getHarvestTool(IBlockState state) {
        return ToolType.AXE;
    }

    @Override
    public int getHarvestLevel(IBlockState state) {
        return 0;
    }

    @Override
    @SuppressWarnings("deprecation")
    public EnumBlockRenderType getRenderType(IBlockState state) {
        return EnumBlockRenderType.ENTITYBLOCK_ANIMATED;
    }

    /* // TODO (3TUSK): FIX ME
    @Override
    @Nonnull
    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        return state.get(HALF) == DoubleBlockHalf.UPPER ? DecorationRegistry.FU : Items.AIR;
    }*/

    @Override
    public void getDrops(IBlockState state, NonNullList<ItemStack> drops, World world, BlockPos pos, int fortune) {
        drops.clear();
    }

    @Override
    public boolean canSilkHarvest(IBlockState state, IWorldReader world, BlockPos pos, EntityPlayer player) {
        return true;
    }

    @Override
    public boolean hasTileEntity(IBlockState state) {
        return state.get(HALF) == DoubleBlockHalf.UPPER;
    }

    @Override
    public TileEntity createTileEntity(IBlockState state, IBlockReader world) {
        return state.get(HALF) == DoubleBlockHalf.UPPER ? new TileFuDoor() : null;
    }

    @Override
    public boolean onBlockActivated(IBlockState state, World world, BlockPos pos, EntityPlayer player, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ) {
        BlockPos position = pos;
        //TileEntity will be null if the block is the lower part of the door
        if (state.get(HALF) == DoubleBlockHalf.LOWER) {
            position = position.up();
        }
        TileFuDoor te = (TileFuDoor) world.getTileEntity(position);
        if (te != null) {
            if (!world.isRemote && player.getHeldItem(hand).isEmpty()) {
                if (player.isSneaking()) {
                    if (te.getOriginalBlockStateUpper() != null) {
                        //Set door // TODO (3TUSK): FIX ME
                        // ItemDoor.placeDoor(world, position.add(0, -1, 0), te.getOriginalBlockStateUpper().get(BlockDoor.FACING), te.getOriginalBlockStateUpper().getBlock(), te.getOriginalBlockStateUpper().getValue(BlockDoor.HINGE) == BlockDoor.EnumHingePosition.RIGHT);
                        //Drop Fu
                        EntityItem entityItem = new EntityItem(world, pos.getX(), pos.getY(), pos.getZ(), new ItemStack(DecorationRegistry.FU, 1));
                        entityItem.setDefaultPickupDelay();
                        world.spawnEntity(entityItem);
                        return true;
                    }
                }
            } else {
                IBlockState upper = te.getOriginalBlockStateUpper();
                if (upper != null) {
                    upper.getBlock().onBlockActivated(upper, world, pos, player, hand, side, hitX, hitY, hitZ);
                }
            }
        }
        return super.onBlockActivated(state, world, pos, player, hand, side, hitX, hitY, hitZ);
    }

    @Override
    public void harvestBlock(World world, EntityPlayer player, BlockPos pos, IBlockState state, @Nullable TileEntity te, ItemStack stack) {
        // player.addStat(StatList.ITEM_BROKEN(this.asItem())); // TODO (3TUSK): What was this call?
        player.addExhaustion(0.005F);

        if (te == null) {
            te = world.getTileEntity(pos.add(0, 1, 0));
        }

        if (te == null) {
            throw new NullPointerException();
        }

        harvesters.set(player);

        List<ItemStack> drops = new ArrayList<>();
        drops.add(((TileFuDoor) te).getOriginalDoor());
        drops.add(new ItemStack(DecorationRegistry.FU, 1));

        for (ItemStack drop : drops) {
            EntityItem entity = new EntityItem(world, pos.getX(), pos.getY(), pos.getZ(), drop);
            entity.setDefaultPickupDelay();
            world.spawnEntity(entity);
        }

        harvesters.set(null);
    }

    @Override
    public ItemStack getPickBlock(IBlockState state, RayTraceResult target, IBlockReader world, BlockPos pos, EntityPlayer player) {
        return ItemStack.EMPTY; // Technical block should not have item form
    }
}
