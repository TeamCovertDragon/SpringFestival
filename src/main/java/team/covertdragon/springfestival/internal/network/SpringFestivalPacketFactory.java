/*
 * Copyright (c) 2018 CovertDragon Team.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package team.covertdragon.springfestival.internal.network;

import it.unimi.dsi.fastutil.ints.Int2ObjectArrayMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.objects.Object2IntArrayMap;
import it.unimi.dsi.fastutil.objects.Object2IntMap;

final class SpringFestivalPacketFactory {

    SpringFestivalPacketFactory() {}

    private final Object2IntMap<Class<? extends AbstractSpringFestivalPacket>> mapping        = new Object2IntArrayMap<>();
    private final Int2ObjectMap<Class<? extends AbstractSpringFestivalPacket>> mappingReverse = new Int2ObjectArrayMap<>();
    private int nextIndex = 0;

    void mapPacketToNextAvailableIndex(Class<? extends AbstractSpringFestivalPacket> klass) {
        mapping.put(klass, nextIndex);
        mappingReverse.put(nextIndex, klass);
        nextIndex++;
    }

    int getPacketIndex(Class<? extends AbstractSpringFestivalPacket> klass) {
        return mapping.getInt(klass);
    }

    AbstractSpringFestivalPacket getByIndex(int index) {
        try {
            return mappingReverse.get(index).newInstance();
        } catch (Exception e) {
            return SpringFestivalPacketVoiding.INSTANCE;
        }
    }
}
