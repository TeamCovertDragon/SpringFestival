/*
 * Copyright (c) 2018 CovertDragon Team.
 * Copyright (c) 2018 Contributors of SpringFestival.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package team.covertdragon.springfestival.module.redpacket;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.tree.LiteralCommandNode;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.common.util.FakePlayer;
import team.covertdragon.springfestival.SpringFestivalConstants;

public class CommandRedPacket {

    public CommandRedPacket(CommandDispatcher<CommandSource> dispatcher) {
        LiteralCommandNode<CommandSource> redPacketCommand = dispatcher.register(Commands.literal("redpacket")
                .requires(CommandRedPacket::sanityCheckCommandSender)
                .then(Commands.argument("password", StringArgumentType.greedyString()))
                .executes(source -> execute(source.getSource().asPlayer(), source.getArgument("password", String.class))));
        for (String alias : new String[] { "hongbao", "rp", "hb" }) {
            dispatcher.register(Commands.literal(alias)
                    .requires(CommandRedPacket::sanityCheckCommandSender)
                    .redirect(redPacketCommand));
        }
    }

    private static boolean sanityCheckCommandSender(CommandSource source) {
        try {
            return !(source.asPlayer() instanceof FakePlayer);
        } catch (CommandSyntaxException e) {
            SpringFestivalConstants.logger.warn("There is an non-human candidate attempting to grab red packet! Offender: {}", source);
            return false;
        }
    }

    private int execute(EntityPlayerMP player, String password) {
        if (ModuleRedPacket.RED_PACKET_CONTROLLER.enqueueOperation(new RedPacketOperation.Get(player.getUniqueID(), password))) {
            player.sendMessage(new TextComponentString("Successfully got this red packet!")); // TODO Internationalization
            return Command.SINGLE_SUCCESS;
        } else {
            player.sendMessage(new TextComponentString("Failed on getting this red packet!")); // TODO Internationalization
            // TODO (3TUSK): What is the appropriate return value here?
            //   Brigadier uses 1 for success which differs from Unix using 0.
            //   For now we use -1 which is common in Unix programs.
            return -1;
        }
    }
}
