/*
 * Copyright (c) 2018 CovertDragon Team.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package team.covertdragon.springfestival.internal.time;

/**
 * An implementation of ISpringFestivalTimeProvider that always returns true, useful for debugging purpose.
 */
public class SpringFestivalTimeProviderDebugging implements ISpringFestivalTimeProvider {

    public static final SpringFestivalTimeProviderDebugging INSTANCE = new SpringFestivalTimeProviderDebugging();

    private SpringFestivalTimeProviderDebugging() {}

    @Override
    public boolean isDuringSpringFestival() {
        return true;
    }
}
