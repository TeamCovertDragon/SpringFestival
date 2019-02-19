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
 *
 *
 * Copyright (c) 2015 - 2018 3TUSK, et al.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package team.covertdragon.springfestival.internal.network;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;
import team.covertdragon.springfestival.SpringFestivalConstants;

public final class SpringFestivalNetworkHandler {

    public static final SpringFestivalNetworkHandler INSTANCE = new SpringFestivalNetworkHandler();

    private static final String PROTOCOL_VERSION = "0.1.0";

    public static String getNetworkingProtocolVersion() {
        return PROTOCOL_VERSION;
    }

    private final SimpleChannel springFestivalChannel;

    private final SpringFestivalPacketFactory packetFactory = new SpringFestivalPacketFactory();

    private SpringFestivalNetworkHandler() {
        this.springFestivalChannel = NetworkRegistry.ChannelBuilder
                .named(new ResourceLocation(SpringFestivalConstants.MOD_ID, "spring_channel"))
                .clientAcceptedVersions(PROTOCOL_VERSION::equals)
                .serverAcceptedVersions(PROTOCOL_VERSION::equals)
                .networkProtocolVersion(SpringFestivalNetworkHandler::getNetworkingProtocolVersion)
                .simpleChannel();
    }

    public void registerPacket(Class<? extends AbstractSpringFestivalPacket> klass) {
        packetFactory.mapPacketToNextAvailableIndex(klass);
    }

    public void sendToAll(AbstractSpringFestivalPacket packet) {
        //springFestivalChannel.send(new FMLProxyPacket(new PacketBuffer(fromPacket(packet)), SpringFestivalConstants.MOD_ID));
    }

    public void sendToAllAround(AbstractSpringFestivalPacket packet, int dim, double x, double y, double z, double range) {
        //springFestivalChannel.(new FMLProxyPacket(new PacketBuffer(fromPacket(packet)), SpringFestivalConstants.MOD_ID), new NetworkRegistry.TargetPoint(dim, x, y, z, range));
    }

    public void sendToDimension(AbstractSpringFestivalPacket packet, int dim) {
        //springFestivalChannel.sendToDimension(new FMLProxyPacket(new PacketBuffer(fromPacket(packet)), SpringFestivalConstants.MOD_ID), dim);
    }

    public void sendToPlayer(AbstractSpringFestivalPacket packet, EntityPlayerMP player) {
        //springFestivalChannel.sendTo(new FMLProxyPacket(new PacketBuffer(fromPacket(packet)), SpringFestivalConstants.MOD_ID), player);
    }

    public void sendToServer(AbstractSpringFestivalPacket packet) {
        //springFestivalChannel.sendToServer(new FMLProxyPacket(new PacketBuffer(fromPacket(packet)), SpringFestivalConstants.MOD_ID));
    }
}
