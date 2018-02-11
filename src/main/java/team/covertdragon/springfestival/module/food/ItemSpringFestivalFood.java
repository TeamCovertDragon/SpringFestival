/*
 * Copyright (c) 2018 CovertDragon Team.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package team.covertdragon.springfestival.module.food;

import net.minecraft.item.ItemFood;
import team.covertdragon.springfestival.SpringFestivalConstants;

public class ItemSpringFestivalFood extends ItemFood {
    public ItemSpringFestivalFood(int amount, float saturation) {
        super(amount, saturation, false);
        setCreativeTab(SpringFestivalConstants.CREATIVE_TAB);
    }
}
