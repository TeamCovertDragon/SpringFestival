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
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.common.ToolType;

import javax.annotation.Nullable;
import java.util.Random;

public class BlockFireworkBox extends Block {
    public BlockFireworkBox(Properties properties) {
        super(properties);
    }

    @Nullable
    @Override
    public ToolType getHarvestTool(IBlockState state) {
        return ToolType.AXE;
    }

    @Override
    public int getHarvestLevel(IBlockState state) {
        return 0;
    }

    @Override
    public boolean hasTileEntity(IBlockState state) {
        return true;
    }

    @Override
    public TileEntity createTileEntity(IBlockState state, IBlockReader blockReader) {
        return new TileFireworkBox();
    }
/* // TODO (3TUSK): Find the replacement
    @Override
    public void breakBlock(World world, BlockPos pos, IBlockState state) {
        TileEntity te = world.getTileEntity(pos);
        if (te instanceof TileFireworkBox) {
            ((TileFireworkBox) te).dropBlockAsItem();
        }
        super.breakBlock(world, pos, state);
    }*/

    @Override
    public boolean onBlockActivated(IBlockState state, World world, BlockPos pos, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        TileEntity te = world.getTileEntity(pos);
        if (!(te instanceof TileFireworkBox)) {
            return false;
        }
        TileFireworkBox cast = (TileFireworkBox) te;
        if (!cast.isActive()) {
            cast.setActive(true);
            return true;
        }
        return false;
    }

    @Override
    public int quantityDropped(IBlockState state, Random random) {
        return 0;
    }

}
