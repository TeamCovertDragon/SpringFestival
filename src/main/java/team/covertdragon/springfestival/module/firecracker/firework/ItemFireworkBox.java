/*
 * Copyright (c) 2018 CovertDragon Team.
 * Copyright (c) 2018 Contributors of SpringFestival.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package team.covertdragon.springfestival.module.firecracker.firework;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.List;

public class ItemFireworkBox extends ItemBlock {
    public ItemFireworkBox(Block block, Properties properties) {
        super(block, properties);
    }

    @Override
    protected boolean placeBlock(BlockItemUseContext context, IBlockState state) {
        if (super.placeBlock(context, state)) {
            TileEntity te = context.getWorld().getTileEntity(context.getPos());
            if (te instanceof TileFireworkBox && context.getItem().hasTag()) {
                ((TileFireworkBox) te).setCount(context.getItem().getTag().getInt("count"));
            }
            return true;
        }
        return false;
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flag) {
        if (stack.hasTag()) {
            tooltip.add(new TextComponentTranslation("firework.tooltip.count", stack.getTag().getInt("count")));
        } else {
            tooltip.add(new TextComponentTranslation("firework.tooltip.count", 64));
        }
    }
}
