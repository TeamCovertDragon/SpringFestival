/*
 * Copyright (c) 2018 CovertDragon Team.
 * Copyright (c) 2018 Contributors of SpringFestival.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package team.covertdragon.springfestival.food;

import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import team.covertdragon.springfestival.SpringFestivalConstants;

public class ItemSpringFestivalFood extends ItemFood {
    public final int itemUseDuration;
    
    ItemSpringFestivalFood(int amount, float saturation) {
        this(amount, saturation, 32);
    }
    
    ItemSpringFestivalFood(int amount, float saturation, int itemUseDuration) {
        super(amount, saturation, false);
        setCreativeTab(SpringFestivalConstants.CREATIVE_TAB);
        this.itemUseDuration = itemUseDuration;
    }
    
    public int getMaxItemUseDuration(ItemStack stack)
    {
        return itemUseDuration;
    }
}
