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
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;
import team.covertdragon.springfestival.SpringFestivalConstants;
import team.covertdragon.springfestival.module.firecracker.FirecrackerRegistry;

// TODO: Firecharge?
public class ItemFirecrackerEgg extends Item {

    public ItemFirecrackerEgg()
    {
        this.setRegistryName(SpringFestivalConstants.MOD_ID, "firecracker_egg");
        this.setUnlocalizedName(SpringFestivalConstants.MOD_ID + ".firecracker_egg");
        this.setCreativeTab(SpringFestivalConstants.CREATIVE_TAB);
    }

    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn)
    {
        ItemStack itemstack = playerIn.getHeldItem(handIn);

        if (!playerIn.capabilities.isCreativeMode)
        {
            itemstack.shrink(1);
        }

        worldIn.playSound(null, playerIn.posX, playerIn.posY, playerIn.posZ, FirecrackerRegistry.soundFirecrackerThrow, SoundCategory.NEUTRAL, 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));

        if (!worldIn.isRemote)
        {
            EntityFirecracker entityfirecracker = new EntityFirecracker(worldIn, playerIn.posX, playerIn.posY, playerIn.posZ, playerIn);
            entityfirecracker.shoot(playerIn, playerIn.rotationPitch, playerIn.rotationYaw, 0.0F, 0.943F, 0.233F);
            worldIn.spawnEntity(entityfirecracker);
        }

        playerIn.addStat(StatList.getObjectUseStats(this));
        return new ActionResult<>(EnumActionResult.SUCCESS, itemstack);
    }
}