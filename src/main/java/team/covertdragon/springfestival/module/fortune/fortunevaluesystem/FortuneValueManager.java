/*
 * Copyright (c) 2018 CovertDragon Team.
 * Copyright (c) 2018 Contributors of SpringFestival.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package team.covertdragon.springfestival.module.fortune.fortunevaluesystem;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import team.covertdragon.springfestival.SpringFestivalConstants;
import team.covertdragon.springfestival.module.fortune.FortuneRegistry;
import team.covertdragon.springfestival.module.fortune.fortunevaluesystem.capability.CapabilityLoader;
import team.covertdragon.springfestival.module.fortune.fortunevaluesystem.capability.IFortuneValueSystem;
import team.covertdragon.springfestival.module.fortune.machines.AbstractTileFVMachine;

import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class FortuneValueManager implements Runnable {
    public boolean alive = false;
    private Queue<Runnable> TASKS = new ConcurrentLinkedQueue<>();
    private MinecraftServer server;

    public FortuneValueManager(MinecraftServer server) {
        this.server = server;
    }

    public void addTask(Runnable task) {
        TASKS.add(task);
    }

    @Override
    public void run() {
        SpringFestivalConstants.logger.info("Starting fortune value handler...");
        List<EntityPlayerMP> playerList = server.getPlayerList().getPlayers();
        while (alive) {
            while (!TASKS.isEmpty()) {
                TASKS.poll().run();
            }

            for (EntityPlayerMP player : playerList) {
                if (player != null) {
                    updatePlayerFortuneValue(player);
                }
            }

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                SpringFestivalConstants.logger.catching(e);
            }
        }
    }

    private void updatePlayerFortuneValue(EntityPlayerMP player) {
        IFortuneValueSystem system = player.getCapability(CapabilityLoader.fortuneValue, null);
        if (system != null) {
            //Fortune potion
            if (player.isPotionActive(FortuneRegistry.potionFortunate)) {
                system.addFortune(9);
            }

            //Update player fortune level
            system.addFortune(system.getIncreasingPoint());
            system.setBufPoint(0);

            //Tick FV machines
            for (AbstractTileFVMachine machine : system.getFVMachines()) {
                if (machine.getWorld().getTileEntity(machine.getPos()) == null) {
                    system.deleteMachine(machine.getId());
                } else if (system.shrinkFortune(machine.getRequiredFV())) {
                    machine.onFVProvided();
                }
            }
        } else {
            throw new RuntimeException("Unable to read fv system info for player " + player.getGameProfile().getName());
        }
    }
}
