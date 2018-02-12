/*
 * Copyright (c) 2018 CovertDragon Team.
 * Copyright (c) 2018 Contributors of SpringFestival.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package team.covertdragon.springfestival.module.redpacket;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

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
            synchronized (waitingQueue) { // TODO Why the hell you are using critical block?
                // TODO Serialize all pending request for next time use
            }
        }
    }

    public boolean enqueueOperation(RedPacketOperation operation) {
        return isAlive() && waitingQueue.offer(operation); // Do not accept operation when service shutting down
    }

    public boolean isAlive() {
        return keepAlive;
    }

    public void setAlive(boolean keepAlive) {
        // TODO Log output, for tracing & debugging
        this.keepAlive = keepAlive;
    }

    private static final class RedPacketDistributionTask implements Runnable {

        private EntityPlayer player;
        private RedPacketData packet;

        @Override
        public void run() {
            // TODO Use the actual item
            final EntityItem entityItem = new EntityItem(player.world, player.posX, player.posY, player.posZ, ItemStack.EMPTY);
            entityItem.setNoPickupDelay();
            player.world.spawnEntity(entityItem);
        }
    }
}
