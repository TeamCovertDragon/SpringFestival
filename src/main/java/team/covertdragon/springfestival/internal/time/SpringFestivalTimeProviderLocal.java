/*
 * Copyright (c) 2018 CovertDragon Team.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package team.covertdragon.springfestival.internal.time;

import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.TimeZone;

public final class SpringFestivalTimeProviderLocal implements ISpringFestivalTimeProvider {

    public static final SpringFestivalTimeProviderLocal INSTANCE = new SpringFestivalTimeProviderLocal();

    private final Set<Date> validDates = new HashSet<>();

    private SpringFestivalTimeProviderLocal() {}

    @Override
    public boolean isDuringSpringFestival() {
        final Date currentDate = Calendar.getInstance(TimeZone.getTimeZone(ZoneId.of("UTC+08:00"))).getTime();
        return validDates.parallelStream().anyMatch(currentDate::after);
    }
}
