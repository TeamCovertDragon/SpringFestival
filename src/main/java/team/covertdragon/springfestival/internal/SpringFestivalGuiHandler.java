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

    @Nullable
    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        if (ID > -1) {
            switch (ID) {
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
        } else { // TODO Universal TileEntity GUI handle
            return null;
        }
    }

    @Nullable
    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        if (ID > -1) {
            switch (ID) {
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
        } else { // TODO Universal TileEntity GUI handle
            return null;
        }
    }
}
