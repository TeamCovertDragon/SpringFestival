/*
 * Copyright (c) 2018 CovertDragon Team.
 * Copyright (c) 2018 Contributors of SpringFestival.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package team.covertdragon.springfestival.internal.time;

final class SpringFestivalTimeProviderQuerying implements SpringFestivalTimeProvider {

    public SpringFestivalTimeProviderQuerying() {
        /*
         * TODO: Hardcode here. I think it's also a gentle way.
         * Information can be get here:
         * https://www.timeanddate.com/holidays/china/spring-festival-golden-week
         *
         * ^ That one is not free
         */
    }

    @Override
    public boolean isDuringSpringFestival() {
        return false;
    }
}
