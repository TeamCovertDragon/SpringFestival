/*
 * Copyright (c) 2018 CovertDragon Team.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package team.covertdragon.springfestival.module.redpacket;

import net.minecraft.client.gui.toasts.GuiToast;
import net.minecraft.client.gui.toasts.IToast;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import team.covertdragon.springfestival.SpringFestivalConstants;

import javax.annotation.Nonnull;
import java.util.List;

@SideOnly(Side.CLIENT)
public class RedPacketToast implements IToast {

    private static final ResourceLocation TEXTURE_RED_PACKET_TOAST = new ResourceLocation(SpringFestivalConstants.MOD_ID, "textures/gui/toast/red_packet.png");

    private String packetName;
    private String packetMessage;
    private final boolean passcodeMode;

    RedPacketToast(final String name, final String message, final boolean hasPasscode) {
        this.packetName = name;
        this.packetMessage = message;
        this.passcodeMode = hasPasscode;
    }

    @Override
    public Visibility draw(GuiToast toastGui, long delta) {
        if (delta < 2000) {
            toastGui.getMinecraft().getTextureManager().bindTexture(TEXTURE_RED_PACKET_TOAST);
            GlStateManager.color(1.0F, 1.0F, 1.0F);
            // TODO Put a length restriction, otherwise player will complain
            final List<String> messages = toastGui.getMinecraft().fontRenderer.listFormattedStringToWidth(this.packetMessage, 125);
            if (messages.size() == 1) {
                // TODO Draw words
            } else {
                // TODO Draw scrolling words (wtf?)
            }
            return Visibility.SHOW;
        } else {
            return Visibility.HIDE;
        }
    }

    @Nonnull
    @Override
    public Object getType() {
        return NO_TOKEN;
    }

}
