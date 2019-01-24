/*
 * Copyright (c) 2018 CovertDragon Team.
 * Copyright (c) 2018 Contributors of SpringFestival.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package team.covertdragon.springfestival.redpacket;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.FMLCommonHandler;
import org.apache.commons.lang3.StringUtils;
import team.covertdragon.springfestival.SpringFestival;
import team.covertdragon.springfestival.SpringFestivalConstants;

import javax.annotation.Nullable;
import java.util.UUID;

public abstract class RedPacketOperation {

    protected final UUID origin;

    /**
     * @param fromPlayer The UUID of that guy (i.e. EntityPlayer) who creates this operation
     */
    public RedPacketOperation(UUID fromPlayer) {
        this.origin = fromPlayer;
    }

    public abstract void process();

    static class Get extends RedPacketOperation {
        private final String passcode;
        Get(UUID fromPlayer, @Nullable final String passcode) {
            super(fromPlayer);
            this.passcode = passcode;
        }

        @Override
        public void process() {
            EntityPlayer player = SpringFestival.proxy.getPlayerByUUID(this.origin);
            if (player != null) {
                RedPacketData redPacket;
                if (StringUtils.isEmpty(passcode)) {
                    redPacket = ModuleRedPacket.RED_PACKET_CONTROLLER.nextAvailableRedPacket();
                } else {
                    redPacket = ModuleRedPacket.RED_PACKET_CONTROLLER.nextAvailableRedPacket(passcode);
                }
                if (redPacket != null) {
                    FMLCommonHandler.instance().getMinecraftServerInstance().addScheduledTask(new RedPacketDistributionTask(player, redPacket.randomSplit()));
                }
            } else {
                SpringFestivalConstants.logger.warn("Looks like the player {} has given up. Voiding this operation...", origin);
            }
        }

        private static final class RedPacketDistributionTask implements Runnable {

            private final EntityPlayer player;
            private final RedPacketData packet;

            RedPacketDistributionTask(final EntityPlayer player, final RedPacketData data) {
                this.player = player;
                this.packet = data;
            }

            @Override
            public void run() {
                final ItemStack item = new ItemStack(ModuleRedPacket.RED_PACKET);
                item.setTagCompound(packet.serializeNBT());
                final EntityItem entityItem = new EntityItem(player.world, player.posX, player.posY, player.posZ, item);
                entityItem.setNoPickupDelay();
                player.world.spawnEntity(entityItem);
            }
        }
    }

    public static class Post extends RedPacketOperation {
        private final RedPacketData redPacket;

        Post(UUID fromPlayer, RedPacketData packet) {
            super(fromPlayer);
            this.redPacket = packet;
        }

        @Override
        public void process() {
            if (!ModuleRedPacket.RED_PACKET_CONTROLLER.enqueueNewRedPacket(this.redPacket)) {
                SpringFestivalConstants.logger.error("Failed to push new red packet to available queue, report immediately");
            }
        }
    }


}
