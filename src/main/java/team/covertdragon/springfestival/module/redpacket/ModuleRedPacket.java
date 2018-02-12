/*
 * Copyright (c) 2018 CovertDragon Team.
 * Copyright (c) 2018 Contributors of SpringFestival.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package team.covertdragon.springfestival.module.redpacket;

import net.minecraft.command.CommandHandler;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import team.covertdragon.springfestival.SpringFestivalConstants;
import team.covertdragon.springfestival.internal.model.ModelUtil;
import team.covertdragon.springfestival.internal.network.SpringFestivalNetworkHandler;
import team.covertdragon.springfestival.module.AbstractSpringFestivalModule;
import team.covertdragon.springfestival.module.SpringFestivalModule;

@SpringFestivalModule(name = "redpacket", dependencies = {"material"})
public class ModuleRedPacket extends AbstractSpringFestivalModule {

    public static final Item RED_PACKET = new ItemRedPacket()
            .setCreativeTab(SpringFestivalConstants.CREATIVE_TAB)
            .setUnlocalizedName("springfestival.redpacket")
            .setRegistryName("springfestival:redpacket");

    public static final int GUI_RED_PACKET = 0;

    static final RedPacketDispatchingController RED_PACKET_CONTROLLER = new RedPacketDispatchingController();
    private Thread redPacketThread;

    @SubscribeEvent
    public void onItemRegister(RegistryEvent.Register<Item> event) {
        event.getRegistry().register(RED_PACKET);
    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void onModelRegister(ModelRegistryEvent event) {
        ModelUtil.mapItemModel(RED_PACKET);
    }

    @Override
    public void onInit() {
        SpringFestivalNetworkHandler.INSTANCE.registerPacket(ClientPacketConfirmRedPacketSending.class);
        SpringFestivalNetworkHandler.INSTANCE.registerPacket(ClientPacketTogglePasscodeMode.class);
        SpringFestivalNetworkHandler.INSTANCE.registerPacket(ClientPacketUpdateRedPacketReceiver.class);
        SpringFestivalNetworkHandler.INSTANCE.registerPacket(ClientPacketUpdateRedPacketMessage.class);
    }

    @Override
    public void onServerStarting() {
        ((CommandHandler)FMLCommonHandler.instance().getMinecraftServerInstance().getCommandManager()).registerCommand(new CommandRedPacket());
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
        try {
            redPacketThread.join();
        } catch (InterruptedException e) {
            SpringFestivalConstants.logger.error("Fail to shutdown RedPacket controller thread", e);
        }
    }
}
