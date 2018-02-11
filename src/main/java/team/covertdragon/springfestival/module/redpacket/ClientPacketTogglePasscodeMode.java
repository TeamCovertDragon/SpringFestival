/*
 * Copyright (c) 2018 CovertDragon Team.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package team.covertdragon.springfestival.module.redpacket;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import team.covertdragon.springfestival.internal.network.AbstractSpringFestivalPacket;

import java.io.IOException;

public class ClientPacketTogglePasscodeMode implements AbstractSpringFestivalPacket {

    private boolean newMode;

    ClientPacketTogglePasscodeMode(boolean newMode) {
        this.newMode = newMode;
    }

    @Override
    public void writeDataTo(ByteBuf buffer) throws IOException {
        buffer.writeBoolean(newMode);
    }

    @Override
    public void readDataFrom(ByteBuf buffer, EntityPlayer player) {
        boolean newMode = buffer.readBoolean();
        ItemStack heldItem = player.getHeldItemMainhand();
        if (heldItem.getItem() == ModuleRedPacket.RED_PACKET) {
            NBTTagCompound tag = heldItem.getTagCompound();
            if (tag == null) {
                tag = new NBTTagCompound();
                heldItem.setTagCompound(tag);
            }
            tag.setBoolean("passcode", newMode);
        }
    }
}
