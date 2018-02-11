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
import team.covertdragon.springfestival.internal.network.AbstractSpringFestivalPacket;

import java.io.IOException;

public class ClientPacketConfirmRedPacketSending implements AbstractSpringFestivalPacket {

    @Override
    public void writeDataTo(ByteBuf buffer) throws IOException {

    }

    @Override
    public void readDataFrom(ByteBuf buffer, EntityPlayer player) throws IOException {

    }
}
