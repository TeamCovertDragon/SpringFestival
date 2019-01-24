/*
 * Copyright (c) 2018 CovertDragon Team.
 * Copyright (c) 2018 Contributors of SpringFestival.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package team.covertdragon.springfestival.fortune.fortunevaluesystem.capability;

import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;

public class CapabilityLoader {
    @CapabilityInject(IFortuneValueSystem.class)
    public static Capability<IFortuneValueSystem> fortuneValue;

    public static void init() {
        CapabilityManager.INSTANCE.register(IFortuneValueSystem.class, new CapabilityFortuneValueSystem.Storage(), new CapabilityFortuneValueSystem.Factory());
    }
}
