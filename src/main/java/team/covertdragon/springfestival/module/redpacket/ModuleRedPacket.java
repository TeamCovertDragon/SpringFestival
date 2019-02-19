/*
 * Copyright (c) 2018 CovertDragon Team.
 * Copyright (c) 2018 Contributors of SpringFestival.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package team.covertdragon.springfestival.module.redpacket;

import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import team.covertdragon.springfestival.SpringFestivalConstants;
import team.covertdragon.springfestival.internal.network.SpringFestivalNetworkHandler;
import team.covertdragon.springfestival.module.AbstractSpringFestivalModule;
import team.covertdragon.springfestival.module.SpringFestivalModule;

@Mod.EventBusSubscriber(modid = SpringFestivalConstants.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
@SpringFestivalModule(name = "redpacket", dependencies = {"material"})
public class ModuleRedPacket extends AbstractSpringFestivalModule {

    public static final int GUI_RED_PACKET = 0;

    public static Item RED_PACKET;

    static final RedPacketDispatchingController RED_PACKET_CONTROLLER = new RedPacketDispatchingController();
    private Thread redPacketThread;

    @SubscribeEvent
    public static void onItemRegister(RegistryEvent.Register<Item> event) {
        event.getRegistry().register((RED_PACKET = new ItemRedPacket(new Item.Properties().group(SpringFestivalConstants.SPRING_GROUP))
                .setRegistryName(SpringFestivalConstants.MOD_ID, "redpacket")));
    }

    @Override
    public void onInit() {
        SpringFestivalNetworkHandler.INSTANCE.registerPacket(ClientPacketConfirmRedPacketSending.class);
        SpringFestivalNetworkHandler.INSTANCE.registerPacket(ClientPacketTogglePasscodeMode.class);
        SpringFestivalNetworkHandler.INSTANCE.registerPacket(ClientPacketUpdateRedPacketReceiver.class);
        SpringFestivalNetworkHandler.INSTANCE.registerPacket(ClientPacketUpdateRedPacketMessage.class);
        SpringFestivalNetworkHandler.INSTANCE.registerPacket(ServerPacketPublishingRedPacket.class);
        SpringFestivalNetworkHandler.INSTANCE.registerPacket(ServerPacketSendingRedPacketToPlayer.class);
    }

    @Override
    public void onServerStarting(FMLServerStartingEvent event) {
        new CommandRedPacket(event.getCommandDispatcher());
        RED_PACKET_CONTROLLER.setAlive(true);
        SpringFestivalConstants.logger.info("Starting red packet handler...");
        redPacketThread = new Thread(RED_PACKET_CONTROLLER, "SpringFestival-RedPacket");
        redPacketThread.setDaemon(true);
        redPacketThread.start();
    }

    @Override
    public void onServerStopping() {
        if (redPacketThread == null) { // But when this will happen?
            return;
        }
        RED_PACKET_CONTROLLER.setAlive(false);
        SpringFestivalConstants.logger.info("Shutting down red packet handler...");
        try {
            redPacketThread.join();
        } catch (InterruptedException e) {
            SpringFestivalConstants.logger.error("Fail to shutdown RedPacket controller thread", e);
        }
    }
}
