package team.covertdragon.springfestival.module.fortune;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.TextComponentTranslation;
import team.covertdragon.springfestival.internal.network.AbstractSpringFestivalPacket;
import team.covertdragon.springfestival.internal.network.SpringFestivalNetworkHandler;

public class FortuneNetwork {
    static void init() {
        SpringFestivalNetworkHandler.INSTANCE.registerPacket(packetFortuneValue.class);
    }

    public static class packetFortuneValue implements AbstractSpringFestivalPacket {
        int val;

        public packetFortuneValue() {
        }

        public packetFortuneValue(int val) {
            this.val = val;
        }

        @Override
        public void writeDataTo(ByteBuf buffer) {
            buffer.writeInt(val);
        }

        @Override
        public void readDataFrom(ByteBuf buffer, EntityPlayer player) {
            Minecraft.getMinecraft().player.sendMessage(new TextComponentTranslation(
                    I18n.format("chat.springfestival.fv", buffer.readInt())));
        }
    }
}
