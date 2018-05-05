package team.covertdragon.springfestival.module.fortune;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import team.covertdragon.springfestival.internal.network.AbstractSpringFestivalPacket;
import team.covertdragon.springfestival.internal.network.SpringFestivalNetworkHandler;
import team.covertdragon.springfestival.module.fortune.fortunevaluesystem.capability.CapabilityLoader;

public class FortuneNetwork {
    static void init() {
        SpringFestivalNetworkHandler.INSTANCE.registerPacket(packetSendFortuneValue.class);
        SpringFestivalNetworkHandler.INSTANCE.registerPacket(packetRequestFortuneValue.class);
    }

    public static class packetSendFortuneValue implements AbstractSpringFestivalPacket {
        int val;

        public packetSendFortuneValue() {
        }

        public packetSendFortuneValue(int val) {
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
            SpringFestivalNetworkHandler.INSTANCE.sendToPlayer(new packetSendFortuneValue(fv), (EntityPlayerMP) player);
        }
    }
}
