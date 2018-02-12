/*
 * Copyright (c) 2018 CovertDragon Team.
 * Copyright (c) 2018 Contributors of SpringFestival.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

/*
 * This file is adapted from FrogCraft: Rebirth, revision e0e04b12d2f8ded8eb54314b5a9d8582cfdfa89b
 *
 * Original file:
 * src/main/java/frogcraftrebirth/common/network/NetworkHandler.java
 *
 * FrogCraft: Rebirth is licensed under MIT. The original license header
 * are provided below.
 */


package team.covertdragon.springfestival.internal.network;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.NetHandlerPlayServer;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.FMLEventChannel;
import net.minecraftforge.fml.common.network.FMLNetworkEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.internal.FMLProxyPacket;
import team.covertdragon.springfestival.SpringFestivalConstants;

import java.io.IOException;

public final class SpringFestivalNetworkHandler {

    public static final SpringFestivalNetworkHandler INSTANCE = new SpringFestivalNetworkHandler();

    private final FMLEventChannel springFestivalChannel;

    private final SpringFestivalPacketFactory packetFactory = new SpringFestivalPacketFactory();

    private SpringFestivalNetworkHandler() {
        (springFestivalChannel = NetworkRegistry.INSTANCE.newEventDrivenChannel(SpringFestivalConstants.MOD_ID)).register(this);
    }

    public void registerPacket(Class<? extends AbstractSpringFestivalPacket> klass) {
        packetFactory.mapPacketToNextAvailableIndex(klass);
    }

    @SubscribeEvent
    public void serverPacket(FMLNetworkEvent.ServerCustomPacketEvent event) {
        decodeData(event.getPacket().payload(), ((NetHandlerPlayServer)event.getHandler()).player);
    }

    @SubscribeEvent
    public void clientPacket(FMLNetworkEvent.ClientCustomPacketEvent event) {
        decodeData(event.getPacket().payload(), Minecraft.getMinecraft().player);
    }

    private void decodeData(ByteBuf buffer, EntityPlayer player) {
        final int index = buffer.readInt();
        AbstractSpringFestivalPacket packet = packetFactory.getByIndex(index);
        FMLCommonHandler.instance().getMinecraftServerInstance().addScheduledTask(() -> {
            try {
                packet.readDataFrom(buffer, player);
            } catch (IOException e) {
                SpringFestivalConstants.logger.catching(e);
            }
        });
    }

    private static ByteBuf fromPacket(AbstractSpringFestivalPacket packet) {
        ByteBuf buffer = Unpooled.buffer();
        try {
            packet.writeDataTo(buffer);
        } catch (IOException e) {
            e.printStackTrace(System.err);
        }
        return buffer;
    }

    public void sendToAll(AbstractSpringFestivalPacket packet) {
        springFestivalChannel.sendToAll(new FMLProxyPacket(new PacketBuffer(fromPacket(packet)), SpringFestivalConstants.MOD_ID));
    }

    public void sendToAllAround(AbstractSpringFestivalPacket packet, int dim, double x, double y, double z, double range) {
        springFestivalChannel.sendToAllAround(new FMLProxyPacket(new PacketBuffer(fromPacket(packet)), SpringFestivalConstants.MOD_ID), new NetworkRegistry.TargetPoint(dim, x, y, z, range));
    }

    public void sendToDimension(AbstractSpringFestivalPacket packet, int dim) {
        springFestivalChannel.sendToDimension(new FMLProxyPacket(new PacketBuffer(fromPacket(packet)), SpringFestivalConstants.MOD_ID), dim);
    }

    public void sendToPlayer(AbstractSpringFestivalPacket packet, EntityPlayerMP player) {
        springFestivalChannel.sendTo(new FMLProxyPacket(new PacketBuffer(fromPacket(packet)), SpringFestivalConstants.MOD_ID), player);
    }

    public void sendToServer(AbstractSpringFestivalPacket packet) {
        springFestivalChannel.sendToServer(new FMLProxyPacket(new PacketBuffer(fromPacket(packet)), SpringFestivalConstants.MOD_ID));
    }
}
