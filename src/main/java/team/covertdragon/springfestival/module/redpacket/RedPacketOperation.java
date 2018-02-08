/*
 * Copyright (c) 2018 CovertDragon Team.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package team.covertdragon.springfestival.module.redpacket;

// TODO Re-evaluate this class, maybe it's a bad design
public abstract class RedPacketOperation {

    private final String redPacketID;

    public RedPacketOperation(final String redPacketID) {
        this.redPacketID = redPacketID;
    }

    public static class Get extends RedPacketOperation {
        public Get(String redPacketID) {
            super(redPacketID);
        }
    }

    public static class Post extends RedPacketOperation {
        public Post(String redPacketID) {
            super(redPacketID);
        }
    }


}
