/*
 * Copyright (c) 2018 CovertDragon Team.
 * Copyright (c) 2018 Contributors of SpringFestival.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package team.covertdragon.springfestival.module.redpacket;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.toasts.GuiToast;
import net.minecraft.client.gui.toasts.IToast;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import team.covertdragon.springfestival.SpringFestivalConstants;

import javax.annotation.Nonnull;

@OnlyIn(Dist.CLIENT)
public class RedPacketToast implements IToast {

    private static final ResourceLocation TEXTURE_RED_PACKET_TOAST = new ResourceLocation(SpringFestivalConstants.MOD_ID, "textures/gui/toast/red_packet.png");

    private static final ItemStack ICON = new ItemStack(ModuleRedPacket.RED_PACKET);

    private final String publisherName;
    private final String packetMessage;
    private final boolean passcodeMode;
    private transient boolean hasPlayedSound = false;

    RedPacketToast(final String publisher, final String message, final boolean hasPasscode) {
        this.publisherName = publisher;
        this.packetMessage = message;
        this.passcodeMode = hasPasscode;
    }

    @Override
    public Visibility draw(GuiToast toastGui, long delta) {
        if (delta < 10000) {
            Minecraft mc = toastGui.getMinecraft();
            mc.getTextureManager().bindTexture(TEXTURE_RED_PACKET_TOAST);
            GlStateManager.color3f(1.0F, 1.0F, 1.0F);
            mc.getItemRenderer().renderItemIntoGUI(ICON, 8, 8);
            FontRenderer fontRenderer = mc.fontRenderer;
            final String title = I18n.format(passcodeMode ? "toast.redpacket.publish.passcode" : "toast.redpacket.publish.regular", publisherName);
            fontRenderer.drawString(title, 30, 7, 0xE2BC36); // wtf it is color?
            fontRenderer.drawString(packetMessage, 30, 18, 0xFF0000); // Pure red
            if (!hasPlayedSound) {
                hasPlayedSound = true;
                // TODO Dang! Dang! Dang! A wild red packet appeared!
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
