/*
 * Copyright (c) 2018 CovertDragon Team.
 * Copyright (c) 2018 Contributors of SpringFestival.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package team.covertdragon.springfestival.module.decoration.fudoor;

import net.minecraft.block.Block;
import net.minecraft.block.BlockDoor;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.state.properties.DoorHingeSide;
import net.minecraft.state.properties.DoubleBlockHalf;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class FuUtil {

    public static void placeDoor(World world, BlockPos pos, EnumFacing facing, Block door, boolean isRightHinge, ItemStack stack) {
        BlockPos blockpos = pos.offset(facing.rotateY());
        BlockPos blockpos1 = pos.offset(facing.rotateYCCW());
        int i = (world.getBlockState(blockpos1).isNormalCube() ? 1 : 0) + (world.getBlockState(blockpos1.up()).isNormalCube() ? 1 : 0);
        int j = (world.getBlockState(blockpos).isNormalCube() ? 1 : 0) + (world.getBlockState(blockpos.up()).isNormalCube() ? 1 : 0);
        boolean flag = world.getBlockState(blockpos1).getBlock() == door || world.getBlockState(blockpos1.up()).getBlock() == door;
        boolean flag1 = world.getBlockState(blockpos).getBlock() == door || world.getBlockState(blockpos.up()).getBlock() == door;

        if ((!flag || flag1) && j <= i) {
            if (flag1 && !flag || j < i) {
                isRightHinge = false;
            }
        } else {
            isRightHinge = true;
        }

        BlockPos blockpos2 = pos.up();
        boolean flag2 = world.isBlockPowered(pos) || world.isBlockPowered(blockpos2);
        IBlockState iblockstate = door.getDefaultState().with(BlockDoor.FACING, facing).with(BlockDoor.HINGE, isRightHinge ? DoorHingeSide.RIGHT : DoorHingeSide.LEFT).with(BlockDoor.POWERED, flag2).with(BlockDoor.OPEN, flag2);
        world.setBlockState(pos, iblockstate.with(BlockDoor.HALF, DoubleBlockHalf.LOWER), 2);
        world.setBlockState(blockpos2, iblockstate.with(BlockDoor.HALF, DoubleBlockHalf.UPPER), 2);
        world.notifyNeighborsOfStateChange(pos, door);
        world.notifyNeighborsOfStateChange(blockpos2, door);

        TileEntity te = world.getTileEntity(pos);
        if (te == null) {
            te = world.getTileEntity(pos.add(0, 1, 0));
        }

        if (stack.hasTag() & te != null && te instanceof TileFuDoor) {
            te.deserializeNBT(stack.getTag());
        }
    }
}
