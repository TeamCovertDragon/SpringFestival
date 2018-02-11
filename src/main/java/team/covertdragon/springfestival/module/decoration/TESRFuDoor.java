/*
 * Copyright (c) 2018 CovertDragon Team.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package team.covertdragon.springfestival.module.decoration;

import net.minecraft.block.BlockDoor;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.client.model.animation.FastTESR;

public class TESRFuDoor extends FastTESR<TileFuDoor> {

    @Override
    public void renderTileEntityFast(TileFuDoor te, double x, double y, double z, float partialTicks, int destroyStage, float partial, BufferBuilder buffer) {
        // TODO Render the "Fu" using TESR, as it is purely static, so that we won't bother IBakedModel
        IBlockState upperDoor = te.originalBlockStateUpper;
        IBlockState lowerDoor = te.originalBlockStateLower;
        EnumFacing doorFacing = upperDoor.getValue(BlockDoor.FACING);
        // TODO What's... next?
    }
}
