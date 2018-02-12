/*
 * Copyright (c) 2018 CovertDragon Team.
 * Copyright (c) 2018 Contributors of SpringFestival.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package team.covertdragon.springfestival.internal.model;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import team.covertdragon.springfestival.SpringFestivalConstants;

import javax.annotation.Nonnull;

@SideOnly(Side.CLIENT)
public final class ModelUtil {

    private ModelUtil() {}

    public static void mapItemModel(Item item) {
        ModelLoader.setCustomModelResourceLocation(item, 0,
                new ModelResourceLocation(item.getRegistryName().toString(), "inventory"));
    }

    public static void mapItemModel(Item item, @Nonnull String customPath) {
        ModelLoader.setCustomModelResourceLocation(item, 0,
                new ModelResourceLocation(SpringFestivalConstants.MOD_ID + ":" + customPath, "inventory"));
    }
}
