/*
 * Copyright (c) 2018 CovertDragon Team.
 * Copyright (c) 2018 Contributors of SpringFestival.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package team.covertdragon.springfestival.module.fortune.potion;

import net.minecraft.client.Minecraft;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
import team.covertdragon.springfestival.SpringFestivalConstants;

public class PotionFortunate extends Potion {
    public PotionFortunate() {
        super(false, 0x7F0000);
    }

    @Override
    public void renderInventoryEffect(int x, int y, PotionEffect effect, Minecraft mc) {
        mc.getTextureManager().bindTexture(new ResourceLocation(SpringFestivalConstants.MOD_ID, "textures/gui/potion.png"));
        mc.currentScreen.drawTexturedModalRect(x + 6, y + 7, 0, 0, 18, 18);
    }

    @Override
    public void renderHUDEffect(int x, int y, PotionEffect effect, Minecraft mc, float alpha) {
        mc.getTextureManager().bindTexture(new ResourceLocation(SpringFestivalConstants.MOD_ID, "textures/gui/potion.png"));
        mc.ingameGUI.drawTexturedModalRect(x + 3, y + 3, 0, 0, 18, 18);
    }
}
