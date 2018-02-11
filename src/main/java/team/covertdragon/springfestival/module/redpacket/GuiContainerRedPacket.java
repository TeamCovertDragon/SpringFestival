/*
 * Copyright (c) 2018 CovertDragon Team.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package team.covertdragon.springfestival.module.redpacket;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiButtonToggle;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.items.IItemHandlerModifiable;
import team.covertdragon.springfestival.SpringFestivalConstants;

import java.io.IOException;

public class GuiContainerRedPacket extends GuiContainer {

    private static final ResourceLocation TEXTURE_BG = new ResourceLocation(SpringFestivalConstants.MOD_ID, "textures/gui/red_packet.png");

    private GuiButtonToggle passcodeModeToggle;
    private GuiButton sendRedPacket;
    private GuiTextField message;

    public GuiContainerRedPacket(InventoryPlayer player, IItemHandlerModifiable inv) {
        super(new ContainerRedPacket(player, inv));
        this.xSize = 194;
        this.ySize = 176;
    }

    @Override
    public void initGui() {
        super.initGui();
        this.passcodeModeToggle = new GuiButtonToggle(0, 0, 0, 24, 16, false);
        // TODO Passcode mode toggle: u = 195, v = 17
        passcodeModeToggle.initTextureValues(195, 17, 0, 0, TEXTURE_BG);
        buttonList.add(passcodeModeToggle);
        // TODO Envelop button: u = 195, v = 0
        this.sendRedPacket = new GuiButton(0, 0, 0, 24, 16, "");
        buttonList.add(sendRedPacket);
        // TODO Correct the coordinate
        this.message = new GuiTextField(2, this.fontRenderer, 0, 0, 60, 16);
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        this.message.mouseClicked(mouseX, mouseY, mouseButton);
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        super.actionPerformed(button);
        // TODO Trigger red packet sending and close GUI
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTick) {
        this.drawDefaultBackground();
        super.drawScreen(mouseX, mouseY, partialTick);
        this.renderHoveredToolTip(mouseX, mouseY);
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
}
