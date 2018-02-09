/*
 * Copyright (c) 2018 CovertDragon Team.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package team.covertdragon.springfestival.module.decoration;

import net.minecraft.client.renderer.tileentity.TileEntityChestRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.EnumHelper;
import team.covertdragon.springfestival.SpringFestival;
import team.covertdragon.springfestival.module.AbstractSpringFestivalModule;

import java.lang.reflect.Field;

public class ModuleDecoration extends AbstractSpringFestivalModule {

    public void onInit() {
        if (SpringFestival.proxy.isDuringSpringFestivalSeason()) {
            try {
                Field textureChestSingle = TileEntityChestRenderer.class.getDeclaredField("TEXTURE_NORMAL");
                Field textureChestDouble = TileEntityChestRenderer.class.getDeclaredField("TEXTURE_NORMAL_DOUBLE");
                EnumHelper.setFailsafeFieldValue(textureChestSingle, null, new ResourceLocation("springfestival", "texture/tile/chest_single.png"));
                EnumHelper.setFailsafeFieldValue(textureChestDouble, null, new ResourceLocation("springfestival", "texture/tile/chest_double.png"));
            } catch (Exception e) {
                // TODO Where is my logger?!
                // TODO Should we catch the exception for both fields separately?
            }
        }
    }

}