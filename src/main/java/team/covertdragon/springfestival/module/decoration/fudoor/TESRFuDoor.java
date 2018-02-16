/*
 * Copyright (c) 2018 CovertDragon Team.
 * Copyright (c) 2018 Contributors of SpringFestival.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package team.covertdragon.springfestival.module.decoration.fudoor;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.client.model.animation.FastTESR;

import javax.annotation.Nullable;

public class TESRFuDoor extends FastTESR<TileFuDoor> {

    public static final String FU_TEXTURE_LOCATION = "springfestival:blocks/overlay/fu";

    @Override
    public void renderTileEntityFast(@Nullable TileFuDoor te, double x, double y, double z, float partialTicks, int destroyStage, float partial, BufferBuilder buffer) {
        if (te == null) {
            return;
        }
        EnumFacing doorFacing = te.getWorld().getBlockState(te.getPos()).getValue(BlockFuDoor.FACING);
        TextureAtlasSprite sprite = Minecraft.getMinecraft().getTextureMapBlocks().getAtlasSprite(FU_TEXTURE_LOCATION);
        int light = te.getWorld().getCombinedLight(te.getPos(), 0);
        int l1 = light >> 16 & 65535, l2 = light & 65535;
        // TODO Must be in pos -> color -> tex -> light -> end order, Must not omit, reason awaiting research
        switch (doorFacing) {
            case EAST: {
                buffer.pos(x + 0.0, y + 0.0, z + 0.0).color(255, 255, 255, 255).tex(sprite.getInterpolatedU( 0), sprite.getInterpolatedV( 0)).lightmap(l1, l2).endVertex();
                buffer.pos(x + 0.0, y + 0.0, z + 1.0).color(255, 255, 255, 255).tex(sprite.getInterpolatedU(16), sprite.getInterpolatedV( 0)).lightmap(l1, l2).endVertex();
                buffer.pos(x + 0.0, y + 1.0, z + 1.0).color(255, 255, 255, 255).tex(sprite.getInterpolatedU(16), sprite.getInterpolatedV(16)).lightmap(l1, l2).endVertex();
                buffer.pos(x + 0.0, y + 1.0, z + 0.0).color(255, 255, 255, 255).tex(sprite.getInterpolatedU( 0), sprite.getInterpolatedV(16)).lightmap(l1, l2).endVertex();
                break;
            }
            case WEST: {
                buffer.pos(x + 1.0, y + 0.0, z + 0.0).color(255, 255, 255, 255).tex(sprite.getInterpolatedU( 0), sprite.getInterpolatedV( 0)).lightmap(l1, l2).endVertex();
                buffer.pos(x + 1.0, y + 0.0, z + 1.0).color(255, 255, 255, 255).tex(sprite.getInterpolatedU(16), sprite.getInterpolatedV( 0)).lightmap(l1, l2).endVertex();
                buffer.pos(x + 1.0, y + 1.0, z + 1.0).color(255, 255, 255, 255).tex(sprite.getInterpolatedU(16), sprite.getInterpolatedV(16)).lightmap(l1, l2).endVertex();
                buffer.pos(x + 1.0, y + 1.0, z + 0.0).color(255, 255, 255, 255).tex(sprite.getInterpolatedU( 0), sprite.getInterpolatedV(16)).lightmap(l1, l2).endVertex();
                break;
            }
            case SOUTH: {
                buffer.pos(x + 0.0, y + 0.0, z + 0.0).color(255, 255, 255, 255).tex(sprite.getInterpolatedU( 0), sprite.getInterpolatedV( 0)).lightmap(l1, l2).endVertex();
                buffer.pos(x + 1.0, y + 0.0, z + 0.0).color(255, 255, 255, 255).tex(sprite.getInterpolatedU(16), sprite.getInterpolatedV( 0)).lightmap(l1, l2).endVertex();
                buffer.pos(x + 1.0, y + 1.0, z + 0.0).color(255, 255, 255, 255).tex(sprite.getInterpolatedU(16), sprite.getInterpolatedV(16)).lightmap(l1, l2).endVertex();
                buffer.pos(x + 0.0, y + 1.0, z + 0.0).color(255, 255, 255, 255).tex(sprite.getInterpolatedU( 0), sprite.getInterpolatedV(16)).lightmap(l1, l2).endVertex();
                break;
            }
            case NORTH: {
                buffer.pos(x + 0.0, y + 0.0, z + 1.0).color(255, 255, 255, 255).tex(sprite.getInterpolatedU( 0), sprite.getInterpolatedV( 0)).lightmap(l1, l2).endVertex();
                buffer.pos(x + 1.0, y + 0.0, z + 1.0).color(255, 255, 255, 255).tex(sprite.getInterpolatedU(16), sprite.getInterpolatedV( 0)).lightmap(l1, l2).endVertex();
                buffer.pos(x + 1.0, y + 1.0, z + 1.0).color(255, 255, 255, 255).tex(sprite.getInterpolatedU(16), sprite.getInterpolatedV(16)).lightmap(l1, l2).endVertex();
                buffer.pos(x + 0.0, y + 1.0, z + 1.0).color(255, 255, 255, 255).tex(sprite.getInterpolatedU( 0), sprite.getInterpolatedV(16)).lightmap(l1, l2).endVertex();
                break;
            }
        }
    }
}
