/*
 * Copyright (c) 2018 CovertDragon Team.
 * Copyright (c) 2018 Contributors of SpringFestival.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package team.covertdragon.springfestival.redpacket;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTUtil;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import team.covertdragon.springfestival.internal.network.AbstractSpringFestivalPacket;

public class ClientPacketUpdateRedPacketReceiver implements AbstractSpringFestivalPacket {

    private String receiverName = "";

    public ClientPacketUpdateRedPacketReceiver() {}

    ClientPacketUpdateRedPacketReceiver(final String receiverName) {
        this.receiverName = receiverName;
    }

    @Override
    public void writeDataTo(ByteBuf buffer) {
        ByteBufUtils.writeUTF8String(buffer, receiverName);
    }

    @Override
    public void readDataFrom(ByteBuf buffer, EntityPlayer player) {
        final String newReceiver = ByteBufUtils.readUTF8String(buffer);
        EntityPlayer receiverPlayer = FMLCommonHandler.instance().getMinecraftServerInstance().getPlayerList().getPlayerByUsername(newReceiver);
        NBTTagCompound redPacketData = player.getHeldItemMainhand().getTagCompound();
        if (redPacketData != null) {
            if (receiverPlayer != null) {
                redPacketData.setTag("receiver", NBTUtil.createUUIDTag(receiverPlayer.getUniqueID()));
            }
            redPacketData.setString("receiver_name", newReceiver);
        }
    }
}
