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
import java.util.LinkedList;
import java.util.function.Consumer;

final class SpringFestivalTimeProviderCached implements ISpringFestivalTimeProvider {
    private final Collection<LocalDate> validDates = new LinkedList<>();

    SpringFestivalTimeProviderCached(Consumer<Collection<LocalDate>> validDatesSink, final String name) {
        Thread t = new Thread(() -> {
            synchronized (validDates) {
                validDatesSink.accept(validDates);
            }
        }, name);
        t.setDaemon(true);
        t.start();
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
