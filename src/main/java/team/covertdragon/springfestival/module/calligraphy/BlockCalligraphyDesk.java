/*
 * Copyright (c) 2018 CovertDragon Team.
 * Copyright (c) 2018 Contributors of SpringFestival.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package team.covertdragon.springfestival.module.calligraphy;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class BlockCalligraphyDesk extends Block {

    public BlockCalligraphyDesk(Properties properties) {
        //super(Material.WOOD, MapColor.WOOD);
        super(properties);
    }

    @Override
    public boolean hasTileEntity(IBlockState state) {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(IBlockState state, IBlockReader blockReader) {
        //return new TileEntityCalligraphyDesk();
        return null; // TODO (3TUSK): FIX ME
    }

    @Override
    public boolean onBlockActivated(IBlockState state, World worldIn, BlockPos pos, EntityPlayer player, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ) {
        /*if (!worldIn.isRemote) {
            playerIn.openGui(SpringFestival.getInstance(), SpringFestivalGuiHandler.TILE_ENTITY_GUI, worldIn, pos.getX(), pos.getY(), pos.getZ());
        }*/
        return true;
    }
}
