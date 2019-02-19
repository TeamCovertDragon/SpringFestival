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
import net.minecraftforge.fml.server.ServerLifecycleHooks;
import team.covertdragon.springfestival.internal.network.AbstractSpringFestivalPacket;

import java.nio.charset.StandardCharsets;

public class ServerPacketPublishingRedPacket implements AbstractSpringFestivalPacket {

    RedPacketData data;

    public ServerPacketPublishingRedPacket() {}

    ServerPacketPublishingRedPacket(RedPacketData data) {
        this.data = data;
    }

    @Override
    public void writeDataTo(ByteBuf buffer) {
        final String publisherName = ServerLifecycleHooks.getCurrentServer().getPlayerList().getPlayerByUUID(data.getOwner()).getDisplayName().getString();
        buffer.writeCharSequence(publisherName, StandardCharsets.UTF_8);
        buffer.writeCharSequence(data.getMessage(), StandardCharsets.UTF_8);
        buffer.writeBoolean(data.isHasPasscode());
    }

    @Override
    public void readDataFrom(ByteBuf buffer, EntityPlayer player) {
        // TODO (3TUSK): FIX ME
        final String publisher = /*ByteBufUtils.readUTF8String(buffer);*/"FIX ME";
        final String message = /*ByteBufUtils.readUTF8String(buffer);*/"FIX ME";
        final boolean hasPasscode = buffer.readBoolean();
        Minecraft.getInstance().getToastGui().add(new RedPacketToast(publisher, message, hasPasscode));
    }
}
