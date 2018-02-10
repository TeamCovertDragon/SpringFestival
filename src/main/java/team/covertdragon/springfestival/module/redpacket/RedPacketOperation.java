/*
 * Copyright (c) 2018 CovertDragon Team.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package team.covertdragon.springfestival.module.redpacket;

import java.util.UUID;

public abstract class RedPacketOperation {

    private final long timestamp = System.currentTimeMillis();

    private final UUID origin;

    /**
     * @param fromPlayer The UUID of that guy (i.e. EntityPlayer) who creates this operation
     */
    public RedPacketOperation(UUID fromPlayer) {
        this.origin = fromPlayer;
    }

    public static class Get extends RedPacketOperation {
        private final UUID packetOwner;
        private final long packetTimestamp;
        public Get(UUID fromPlayer, UUID redPacketOwner, long timestamp) {
            super(fromPlayer);
            this.packetOwner = redPacketOwner;
            this.packetTimestamp = timestamp;
        }
    }

    public static class Post extends RedPacketOperation {
        private final RedPacketData redPacket;
        public Post(UUID fromPlayer, RedPacketData packet) {
            super(fromPlayer);
            this.redPacket = packet;
        }
    }


}
