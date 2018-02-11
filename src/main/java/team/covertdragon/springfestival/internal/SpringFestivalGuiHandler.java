/*
 * Copyright (c) 2018 CovertDragon Team.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package team.covertdragon.springfestival.internal;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;
import team.covertdragon.springfestival.module.redpacket.ContainerRedPacket;
import team.covertdragon.springfestival.module.redpacket.GuiContainerRedPacket;
import team.covertdragon.springfestival.module.redpacket.ModuleRedPacket;

import javax.annotation.Nullable;

public class SpringFestivalGuiHandler implements IGuiHandler {

    public static final int TILE_ENTITY_GUI = -1;

    @Nullable
    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        switch (ID) {
            case TILE_ENTITY_GUI: {
                TileEntity tile = world.getTileEntity(new BlockPos(x, y, z));
                if (tile instanceof IGuiAccessible) {
                    return ((IGuiAccessible)tile).getServerGuiObject(world, player);
                }
            }
            case ModuleRedPacket.GUI_RED_PACKET: {
                ItemStack heldItem = player.getHeldItemMainhand();
                IItemHandler inv = heldItem.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
                if (inv != null && inv instanceof IItemHandlerModifiable) {
                    return new ContainerRedPacket(player.inventory, (IItemHandlerModifiable) inv);
                } else {
                    return null;
                }
            }
            default:
                return null;
        }
    }

    @Nullable
    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        switch (ID) {
            case TILE_ENTITY_GUI: {
                TileEntity tile = world.getTileEntity(new BlockPos(x, y, z));
                if (tile instanceof IGuiAccessible) {
                    return ((IGuiAccessible)tile).getClientGuiObject(world, player);
                }
            }
            case ModuleRedPacket.GUI_RED_PACKET: {
                ItemStack heldItem = player.getHeldItemMainhand();
                IItemHandler inv = heldItem.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
                if (inv != null && inv instanceof IItemHandlerModifiable) {
                    return new GuiContainerRedPacket(player.inventory, (IItemHandlerModifiable) inv);
                } else {
                    return null;
                }
            }
            default:
                return null;
        }
    }
}
