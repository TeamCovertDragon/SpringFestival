/*
 * Copyright (c) 2018 CovertDragon Team.
 * Copyright (c) 2018 Contributors of SpringFestival.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package team.covertdragon.springfestival.module.material;

import net.minecraft.block.BlockCrops;
import net.minecraft.item.Item;

import javax.annotation.Nonnull;

/**
 * The block that represents Nuomi (Oryza sativa var. glutinosa),
 * often used in cuisine or as glue after harvested.
 */
public class BlockGlutinousRice extends BlockCrops {

    BlockGlutinousRice(Properties builder) {
        super(builder);
    }

    @Nonnull
    protected Item getSeed() {
        return MaterialRegistry.GLUTINOUS_RICE_SEED;
    }

    @Nonnull
    protected Item getCrop() {
        return MaterialRegistry.GLUTINOUS_RICE;
    }
}
