/*
 * Copyright (c) 2018 CovertDragon Team.
 * Copyright (c) 2018 Contributors of SpringFestival.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package team.covertdragon.springfestival.module.music;

import net.minecraft.item.ItemRecord;
import net.minecraft.util.SoundEvent;

public final class ItemSpringFestivalMusicRecord extends ItemRecord {

    ItemSpringFestivalMusicRecord(int priority, SoundEvent sound, Properties properties) {
        super(priority, sound, properties);
    }

}
