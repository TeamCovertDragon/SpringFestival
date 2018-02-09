/*
 * Copyright (c) 2018 CovertDragon Team.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package team.covertdragon.springfestival.module.calligraphy;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

import javax.annotation.Nonnull;
import java.util.Locale;

/**
 * The block that represents "Chunlian" (a.k.a. "spring couplet")
 */
@SuppressWarnings("deprecation")
public class BlockSpringCouplet extends Block {

    public enum CoupletPart implements IStringSerializable {
        UPPER, MIDDLE, LOWER;

        @Override
        public String getName() {
            return this.name().toLowerCase(Locale.ENGLISH);
        }
    }

    public static final PropertyEnum<CoupletPart> PART = PropertyEnum.create("part", CoupletPart.class);

    public BlockSpringCouplet() {
        super(Material.CARPET);
    }

    // TODO Drop "broken red paper" when broken. No exception. No silk touch. Why the hell you can silk-touch such a piece of thin paper?

    @Override
    public int damageDropped(IBlockState state) {
        return 0;
    }

    @Override
    public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face) {
        return BlockFaceShape.UNDEFINED;
    }

    @Override
    public boolean isNormalCube(IBlockState state, IBlockAccess blockAccess, BlockPos pos) {
        return false;
    }

    @Nonnull
    @Override
    public BlockStateContainer getBlockState() {
        return new BlockStateContainer(this, PART);
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        return this.blockState.getBaseState().withProperty(PART, CoupletPart.values()[meta]); // OH I HATE THIS
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(PART).ordinal();
    }
}