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
import net.minecraftforge.event.ServerChatEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public final class RedPacketDispatchingController implements Runnable {

    private volatile boolean keepAlive = true;

    private final Queue<RedPacketOperation> waitingQueue = new ConcurrentLinkedQueue<>();

    @Override
    public void run() {
        // TODO Read pending request from last server shutdown
        while (keepAlive) {
            // TODO Control all incoming red packet distribution without blocking game running
            RedPacketOperation operation = waitingQueue.poll();
            if (operation != null) {
                // TODO Process it
            }
            // TODO Thread safety. Requiring IThreadListener::addScheduledTask
        }
        if (!waitingQueue.isEmpty()) {
            synchronized (waitingQueue) {
                // TODO Serialize all pending request for next time use
            }
        }
    }

    public boolean enqueueGetOperation(RedPacketOperation operation) {
        return keepAlive && waitingQueue.offer(operation); // Do not accept operation when service shutting down
    }

    public boolean isAlive() {
        return keepAlive;
    }

    public void setAlive(boolean keepAlive) {
        // TODO Log output, for tracing & debugging
        this.keepAlive = keepAlive;
    }

    @SubscribeEvent
    public void onServerReceivedChat(ServerChatEvent event) {

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
