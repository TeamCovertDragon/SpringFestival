/*
 * Copyright (c) 2018 CovertDragon Team.
 * Copyright (c) 2018 Contributors of SpringFestival.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package team.covertdragon.springfestival.module.food;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;
import team.covertdragon.springfestival.SpringFestivalConstants;
import team.covertdragon.springfestival.SpringFestivalModuleController;
import team.covertdragon.springfestival.module.fortune.FortuneRegistry;

public class ItemSpringFestivalFood extends ItemFood {

    private final int itemUseDuration;

    ItemSpringFestivalFood(int amount, float saturation, Properties properties) {
        this(amount, saturation, 32, properties);
    }

    ItemSpringFestivalFood(int amount, float saturation, int itemUseDuration, Properties properties) {
        super(amount, saturation, false, properties);
        this.itemUseDuration = itemUseDuration;
    }

    @Override
    public int getUseDuration(ItemStack stack) {
        return itemUseDuration;
    }

    @Override
    protected void onFoodEaten(ItemStack stack, World worldIn, EntityPlayer player) {
        super.onFoodEaten(stack, worldIn, player);
        //Fortune module integration
        if (SpringFestivalModuleController.isModuleLoaded("fortune")) {
            player.addPotionEffect(new PotionEffect(FortuneRegistry.potionFortunate, 200));
        }
    }
}
