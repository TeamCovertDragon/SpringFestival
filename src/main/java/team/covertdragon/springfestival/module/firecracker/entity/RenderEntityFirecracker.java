/*
 * Copyright (c) 2018 CovertDragon Team.
 * Copyright (c) 2018 Contributors of SpringFestival.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package team.covertdragon.springfestival.module.firecracker.entity;

import javax.annotation.Nonnull;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderSnowball;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import team.covertdragon.springfestival.module.firecracker.FirecrackerRegistry;

public class RenderEntityFirecracker extends RenderSnowball<EntityFirecracker> {
    
    public static final IRenderFactory<EntityFirecracker> FACTORY = new Factory();

    public RenderEntityFirecracker(RenderManager renderManagerIn, Item p_i46137_2_, RenderItem p_i46137_3_) {
      super(renderManagerIn, p_i46137_2_, p_i46137_3_);
    }

    @Nonnull
    @Override
    public ItemStack getStackToRender(EntityFirecracker entityIn) {
      return new ItemStack(FirecrackerRegistry.FIRECRACKER_EGG);
    }

    private static class Factory implements IRenderFactory<EntityFirecracker> {

      @Override
      public Render<? super EntityFirecracker> createRenderFor(RenderManager manager) {
        return new RenderEntityFirecracker(manager, FirecrackerRegistry.FIRECRACKER_EGG, Minecraft.getMinecraft().getRenderItem());
      }
    }
}
