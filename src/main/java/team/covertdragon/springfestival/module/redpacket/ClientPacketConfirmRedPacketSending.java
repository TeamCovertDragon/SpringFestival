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
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.FMLCommonHandler;
import team.covertdragon.springfestival.internal.network.AbstractSpringFestivalPacket;
import team.covertdragon.springfestival.internal.network.SpringFestivalNetworkHandler;

public class ClientPacketConfirmRedPacketSending implements AbstractSpringFestivalPacket {

    @Override
    public void writeDataTo(ByteBuf buffer) {

    }

    @Override
    public void readDataFrom(ByteBuf buffer, EntityPlayer player) {
        final RedPacketData data = RedPacketData.fromItemStack(player.getHeldItemMainhand());
        if (data.getReceiver() == null) {
            SpringFestivalNetworkHandler.INSTANCE.sendToAll(new ServerPacketPublishingRedPacket(data));
        } else {
            final EntityPlayerMP targetPlayer = FMLCommonHandler.instance().getMinecraftServerInstance().getPlayerList().getPlayerByUUID(data.getReceiver());
            SpringFestivalNetworkHandler.INSTANCE.sendToPlayer(new ServerPacketSendingRedPacketToPlayer(), targetPlayer);
        }
    }
}
