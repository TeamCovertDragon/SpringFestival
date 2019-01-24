/*
 * Copyright (c) 2018 CovertDragon Team.
 * Copyright (c) 2018 Contributors of SpringFestival.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package team.covertdragon.springfestival.fortune.tools;

import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import team.covertdragon.springfestival.SpringFestivalConstants;
import team.covertdragon.springfestival.internal.network.SpringFestivalNetworkHandler;
import team.covertdragon.springfestival.fortune.FortuneClientHelper;
import team.covertdragon.springfestival.fortune.FortuneNetwork;

import javax.annotation.Nullable;
import java.util.List;

public class FortuneStone extends Item {
    public FortuneStone() {
        setRegistryName(SpringFestivalConstants.MOD_ID, "fortune_stone");
        setUnlocalizedName(SpringFestivalConstants.MOD_ID + ".fortune_stone");
        setCreativeTab(SpringFestivalConstants.CREATIVE_TAB);
        setMaxStackSize(1);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        super.addInformation(stack, worldIn, tooltip, flagIn);
        if (stack.getItemDamage() == 233) {
            SpringFestivalNetworkHandler.INSTANCE.sendToServer(new FortuneNetwork.PacketRequestFortuneValue());
            tooltip.add(I18n.format("tooltip.springfestival.fv", FortuneClientHelper.fortune_value));
        } else {
            tooltip.add(I18n.format("tooltip.springfestival.right_click_to_active"));
        }
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer player, EnumHand handIn) {
        ItemStack stack = player.getHeldItem(handIn);
        if (stack.getItemDamage() == 233)
            return new ActionResult<>(EnumActionResult.FAIL, stack);
        stack.setItemDamage(233);
        return new ActionResult<>(EnumActionResult.SUCCESS, stack);
    }
}
