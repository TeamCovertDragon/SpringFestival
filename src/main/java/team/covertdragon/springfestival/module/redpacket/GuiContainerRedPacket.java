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
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiButtonImage;
import net.minecraft.client.gui.GuiButtonToggle;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.items.IItemHandlerModifiable;
import team.covertdragon.springfestival.SpringFestivalConstants;
import team.covertdragon.springfestival.internal.network.SpringFestivalNetworkHandler;

import java.io.IOException;

public class GuiContainerRedPacket extends GuiContainer {

    private static final ResourceLocation TEXTURE_BG = new ResourceLocation(SpringFestivalConstants.MOD_ID, "textures/gui/red_packet.png");

    private GuiButtonToggle passcodeModeToggle;
    private GuiButton sendRedPacket;
    private GuiTextField receiver;
    private GuiTextField message;
    private boolean passcodeMode;

    public GuiContainerRedPacket(InventoryPlayer player, IItemHandlerModifiable inv) {
        super(new ContainerRedPacket(player, inv));
        this.xSize = 194;
        this.ySize = 176;
    }

    @Override
    public void initGui() {
        super.initGui();
        this.sendRedPacket = new GuiButtonImage(0, this.guiLeft + 17, this.guiTop + 13, 25, 15, 195, 0, 0, TEXTURE_BG);
        buttonList.add(sendRedPacket);
        this.passcodeModeToggle = new GuiButtonToggle(1, this.guiLeft + 15, this.guiTop + 49, 14, 14, false) {
            @Override
            public void drawButton(Minecraft mc, int mouseX, int mouseY, float partialTicks) {
                if (passcodeMode) {
                    super.drawButton(mc, mouseX, mouseY, partialTicks);
                }
            }
        };
        passcodeModeToggle.initTextureValues(195, 17, 0, 0, TEXTURE_BG);
        buttonList.add(passcodeModeToggle);

        this.receiver = new GuiTextField(2, this.fontRenderer, this.guiLeft + 46 + 4, this.guiTop + 13 + 3, 133, 15);
        this.receiver.setTextColor(-1);
        this.receiver.setDisabledTextColour(-1);
        this.receiver.setEnableBackgroundDrawing(false);
        this.receiver.setCanLoseFocus(true);
        this.receiver.setEnabled(true);
        this.message = new GuiTextField(3, this.fontRenderer, this.guiLeft + 17 + 4, this.guiTop + 31 + 3, 162, 15);
        this.message.setTextColor(-1);
        this.message.setEnableBackgroundDrawing(false);
        this.message.setCanLoseFocus(true);
        this.message.setEnabled(true);

        NBTTagCompound data = Minecraft.getMinecraft().player.getHeldItemMainhand().getTagCompound();
        if (data != null) {
            this.receiver.setText(data.getString("receiver_name"));
            this.message.setText(data.getString("message"));
        }
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        this.receiver.mouseClicked(mouseX, mouseY, mouseButton);
        this.message.mouseClicked(mouseX, mouseY, mouseButton);
    }

    @Override
    protected void actionPerformed(GuiButton button) {
        if (button.enabled) { // Dirty implementation. No time to waste, just make it works first!
            if (button.id == 0) {
                // TODO Save all data and send a packet to server, to tell server that this red packet is ready to be enqueued
                SpringFestivalNetworkHandler.INSTANCE.sendToServer(new ClientPacketConfirmRedPacketSending());
                this.mc.player.closeScreen();
            } else if (button.id == 1) {
                passcodeMode = !passcodeMode;
                SpringFestivalNetworkHandler.INSTANCE.sendToServer(new ClientPacketTogglePasscodeMode(this.passcodeMode));
            }
        }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTick) {
        this.drawDefaultBackground();
        super.drawScreen(mouseX, mouseY, partialTick);
        this.renderHoveredToolTip(mouseX, mouseY);
        GlStateManager.disableLighting();
        GlStateManager.disableBlend();
        this.receiver.drawTextBox();
        this.message.drawTextBox();
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        super.drawGuiContainerForegroundLayer(mouseX, mouseY);
        for (GuiButton button : this.buttonList) {
            if (button.isMouseOver()) {
                button.drawButtonForegroundLayer(mouseX, mouseY);
            }
        }
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        this.mc.getTextureManager().bindTexture(TEXTURE_BG);
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        this.drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        if (this.receiver.textboxKeyTyped(typedChar, keyCode)) {
            SpringFestivalNetworkHandler.INSTANCE.sendToServer(new ClientPacketUpdateRedPacketReceiver(receiver.getText()));
        } else if (this.message.textboxKeyTyped(typedChar, keyCode)) {
            SpringFestivalNetworkHandler.INSTANCE.sendToServer(new ClientPacketUpdateRedPacketMessage(message.getText()));
        } else {
            super.keyTyped(typedChar, keyCode);
        }
    }
}
