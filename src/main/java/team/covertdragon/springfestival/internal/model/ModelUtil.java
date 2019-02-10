/*
 * Copyright (c) 2018 CovertDragon Team.
 * Copyright (c) 2018 Contributors of SpringFestival.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package team.covertdragon.springfestival.internal.model;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import team.covertdragon.springfestival.SpringFestivalConstants;

import javax.annotation.Nonnull;
import java.util.Objects;

public final class ModelUtil {

    private ModelUtil() {
        throw new UnsupportedOperationException("You just don't have instance of ModelUtil.");
    }

    public static void mapItemModel(Block block) {
        mapItemModel(Item.getItemFromBlock(block));
    }

    public static void mapItemModel(Item item) {
        mapItemModel(item, 0);
    }

    public static void mapItemModel(Item item, int meta) {
        if (item == null) {
            SpringFestivalConstants.logger.debug("Someone was trying to map model location for null item. Context: {}", new RuntimeException());
            return;
        }
        ModelLoader.setCustomModelResourceLocation(item, meta, new ModelResourceLocation(
                Objects.requireNonNull(item.getRegistryName(), "Unregistered item or invalid registry name"),
                "inventory"));
    }

    public static void mapItemModel(Item item, @Nonnull String customPath) {
        if (item == null) {
            SpringFestivalConstants.logger.debug("Someone was trying to map model location '{}' for null item. Context: {}", customPath, new RuntimeException());
            return;
        }
        ModelLoader.setCustomModelResourceLocation(item, 0,
                new ModelResourceLocation(new ResourceLocation(SpringFestivalConstants.MOD_ID, customPath), "inventory"));
    }
}
