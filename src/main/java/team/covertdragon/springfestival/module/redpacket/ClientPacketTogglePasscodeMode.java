/*
 * Copyright (c) 2018 CovertDragon Team.
 * Copyright (c) 2018 Contributors of SpringFestival.
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

public class ClientPacketTogglePasscodeMode implements AbstractSpringFestivalPacket {

    private boolean newMode;

    public ClientPacketTogglePasscodeMode() {}

    ClientPacketTogglePasscodeMode(boolean newMode) {
        this.newMode = newMode;
    }

    @Override
    public void writeDataTo(ByteBuf buffer) {
        buffer.writeBoolean(newMode);
    }

    @Override
    public void readDataFrom(ByteBuf buffer, EntityPlayer player) {
        boolean newMode = buffer.readBoolean();
        ItemStack heldItem = player.getHeldItemMainhand();
        if (heldItem.getItem() == ModuleRedPacket.RED_PACKET) {
            NBTTagCompound tag = heldItem.getTag();
            if (tag == null) {
                tag = new NBTTagCompound();
                heldItem.setTag(tag);
            }
            tag.putBoolean("passcode", newMode);
        }
    }
}
