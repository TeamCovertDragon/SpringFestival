/*
 * Copyright (c) 2018 CovertDragon Team.
 * Copyright (c) 2018 Contributors of SpringFestival.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package team.covertdragon.springfestival.module.fortune.tools;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import team.covertdragon.springfestival.SpringFestivalConstants;
import team.covertdragon.springfestival.internal.network.SpringFestivalNetworkHandler;
import team.covertdragon.springfestival.module.fortune.FortuneClientHelper;
import team.covertdragon.springfestival.module.fortune.FortuneNetwork;

import javax.annotation.Nullable;
import java.util.List;

public class FortuneStone extends Item {

    public FortuneStone(Properties properties) {
        super(properties);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        super.addInformation(stack, worldIn, tooltip, flagIn);
        if (this.getDamage(stack) == 233) {
            SpringFestivalNetworkHandler.INSTANCE.sendToServer(new FortuneNetwork.PacketRequestFortuneValue());
            tooltip.add(new TextComponentTranslation("tooltip.springfestival.fv", FortuneClientHelper.fortune_value));
        } else {
            tooltip.add(new TextComponentTranslation("tooltip.springfestival.right_click_to_active"));
        }
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer player, EnumHand handIn) {
        ItemStack stack = player.getHeldItem(handIn);
        if (this.getDamage(stack) == 233)
            return new ActionResult<>(EnumActionResult.FAIL, stack);
        this.setDamage(stack, 233);
        return new ActionResult<>(EnumActionResult.SUCCESS, stack);
    }
}
