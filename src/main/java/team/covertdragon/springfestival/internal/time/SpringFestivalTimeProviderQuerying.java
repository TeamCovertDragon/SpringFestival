/*
 * Copyright (c) 2018 CovertDragon Team.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package team.covertdragon.springfestival.internal.time;

public final class SpringFestivalTimeProviderQuerying implements ISpringFestivalTimeProvider {

    public static final SpringFestivalTimeProviderQuerying INSTANCE = new SpringFestivalTimeProviderQuerying();

    private SpringFestivalTimeProviderQuerying() {}

    @Override
    public boolean isDuringSpringFestival() {
        // TODO Implementing on-line querying
        return false;
    }
}
