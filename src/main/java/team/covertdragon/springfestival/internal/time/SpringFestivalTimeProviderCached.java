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
import java.util.Collection;
import java.util.ArrayList;
import java.util.function.Consumer;

final class SpringFestivalTimeProviderCached implements SpringFestivalTimeProvider {
    private final Collection<LocalDate> validDates = new ArrayList<>(8);

    private volatile QueryStatus status = QueryStatus.UNINITIALIZED;

    SpringFestivalTimeProviderCached(final Consumer<Collection<LocalDate>> validDatesSink, final String name) {
        Thread t = new Thread(() -> {
            try {
                status = QueryStatus.PENDING;
                synchronized (validDates) {
                    validDatesSink.accept(validDates);
                }
                status = QueryStatus.AVAILABLE;
            } catch (Exception e) {
                status = QueryStatus.ERROR;
            }
        }, name);
        t.setDaemon(true);
        t.start();
    }

    @Override
    public QueryStatus getStatus() {
        return status;
    }

    @Override
    public boolean isDuringSpringFestival() {
        if (status != QueryStatus.AVAILABLE) {
            return false;
        }
        if (SpringFestivalConfig.TIME_SETTING.enforceChinaStandardTime.get()) {
            return validDates.contains(LocalDate.now(ZoneId.of("Asia/Shanghai")));
        } else {
            return validDates.contains(LocalDate.now());
        }
    }
}
