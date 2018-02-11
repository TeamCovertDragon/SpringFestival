/*
 * Copyright (c) 2018 CovertDragon Team.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package team.covertdragon.springfestival;

import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.event.FMLServerStoppingEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import team.covertdragon.springfestival.internal.SpringFestivalGuiHandler;
import team.covertdragon.springfestival.internal.time.ISpringFestivalTimeProvider;
import team.covertdragon.springfestival.internal.time.SpringFestivalTimeProviderImpossible;
import team.covertdragon.springfestival.internal.time.SpringFestivalTimeProviderLocal;
import team.covertdragon.springfestival.internal.time.SpringFestivalTimeProviderQuerying;
import team.covertdragon.springfestival.module.redpacket.RedPacketDispatchingController;

import java.util.ArrayList;
import java.util.List;

public abstract class SpringFestivalProxy {

    private static final List<ISpringFestivalTimeProvider> DATE_CHECKERS = new ArrayList<>();

    static {
        FluidRegistry.enableUniversalBucket();
        DATE_CHECKERS.add(SpringFestivalTimeProviderQuerying.INSTANCE);
        DATE_CHECKERS.add(SpringFestivalTimeProviderLocal.INSTANCE);
        DATE_CHECKERS.add(SpringFestivalTimeProviderImpossible.INSTANCE);
    }

    private RedPacketDispatchingController redPacketController = new RedPacketDispatchingController();
    private Thread redPacketThread;

    /**
     * Determine whether the current time is falling into the Spring Festival season, based on
     * current system time.
     * @return true if it is during Spring Festival; false for otherwise.
     */
    public boolean isDuringSpringFestivalSeason() {
        boolean queryResult = false;
        for (ISpringFestivalTimeProvider checker : DATE_CHECKERS) {
            queryResult |= checker.isDuringSpringFestival();
        }
        return queryResult;
    }

    public void onPreInit(FMLPreInitializationEvent event) {
        SpringFestivalConstants.logger = event.getModLog();
    };

    public void onInit(FMLInitializationEvent event) {
        NetworkRegistry.INSTANCE.registerGuiHandler(SpringFestival.getInstance(), new SpringFestivalGuiHandler());
    }

    public abstract void onPostInit(FMLPostInitializationEvent event);

    // TODO Move all RedPacket stuff to ModuleRedPacket, requiring internal refactor
    public void onServerStarting(FMLServerStartingEvent event) {
        redPacketThread = new Thread(redPacketController, "SpringFestival-RedPacket");
        redPacketThread.setDaemon(true);
        redPacketThread.start();
    }

    public void onServerStopping(FMLServerStoppingEvent event) {
        if (redPacketThread == null) { // But when this will happen?
            return;
        }
        redPacketController.setAlive(false);
        try {
            redPacketThread.join();
        } catch (InterruptedException e) {
            SpringFestivalConstants.logger.error("Fail to shutdown RedPacket controller thread", e);
        }
    }

    public RedPacketDispatchingController getRedPacketController() {
        return redPacketController;
    }
}
