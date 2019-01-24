/*
 * Copyright (c) 2018 CovertDragon Team.
 * Copyright (c) 2018 Contributors of SpringFestival.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package team.covertdragon.springfestival.trick;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiErrorScreen;
import net.minecraft.client.resources.I18n;
import net.minecraftforge.fml.client.CustomModLoadingErrorDisplayException;

import java.util.List;

/**
 * An implementation of {@link CustomModLoadingErrorDisplayException} that is thrown
 * when the time is in Spring Festival season and corresponding config option is
 * enabled, for sake of reminding player the fact that Spring Festival is a festival
 * for family reunion and there is no reason to simply stick on your desktop or laptop
 * when the time is intended for family.
 */
final class PleaseSpendMoreTimeWithYourFamilyException extends CustomModLoadingErrorDisplayException {
    @Override
    public void initGui(GuiErrorScreen errorScreen, FontRenderer fontRenderer) {}

    @Override
    public void drawScreen(GuiErrorScreen errorScreen, FontRenderer fontRenderer, int mouseRelX, int mouseRelY, float tickTime) {
        final List<String> text = fontRenderer.listFormattedStringToWidth(I18n.format("error.springfestival.spend_time_with_your_family"), errorScreen.width - 80);
        int yOffset = 50;
        for (String sentence : text) {
            errorScreen.drawCenteredString(fontRenderer, sentence, errorScreen.width / 2, yOffset, 0xFFFFFF);
            yOffset += 10;
        }
        // TODO Display the way of disabling this feature for those who don't have life
    }
}
