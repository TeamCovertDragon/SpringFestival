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
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import team.covertdragon.springfestival.internal.network.AbstractSpringFestivalPacket;

import java.util.UUID;

public class ServerPacketPublishingRedPacket implements AbstractSpringFestivalPacket {

    RedPacketData data;

    public ServerPacketPublishingRedPacket() {}

    ServerPacketPublishingRedPacket(RedPacketData data) {
        this.data = data;
    }

    @Override
    public void writeDataTo(ByteBuf buffer) {
        final String publisherName = FMLCommonHandler.instance().getMinecraftServerInstance().getPlayerList().getPlayerByUUID(data.getOwner()).getDisplayNameString();
        ByteBufUtils.writeUTF8String(buffer, publisherName);
        ByteBufUtils.writeUTF8String(buffer, data.getMessage());
        buffer.writeBoolean(data.isHasPasscode());
    }

    @Override
    public void readDataFrom(ByteBuf buffer, EntityPlayer player) {
        final String publisher = ByteBufUtils.readUTF8String(buffer);
        final String message = ByteBufUtils.readUTF8String(buffer);
        final boolean hasPasscode = buffer.readBoolean();
        Minecraft.getMinecraft().getToastGui().add(new RedPacketToast(publisher, message, hasPasscode));
    }
}
