/*
 * Copyright (c) 2018 CovertDragon Team.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package team.covertdragon.springfestival.module.redpacket;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import team.covertdragon.springfestival.internal.network.AbstractSpringFestivalPacket;

public class ServerPacketPublishingRedPacket implements AbstractSpringFestivalPacket {

    RedPacketData data;

    ServerPacketPublishingRedPacket(RedPacketData data) {
        this.data = data;
    }

    @Override
    public void writeDataTo(ByteBuf buffer) {
        ByteBufUtils.writeUTF8String(buffer, data.getName());
        ByteBufUtils.writeUTF8String(buffer, data.getMessage());
        buffer.writeBoolean(data.isHasPasscode());
    }

    @Override
    public void readDataFrom(ByteBuf buffer, EntityPlayer player) {
        final String name = ByteBufUtils.readUTF8String(buffer);
        final String message = ByteBufUtils.readUTF8String(buffer);
        final boolean hasPasscode = data.isHasPasscode();
        Minecraft.getMinecraft().getToastGui().add(new RedPacketToast(name, message, hasPasscode));
    }
}
