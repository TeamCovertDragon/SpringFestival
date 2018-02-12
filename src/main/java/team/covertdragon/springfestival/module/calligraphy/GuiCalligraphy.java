/*
 * Copyright (c) 2018 CovertDragon Team.
 * Copyright (c) 2018 Contributors of SpringFestival.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package team.covertdragon.springfestival.module.calligraphy;

import net.minecraft.client.gui.GuiScreen;

import java.io.IOException;

public class GuiCalligraphy extends GuiScreen {

    private transient int strokeBeginX, strokeBeginY, strokeRadius;

    /**
     * The default 256 by 256 pixels canvas, mimicking the red paper for writing words.
     * <p>
     * A boolean value of true indicates a black (RGB [0, 0, 0]) pixel; whereas a boolean
     * value of false indicates a white (RGB [255, 255, 255]) pixel. Because it's hand-writing
     * with ink and pen, we only need to consider about 2 colors.
     * </p>
     *
     */
    // This field is here for fast response on client side. We have to manually sync it to server...
    private boolean canvas[][] = new boolean[256][256];

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        this.strokeBeginX = mouseX;
        this.strokeBeginY = mouseY;
    }

    @Override
    protected void mouseClickMove(int mouseX, int mouseY, int clickedMouseButton, long timeSinceLastClick) {

    }

    @Override
    protected void mouseReleased(int mouseX, int mouseY, int state) {
        this.strokeBeginX = -1;
        this.strokeBeginY = -1;
        this.strokeRadius = 0;
    }
}
