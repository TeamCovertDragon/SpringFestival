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
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.FMLCommonHandler;
import team.covertdragon.springfestival.SpringFestivalConstants;
import team.covertdragon.springfestival.internal.network.AbstractSpringFestivalPacket;
import team.covertdragon.springfestival.internal.network.SpringFestivalNetworkHandler;

public class ClientPacketConfirmRedPacketSending implements AbstractSpringFestivalPacket {

    @Override
    public void writeDataTo(ByteBuf buffer) {}

    @Override
    public void readDataFrom(ByteBuf buffer, EntityPlayer player) {
        final RedPacketData data = RedPacketData.fromItemStack(player.getHeldItemMainhand());
        if (data.getReceiver() == null) {
            RedPacketOperation.Post postOperation = new RedPacketOperation.Post(data.getOwner(), data);
            if (ModuleRedPacket.RED_PACKET_CONTROLLER.enqueueOperation(postOperation)) {
                player.getHeldItemMainhand().shrink(1);
                SpringFestivalNetworkHandler.INSTANCE.sendToAll(new ServerPacketPublishingRedPacket(data));
            } else {
                SpringFestivalConstants.logger.error("Failed to enqueue red packet! Report immediately!");
            }
        } else {
            final EntityPlayerMP targetPlayer = FMLCommonHandler.instance().getMinecraftServerInstance().getPlayerList().getPlayerByUUID(data.getReceiver());
            if (targetPlayer != null) {
                player.getHeldItemMainhand().shrink(1);
                SpringFestivalNetworkHandler.INSTANCE.sendToPlayer(new ServerPacketSendingRedPacketToPlayer(), targetPlayer);
            } else {
                // TODO Enqueue to wait list
            }
        }
    }
}
