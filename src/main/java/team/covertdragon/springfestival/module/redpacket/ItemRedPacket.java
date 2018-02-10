/*
 * Copyright (c) 2018 CovertDragon Team.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package team.covertdragon.springfestival.module.redpacket;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import team.covertdragon.springfestival.internal.capabilities.ItemStackInventoryProvider;

import javax.annotation.Nullable;

public class ItemRedPacket extends Item {

    public ItemRedPacket() {
        this.setMaxStackSize(1);
        this.setMaxDamage(0);
    }

    @Nullable
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable NBTTagCompound nbt) {
        // Number "8" pronounces similarly with "发", implying "becoming rich" when the context is Spring Festival.
        // TODO A smaller sized one, like 6?
        return new ItemStackInventoryProvider(8);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
        // TODO Open the Red Packet GUI
        return ActionResult.newResult(EnumActionResult.PASS, playerIn.getHeldItem(handIn));
    }
}
