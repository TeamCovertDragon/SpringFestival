/*
 * Copyright (c) 2018 CovertDragon Team.
 * Copyright (c) 2018 Contributors of SpringFestival.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package team.covertdragon.springfestival.module.firecracker.firework;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import team.covertdragon.springfestival.SpringFestivalConstants;
import team.covertdragon.springfestival.module.firecracker.FirecrackerRegistry;

import javax.annotation.Nullable;
import java.util.List;

public class ItemFireworkBox extends ItemBlock {
    public ItemFireworkBox() {
        super(FirecrackerRegistry.blockFirework);
        setCreativeTab(SpringFestivalConstants.CREATIVE_TAB);
        setTranslationKey(SpringFestivalConstants.MOD_ID + ".firework_box");
        setRegistryName(SpringFestivalConstants.MOD_ID, "firework_box");
    }

    @Override
    public boolean placeBlockAt(ItemStack stack, EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ, IBlockState newState) {
        if (super.placeBlockAt(stack, player, world, pos, side, hitX, hitY, hitZ, newState)) {
            TileEntity te = world.getTileEntity(pos);
            if (te != null && te instanceof TileFireworkBox && stack.hasTagCompound()) {
                ((TileFireworkBox) te).setCount(stack.getTagCompound().getInteger("count"));
            }
            return true;
        }
        return false;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        if (stack.hasTagCompound()) {
            tooltip.add(I18n.format("firework.tooltip.count", stack.getTagCompound().getInteger("count")));
        } else {
            tooltip.add(I18n.format("firework.tooltip.count", 64));
        }
    }
}
