/*
 * Copyright (c) 2018 CovertDragon Team.
 * Copyright (c) 2018 Contributors of SpringFestival.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package team.covertdragon.springfestival.internal.time;

public final class SpringFestivalTimeProviderImpossible implements ISpringFestivalTimeProvider {

    public static final SpringFestivalTimeProviderImpossible INSTANCE = new SpringFestivalTimeProviderImpossible();

    private SpringFestivalTimeProviderImpossible() {}

    @Override
    public boolean isDuringSpringFestival() {
        return false;
    }
}
