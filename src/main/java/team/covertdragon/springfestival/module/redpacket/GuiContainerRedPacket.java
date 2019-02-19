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
        GuiButtonImage sendRedPacket = new GuiButtonImage(0, this.guiLeft + 17, this.guiTop + 13, 25, 15, 195, 0, 0, TEXTURE_BG) {
            @Override
            public void onRelease(double mouseX, double mouseY) {
                SpringFestivalNetworkHandler.INSTANCE.sendToServer(new ClientPacketConfirmRedPacketSending());
                Minecraft.getInstance().player.closeScreen();
            }
        };
        buttons.add(sendRedPacket);
        GuiButtonToggle passcodeModeToggle = new GuiButtonToggle(1, this.guiLeft + 15, this.guiTop + 49, 14, 14, false) {
            @Override
            public void render(int mouseX, int mouseY, float partialTicks) {
                if (passcodeMode) {
                    super.render(mouseX, mouseY, partialTicks);
                }
            }

            @Override
            public void onRelease(double mouseX, double mouseY) {
                passcodeMode = !passcodeMode;
                SpringFestivalNetworkHandler.INSTANCE.sendToServer(new ClientPacketTogglePasscodeMode(GuiContainerRedPacket.this.passcodeMode));
            }
        };
        passcodeModeToggle.initTextureValues(195, 17, 0, 0, TEXTURE_BG);
        buttons.add(passcodeModeToggle);

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

        NBTTagCompound data = Minecraft.getInstance().player.getHeldItemMainhand().getTag();
        if (data != null) {
            this.receiver.setText(data.getString("receiver_name"));
            this.message.setText(data.getString("message"));
        }
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int mouseButton) {
        this.receiver.mouseClicked(mouseX, mouseY, mouseButton);
        this.message.mouseClicked(mouseX, mouseY, mouseButton);
        return super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    @Override
    public void render(int mouseX, int mouseY, float partialTick) {
        this.drawDefaultBackground();
        super.render(mouseX, mouseY, partialTick);
        this.renderHoveredToolTip(mouseX, mouseY);
        GlStateManager.disableLighting();
        GlStateManager.disableBlend();
        this.receiver.drawTextField(mouseX, mouseY, partialTick);
        this.message.drawTextField(mouseX, mouseY, partialTick);
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        super.drawGuiContainerForegroundLayer(mouseX, mouseY);
        for (GuiButton button : this.buttons) {
            if (button.isMouseOver()) {
                button.drawButtonForegroundLayer(mouseX, mouseY);
            }
        }
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        this.mc.getTextureManager().bindTexture(TEXTURE_BG);
        GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
    }


    @Override
    public boolean charTyped(char typedChar, int keyCode) {
        if (this.receiver.charTyped(typedChar, keyCode)) {
            SpringFestivalNetworkHandler.INSTANCE.sendToServer(new ClientPacketUpdateRedPacketReceiver(receiver.getText()));
            return true;
        } else if (this.message.charTyped(typedChar, keyCode)) {
            SpringFestivalNetworkHandler.INSTANCE.sendToServer(new ClientPacketUpdateRedPacketMessage(message.getText()));
            return true;
        } else {
            return super.charTyped(typedChar, keyCode);
        }
    }
}
