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
    public ItemBlockFVMachine(Block block) {
        super(block);
        setRegistryName(block.getRegistryName());
        setUnlocalizedName(block.getUnlocalizedName().replaceAll("tile.", ""));
    }

    @Override
    @OverridingMethodsMustInvokeSuper
    public boolean placeBlockAt(ItemStack stack, EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ, IBlockState newState) {
        if (super.placeBlockAt(stack, player, world, pos, side, hitX, hitY, hitZ, newState)) {
            TileEntity te = world.getTileEntity(pos);
            if (te instanceof AbstractTileFVMachine && (!world.isRemote)) {
                try {
                    ModuleFortune.manager.addTask(new FortuneManagerActions.ActionRegisterMachine((AbstractTileFVMachine) te, player.getCapability(CapabilityLoader.fortuneValue, null)));
                    ((AbstractTileFVMachine) te).setOwner(player.getName());
                } catch (NullPointerException e) {
                    throw new RuntimeException("Unable to read fv info for player " + player.getGameProfile().getName());
                }
            }
            return true;
        }
        return false;
    }
}
