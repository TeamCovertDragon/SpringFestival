/*
 * Copyright (c) 2018 CovertDragon Team.
 * Copyright (c) 2018 Contributors of SpringFestival.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package team.covertdragon.springfestival.module.fortune;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import team.covertdragon.springfestival.internal.network.AbstractSpringFestivalPacket;
import team.covertdragon.springfestival.internal.network.SpringFestivalNetworkHandler;
import team.covertdragon.springfestival.module.fortune.fortunevaluesystem.capability.CapabilityLoader;

public class FortuneNetwork {
    static void init() {
        SpringFestivalNetworkHandler.INSTANCE.registerPacket(packetResponseFortuneValue.class);
        SpringFestivalNetworkHandler.INSTANCE.registerPacket(packetRequestFortuneValue.class);
    }

    public static class packetResponseFortuneValue implements AbstractSpringFestivalPacket {
        int val;

        public packetResponseFortuneValue() {
        }

        public packetResponseFortuneValue(int val) {
            this.val = val;
        }

        @Override
        public void writeDataTo(ByteBuf buffer) {
            buffer.writeInt(val);
        }

        @Override
        public void readDataFrom(ByteBuf buffer, EntityPlayer player) {
            FortuneClientHelper.fortune_value = buffer.readInt();
        }
    }

    public static class packetRequestFortuneValue implements AbstractSpringFestivalPacket {

        @Override
        public void writeDataTo(ByteBuf buffer) {

        }

        @Override
        public void readDataFrom(ByteBuf buffer, EntityPlayer player) {
            int fv = player.getCapability(CapabilityLoader.fortuneValue, null).getFortuneValue();
            SpringFestivalNetworkHandler.INSTANCE.sendToPlayer(new packetResponseFortuneValue(fv), (EntityPlayerMP) player);
        }
    }
}
