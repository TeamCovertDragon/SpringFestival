/*
 * Copyright (c) 2018 CovertDragon Team.
 * Copyright (c) 2018 Contributors of SpringFestival.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package team.covertdragon.springfestival.module.redpacket;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import team.covertdragon.springfestival.SpringFestival;
import team.covertdragon.springfestival.internal.capabilities.ItemStackInventoryProvider;

import javax.annotation.Nullable;
import java.util.List;

public class ItemRedPacket extends Item {

    public ItemRedPacket() {
        this.setMaxStackSize(1);
        this.setMaxDamage(0);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        NBTTagCompound data = stack.getTagCompound();
        if (data != null) {
            tooltip.add("Owned by " + data.getString("owner_name"));
            tooltip.add("Receiver: " + data.getString("receiver_name"));
            tooltip.add(data.getString("message"));
            tooltip.add(data.getBoolean("passcode") ? "口令红包" : "手气红包");
        }
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
        if (!worldIn.isRemote && handIn == EnumHand.MAIN_HAND) { // Somehow realistic, more for simplicity
            ItemStack s = playerIn.getHeldItemMainhand();
            if (s.getItem() == ModuleRedPacket.RED_PACKET) {
                NBTTagCompound data = s.getTagCompound();
                if (data == null) {
                    data = new NBTTagCompound();
                    s.setTagCompound(data);
                }
                if (data.hasKey("owner", data.getId())) {
                    if (!NBTUtil.getUUIDFromTag(data.getCompoundTag("owner")).equals(playerIn.getUniqueID())) {
                        return ActionResult.newResult(EnumActionResult.FAIL, playerIn.getHeldItem(handIn));
                    }
                } else {
                    data.setTag("owner", NBTUtil.createUUIDTag(playerIn.getUniqueID()));
                    data.setString("owner_name", playerIn.getName());
                }
                playerIn.openGui(SpringFestival.getInstance(), 0, worldIn, (int) playerIn.posX, (int) playerIn.posY, (int) playerIn.posZ);
            }
        }
        return ActionResult.newResult(EnumActionResult.SUCCESS, playerIn.getHeldItem(handIn));
    }
}
