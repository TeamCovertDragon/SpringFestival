/*
 * Copyright (c) 2018 CovertDragon Team.
 * Copyright (c) 2018 Contributors of SpringFestival.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package team.covertdragon.springfestival.redpacket;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.common.util.FakePlayer;
import team.covertdragon.springfestival.SpringFestivalConstants;

import java.util.Arrays;
import java.util.List;

public class CommandRedPacket extends CommandBase {

    @Override
    public String getName() {
        return "redpacket";
    }

    @Override
    public List<String> getAliases() {
        return Arrays.asList("hongbao", "rp", "hb");
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return "/redpacket <optional pass-code>";
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 0;
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) {
        if (sender instanceof EntityPlayer && !(sender instanceof FakePlayer)) {
            final String passcode = args.length > 0 ? args[1] : null;
            if (ModuleRedPacket.RED_PACKET_CONTROLLER.enqueueOperation(new RedPacketOperation.Get(((EntityPlayer) sender).getUniqueID(), passcode))) {
                sender.sendMessage(new TextComponentString("Successfully got this red packet!")); // TODO Internationalization
            } else {
                sender.sendMessage(new TextComponentString("Failed on getting this red packet!")); // TODO Internationalization
            }
        } else {
            SpringFestivalConstants.logger.warn("There is an non-human candidate attempting to grab red packet! Offender: {}", sender);
        }
    }
}
