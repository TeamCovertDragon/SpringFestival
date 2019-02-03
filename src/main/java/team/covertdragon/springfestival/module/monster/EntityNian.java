/*
 * Copyright (c) 2018 CovertDragon Team.
 * Copyright (c) 2018 Contributors of SpringFestival.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package team.covertdragon.springfestival.module.monster;

import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.IMob;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import team.covertdragon.springfestival.SpringFestivalConstants;

public class EntityNian extends EntityMob implements IMob {

    private static final ResourceLocation NIAN_DROPS = new ResourceLocation(SpringFestivalConstants.MOD_ID, "entities/nian");

    public EntityNian(World worldIn) {
        super(worldIn);
    }

    // TODO Pending on detailed documentation, just leave this stub here

    @Override
    protected ResourceLocation getLootTable() {
        return NIAN_DROPS;
    }
}
