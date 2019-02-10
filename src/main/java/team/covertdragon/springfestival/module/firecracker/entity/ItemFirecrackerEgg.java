/*
 * Copyright (c) 2018 CovertDragon Team.
 * Copyright (c) 2018 Contributors of SpringFestival.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package team.covertdragon.springfestival.module.firecracker.entity;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;
import team.covertdragon.springfestival.SpringFestivalConstants;
import team.covertdragon.springfestival.module.firecracker.FirecrackerRegistry;

// TODO: Firecharge?
public class ItemFirecrackerEgg extends Item {

    public ItemFirecrackerEgg() {
        this.setTranslationKey(SpringFestivalConstants.MOD_ID + ".firecracker_egg");
        this.setCreativeTab(SpringFestivalConstants.CREATIVE_TAB);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
        ItemStack held = player.getHeldItem(hand);
        if (!player.isCreative()) {
            held.shrink(1);
        }

        world.playSound(null, player.posX, player.posY, player.posZ, FirecrackerRegistry.soundFirecrackerThrow, SoundCategory.NEUTRAL, 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));

        if (!world.isRemote) {
            EntityFirecracker firecracker = new EntityFirecracker(world, player.posX, player.posY, player.posZ, player);
            firecracker.shoot(player, player.rotationPitch, player.rotationYaw, 0.0F, 0.943F, 0.233F);
            world.spawnEntity(firecracker);
            player.addStat(StatList.getObjectUseStats(this));
        }

        return new ActionResult<>(EnumActionResult.SUCCESS, held);
    }
}