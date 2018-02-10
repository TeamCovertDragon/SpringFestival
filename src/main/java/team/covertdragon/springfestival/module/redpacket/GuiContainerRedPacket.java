/*
 * Copyright (c) 2018 CovertDragon Team.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package team.covertdragon.springfestival.module.redpacket;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.items.IItemHandlerModifiable;
import team.covertdragon.springfestival.SpringFestivalConstants;

public class GuiContainerRedPacket extends GuiContainer {

    public GuiContainerRedPacket(InventoryPlayer player, IItemHandlerModifiable inv) {
        super(new ContainerRedPacket(player, inv));
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        this.mc.getTextureManager().bindTexture(new ResourceLocation(SpringFestivalConstants.MOD_ID, "textures/gui/red_packet.png")); // TODO the texture
    }
}
