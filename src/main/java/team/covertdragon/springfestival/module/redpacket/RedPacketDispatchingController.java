/*
 * Copyright (c) 2018 CovertDragon Team.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package team.covertdragon.springfestival.module.redpacket;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public final class RedPacketDispatchingController implements Runnable {

    private volatile boolean keepAlive = true;

    private String currentRedPacketID = "";
    private final Queue<RedPacketOperation.Get> dispatchTargets = new ConcurrentLinkedQueue<>();

    @Override
    public void run() {
        while (keepAlive) {
            // TODO Control all incoming red packet distribution without blocking game running
            // TODO Thread safety. Requiring IThreadListener::addScheduledTask
        }
    }

    public boolean isKeepAlive() {
        return keepAlive;
    }

    public void setKeepAlive(boolean keepAlive) {
        // TODO Log output, for tracing & debugging
        this.keepAlive = keepAlive;
    }
}
