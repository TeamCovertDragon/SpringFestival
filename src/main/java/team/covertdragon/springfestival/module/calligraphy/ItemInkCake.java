/*
 * Copyright (c) 2018 CovertDragon Team.
 * Copyright (c) 2018 Contributors of SpringFestival.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package team.covertdragon.springfestival.module.calligraphy;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

/**
 * ItemInkCake represents the item type "Ink Cake" (https://en.wikipedia.org/wiki/Inkstick).
 */
public class ItemInkCake extends Item {

    public ItemInkCake(Properties properties) {
        super(properties);
    }

    // TODO: recipe. Probably goes with coal, slime ball (glue replacement, or we have to process leather?), spurge wood and water.

    // TODO: ore dictionary entry. High-quality ink should be equivalent to black dye, thus dyeBlack. (No, there is no realism)

    // TODO: throwable? This is why it gets separate class. ItemSnowball cannot be reused as it hard-codes EntitySnowball in onItemRightClick
    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
        ItemStack itemHeld = player.getHeldItem(hand);
        itemHeld.shrink(1);
        return ActionResult.newResult(EnumActionResult.SUCCESS, itemHeld);
    }
}
