/*
 * Copyright (c) 2018 CovertDragon Team.
 * Copyright (c) 2018 Contributors of SpringFestival.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package team.covertdragon.springfestival.module.fortune.machines;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import team.covertdragon.springfestival.module.fortune.ModuleFortune;
import team.covertdragon.springfestival.module.fortune.fortunevaluesystem.FortuneManagerActions;
import team.covertdragon.springfestival.module.fortune.fortunevaluesystem.capability.CapabilityLoader;

import javax.annotation.OverridingMethodsMustInvokeSuper;

public class ItemBlockFVMachine extends ItemBlock {
    public ItemBlockFVMachine(Block block, Properties properties) {
        super(block, properties);
        // setRegistryName(block.getRegistryName());
        // setTranslationKey(block.getTranslationKey().replaceAll("tile.", ""));
    }

    @Override
    protected boolean placeBlock(BlockItemUseContext context, IBlockState newState) {
        if (super.placeBlock(context, newState)) {
            TileEntity te = context.getWorld().getTileEntity(context.getPos());
            if (te instanceof AbstractTileFVMachine && (!context.getWorld().isRemote)) {
                try {
                    ModuleFortune.manager.addTask(new FortuneManagerActions.ActionRegisterMachine((AbstractTileFVMachine) te, context.getPlayer().getCapability(CapabilityLoader.fortuneValue, null).orElseThrow(NullPointerException::new)));
                    ((AbstractTileFVMachine) te).setOwner(context.getPlayer().getGameProfile().getName());
                } catch (NullPointerException e) {
                    throw new RuntimeException("Unable to read fv info for player " + context.getPlayer().getGameProfile().getName());
                }
            }
            return true;
        }
        return false;
    }

}
