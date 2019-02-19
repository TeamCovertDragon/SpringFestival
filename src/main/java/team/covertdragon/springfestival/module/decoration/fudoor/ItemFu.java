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
import net.minecraft.item.Item;
import net.minecraft.item.ItemUseContext;
import net.minecraft.state.properties.DoubleBlockHalf;
import net.minecraft.util.EnumActionResult;
import team.covertdragon.springfestival.internal.network.SpringFestivalNetworkHandler;

/**
 * The item instance that represents the Fu character post.
 */
public class ItemFu extends Item {

    public ItemFu(Properties properties) {
        super(properties);
    }

    @Override
    public EnumActionResult onItemUse(ItemUseContext context) {
        IBlockState state = context.getWorld().getBlockState(context.getPos());
        if (!context.getWorld().isRemote && state.getBlock() instanceof BlockDoor && context.getPlayer().isSneaking() && !(state.getBlock() instanceof BlockFuDoor)) {
            IBlockState originalUpper;
            IBlockState originalLower;
            if (state.get(BlockDoor.HALF) == DoubleBlockHalf.UPPER) {
                originalUpper = state;
                originalLower = context.getWorld().getBlockState(context.getPos().add(0, -1, 0));
            } else {
                originalLower = state;
                originalUpper = context.getWorld().getBlockState(context.getPos().add(0, 1, 0));
            }

            //Delete original door
            context.getWorld().removeBlock(context.getPos());
            context.getWorld().removeBlock(context.getPos().add(0, -1, 0));
            //Set Tile Entity
            // TODO Orientation is wrong
            // TODO (3TUSK): Fix me
            // ItemDoor.placeDoor(world, pos.add(0, -1, 0), state.get(BlockDoor.FACING), DecorationRegistry.FU_DOOR, state.get(BlockDoor.HINGE) == DoorHingeSide.RIGHT);
            TileFuDoor te = (TileFuDoor) context.getWorld().getTileEntity(context.getPos());
            if (te != null) {
                te.setOriginalBlockStateLower(originalLower);
                te.setOriginalBlockStateUpper(originalUpper);
                SpringFestivalNetworkHandler.INSTANCE.sendToDimension(new ServerPacketFuDoorCreation(te), context.getWorld().dimension.getType().getId()); // TODO (3TUSK): This could be wrong
            } else {
                throw new NullPointerException();
            }

            context.getPlayer().getHeldItem(context.getPlayer().getActiveHand()).shrink(1);

            return EnumActionResult.SUCCESS;
        }
        return EnumActionResult.PASS;
    }

}
