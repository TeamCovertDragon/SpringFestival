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
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import team.covertdragon.springfestival.internal.network.AbstractSpringFestivalPacket;

public class ServerPacketSendingRedPacketToPlayer implements AbstractSpringFestivalPacket {
    @Override
    public void writeDataTo(ByteBuf buffer) {

    }

    @Override
    public void readDataFrom(ByteBuf buffer, EntityPlayer player) {
        // TODO Actually implements this packet
        Minecraft.getMinecraft().getToastGui().add(new RedPacketToast("", "", false));
    }
}
