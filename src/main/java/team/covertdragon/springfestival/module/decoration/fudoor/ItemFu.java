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
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import team.covertdragon.springfestival.SpringFestivalConstants;
import team.covertdragon.springfestival.internal.network.SpringFestivalNetworkHandler;
import team.covertdragon.springfestival.module.decoration.DecorationRegistry;

/**
 * The item instance that represents the Fu character post.
 */
public class ItemFu extends Item {

    public ItemFu() {
        setRegistryName(SpringFestivalConstants.MOD_ID, "fu");
        setUnlocalizedName(SpringFestivalConstants.MOD_ID + ".fu");
        setCreativeTab(SpringFestivalConstants.CREATIVE_TAB);
    }

    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        IBlockState state = world.getBlockState(pos);
        if (!world.isRemote && state.getBlock() instanceof BlockDoor && player.isSneaking() && !(state.getBlock() instanceof BlockFuDoor)) {
            IBlockState originalUpper;
            IBlockState originalLower;
            if (state.getValue(BlockDoor.HALF) == BlockDoor.EnumDoorHalf.UPPER) {
                originalUpper = state;
                originalLower = world.getBlockState(pos.add(0, -1, 0));
            } else {
                originalLower = state;
                pos = pos.add(0, 1, 0);
                originalUpper = world.getBlockState(pos);
            }

            //Delete original door
            world.setBlockToAir(pos);
            world.setBlockToAir(pos.add(0, -1, 0));
            //Set Tile Entity
            ItemFuDoor.placeDoor(world, pos.add(0, -1, 0), state.getValue(BlockDoor.FACING), DecorationRegistry.FU_DOOR, state.getValue(BlockDoor.HINGE) == BlockDoor.EnumHingePosition.RIGHT);
            TileFuDoor te = (TileFuDoor) world.getTileEntity(pos);
            if (te != null) {
                te.setOriginalBlockStateLower(originalLower);
                te.setOriginalBlockStateUpper(originalUpper);
                SpringFestivalNetworkHandler.INSTANCE.sendToDimension(new ServerPacketFuDoorCreation(te), world.provider.getDimension());
            } else {
                throw new NullPointerException();
            }

            player.getHeldItem(hand).shrink(1);

            return EnumActionResult.SUCCESS;
        }
        return EnumActionResult.PASS;
    }

}
