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
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import team.covertdragon.springfestival.internal.network.AbstractSpringFestivalPacket;

import java.nio.charset.StandardCharsets;

public class ClientPacketUpdateRedPacketMessage implements AbstractSpringFestivalPacket {

    private String message = "";

    public ClientPacketUpdateRedPacketMessage() {}

    ClientPacketUpdateRedPacketMessage(final String receiverName) {
        this.message = receiverName;
    }

    @Override
    public void writeDataTo(ByteBuf buffer) {
        buffer.writeCharSequence(message, StandardCharsets.UTF_8);
    }

    @Override
    public void readDataFrom(ByteBuf buffer, EntityPlayer player) {
        final String newMessage = /*ByteBufUtils.readUTF8String(buffer)*/"FIX ME"; // TODO (3TUSK): FIX ME
        NBTTagCompound redPacketData = player.getHeldItemMainhand().getTag();
        if (redPacketData != null) {
            redPacketData.putString("message", newMessage);
        }
    }
}
