/*
 * Copyright (c) 2018 CovertDragon Team.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package team.covertdragon.springfestival.module.redpacket;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.toasts.GuiToast;
import net.minecraft.client.gui.toasts.IToast;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import team.covertdragon.springfestival.SpringFestivalConstants;

import javax.annotation.Nonnull;

@SideOnly(Side.CLIENT)
@Mod.EventBusSubscriber(modid = SpringFestivalConstants.MOD_ID, value = Side.CLIENT)
public class RedPacketToast implements IToast {
    @Override
    public Visibility draw(GuiToast toastGui, long delta) {
        // TODO Draw the toast
        return Visibility.HIDE;
    }

    @Nonnull
    @Override
    public Object getType() {
        return NO_TOKEN;
    }

    //@SubscribeEvent
    public static void onRedPacketDispatched() {
        // TODO Stub!
        Minecraft.getMinecraft().getToastGui().add(new RedPacketToast());
    }
}
