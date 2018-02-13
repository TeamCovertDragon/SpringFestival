/*
 * Copyright (c) 2018 CovertDragon Team.
 * Copyright (c) 2018 Contributors of SpringFestival.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package team.covertdragon.springfestival.module.redpacket;

import javax.annotation.Nullable;
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
        private final String passcode;
        public Get(UUID fromPlayer, @Nullable final String passcode) {
            super(fromPlayer);
            this.passcode = passcode;
        }
    }

    public static class Post extends RedPacketOperation {
        private final RedPacketData redPacket;

        public Post(UUID fromPlayer, RedPacketData packet) {
            super(fromPlayer);
            this.redPacket = packet;
        }

        public RedPacketData getRedPacket() {
            return redPacket;
        }
    }


}
