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
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;

public class BlockFu extends Block {

    /**
     * Used to denote that whether the "Fu" character ("福") is flipped or not.
     */
    // TODO Correctly map it to metadata, because we still can't get rid of that
    public static final PropertyBool INVERSE = PropertyBool.create("inverse");

    public BlockFu() {
        super(Material.CARPET);
    }
}
