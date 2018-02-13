/*
 * Copyright (c) 2018 CovertDragon Team.
 * Copyright (c) 2018 Contributors of SpringFestival.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package team.covertdragon.springfestival.internal.time;

import org.apache.commons.io.IOUtils;
import team.covertdragon.springfestival.SpringFestivalConstants;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.Collection;
import java.util.LinkedList;

public final class SpringFestivalTimeProviderQuerying implements ISpringFestivalTimeProvider {

    public static final SpringFestivalTimeProviderQuerying INSTANCE = new SpringFestivalTimeProviderQuerying();

    private final Collection<LocalDate> validDates = new LinkedList<>();

    private SpringFestivalTimeProviderQuerying() {
        Thread t = new Thread(() -> {
            try {
                synchronized (validDates) {
                    IOUtils.readLines(new URL("").openStream(), StandardCharsets.UTF_8)
                            .stream()
                            .map(LocalDate::parse)
                            .forEach(validDates::add);
                }
            } catch (IOException e) {
                SpringFestivalConstants.logger.catching(e);
            }
        }, "SpringFestival-DateQuerying");
        t.setDaemon(true);
        t.start();
    }

    @Override
    public boolean isDuringSpringFestival() {
        final LocalDate today = LocalDate.now(); // TODO Config option to enforce UTC+8, a.k.a. China Standard Time
        return validDates.contains(today);
    }
}
