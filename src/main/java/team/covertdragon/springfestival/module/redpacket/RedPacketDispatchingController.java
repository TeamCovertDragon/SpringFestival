/*
 * Copyright (c) 2018 CovertDragon Team.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package team.covertdragon.springfestival.module.redpacket;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public final class RedPacketDispatchingController implements Runnable {

    private volatile boolean keepAlive = true;

    private String currentRedPacketID = "";
    // TODO change signature to <RedPacketOperation> so that it can also handles
    private final Queue<RedPacketOperation.Get> waitingQueue = new ConcurrentLinkedQueue<>();

    @Override
    public void run() {
        while (keepAlive) {
            // TODO Control all incoming red packet distribution without blocking game running
            RedPacketOperation operation = waitingQueue.poll();
            if (operation != null) {
                // TODO Process it
            }
            // TODO Thread safety. Requiring IThreadListener::addScheduledTask
        }
        // TODO What if the game exit without finishing processing all requests?
    }

    public boolean enqueueGetOperation(RedPacketOperation.Get operation) {
        return waitingQueue.offer(operation);
    }

    public boolean isKeepAlive() {
        return keepAlive;
    }

    public void setKeepAlive(boolean keepAlive) {
        // TODO Log output, for tracing & debugging
        this.keepAlive = keepAlive;
    }

    private static final class RedPacketDistributionTask implements Runnable {

        private EntityPlayer player;
        private RedPacketData packet;

        @Override
        public void run() {
            for (ItemStack stack : packet.contents) {
                // TODO Why you wrote this evil???
                player.inventory.mainInventory.add(stack);
            }
        }
    }
}