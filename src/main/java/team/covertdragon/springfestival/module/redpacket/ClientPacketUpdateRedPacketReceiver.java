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
import net.minecraft.nbt.NBTUtil;
import net.minecraftforge.fml.server.ServerLifecycleHooks;
import team.covertdragon.springfestival.internal.network.AbstractSpringFestivalPacket;

import java.nio.charset.StandardCharsets;

public class ClientPacketUpdateRedPacketReceiver implements AbstractSpringFestivalPacket {

    private String receiverName = "";

    public ClientPacketUpdateRedPacketReceiver() {}

    ClientPacketUpdateRedPacketReceiver(final String receiverName) {
        this.receiverName = receiverName;
    }

    @Override
    public void writeDataTo(ByteBuf buffer) {
        buffer.writeCharSequence(receiverName, StandardCharsets.UTF_8);
    }

    @Override
    public void readDataFrom(ByteBuf buffer, EntityPlayer player) {
        final String newReceiver = /*ByteBufUtils.readUTF8String(buffer)*/"FIX ME"; // TODO (3TUSK): FIX ME
        EntityPlayer receiverPlayer = ServerLifecycleHooks.getCurrentServer().getPlayerList().getPlayerByUsername(newReceiver);
        NBTTagCompound redPacketData = player.getHeldItemMainhand().getTag();
        if (redPacketData != null) {
            if (receiverPlayer != null) {
                redPacketData.put("receiver", NBTUtil.writeUniqueId(receiverPlayer.getUniqueID()));
            }
            redPacketData.putString("receiver_name", newReceiver);
        }
    }
}
