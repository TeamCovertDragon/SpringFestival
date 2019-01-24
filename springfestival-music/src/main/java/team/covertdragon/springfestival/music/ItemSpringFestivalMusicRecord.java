/*
 * Copyright (c) 2018 CovertDragon Team.
 * Copyright (c) 2018 Contributors of SpringFestival.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package team.covertdragon.springfestival.music;

import net.minecraft.item.ItemRecord;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.text.translation.I18n;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemSpringFestivalMusicRecord extends ItemRecord {

    private final String displayNameKey;

    public ItemSpringFestivalMusicRecord(String displayNameKey, SoundEvent soundIn) {
        super(displayNameKey, soundIn);
        this.displayNameKey = "item.springfestival.record." + displayNameKey;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public String getRecordNameLocal() {
        return I18n.translateToLocal(displayNameKey);
    }
}
