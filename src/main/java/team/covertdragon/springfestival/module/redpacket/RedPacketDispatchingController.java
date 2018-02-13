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

import javax.annotation.Nullable;
import java.util.Deque;
import java.util.Iterator;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.ConcurrentLinkedQueue;

public final class RedPacketDispatchingController implements Runnable {

    private volatile boolean keepAlive = true;

    private final Queue<RedPacketOperation> waitingQueue = new ConcurrentLinkedQueue<>();
    private final Queue<RedPacketData> publicRedPacket = new ConcurrentLinkedQueue<>();
    private final Deque<RedPacketData> restrictedRedPacket = new ConcurrentLinkedDeque<>();

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

    /**
     * Retrieve the next, publicly available {@link RedPacketData}.
     * <p>
     *     Do note that this is not a typical getter method, as it violates the rule of that
     *     getter shall not mutating the internal data - calling this method will also automatically
     *     remove any instances of RedPacketData on the head that satisfies
     *     RedPacketData.isEmpty() == true.
     * </p>
     * @return The oldest {@link RedPacketData} in the waiting queue, or null is there is none
     */
    @Nullable
    public RedPacketData nextAvailableRedPacket() {
        if (publicRedPacket.isEmpty()) {
            return null;
        } else {
            while (true) {
                RedPacketData redPacket = publicRedPacket.peek();
                if (redPacket.isEmpty()) {
                    publicRedPacket.poll();
                } else {
                    return redPacket;
                }
            }
        }
    }

    /**
     * Retrieve the next available {@link RedPacketData} according to passcode.
     * <p>
     *     Do note that this is not a typical getter method, as it violates the rule of that
     *     getter shall not mutating the internal data - calling this method will also automatically
     *     remove any instances of RedPacketData on the head that satisfies
     *     RedPacketData.isEmpty() == true.
     * </p>
     * @return The newest, passcode-restricted {@link RedPacketData} in the waiting queue, or null is there is none
     */
    @Nullable
    public RedPacketData nextAvailableRedPacket(final String passcode) {
        if (restrictedRedPacket.isEmpty()) {
            return null;
        } else {
            // TODO This should work better, since the latest RedPacket should attract most attention, but we need to double check
            Iterator<RedPacketData> itr = restrictedRedPacket.descendingIterator();
            while (itr.hasNext()) {
                RedPacketData redPacket = itr.next();
                if (redPacket.isEmpty()) {
                    itr.remove();
                } else if (redPacket.getMessage().equals(passcode)) {
                    return redPacket;
                }
            }
            return null;
        }
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
