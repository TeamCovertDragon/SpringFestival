/*
 * Copyright (c) 2018 CovertDragon Team.
 * Copyright (c) 2018 Contributors of SpringFestival.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package team.covertdragon.springfestival.internal.time;

import team.covertdragon.springfestival.SpringFestivalConfig;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.TimeZone;

public final class SpringFestivalTimeProviderLocal implements ISpringFestivalTimeProvider {

    public static final SpringFestivalTimeProviderLocal INSTANCE = new SpringFestivalTimeProviderLocal();

    private final Set<LocalDate> validDates = new HashSet<>();

    private SpringFestivalTimeProviderLocal() {
        /*
         * TODO: Hardcode here. I think it's also a gentle way. <- Wait, this is local time provider - means that we need to bundle a file into the source!
         * Information can be get here:
         * https://www.timeanddate.com/holidays/china/spring-festival-golden-week
         */
    }

    @Override
    public boolean isDuringSpringFestival() {
        if (SpringFestivalConfig.enforceChinaStandardTime) {
            return validDates.contains(LocalDate.now(ZoneId.of("Asia/Shanghai")));
        } else {
            return validDates.contains(LocalDate.now());
        }
    }
}
