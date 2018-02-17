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

import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.function.BooleanSupplier;

public interface ISpringFestivalTimeProvider extends BooleanSupplier {

    boolean isDuringSpringFestival();

    @Override
    default boolean getAsBoolean() {
        return isDuringSpringFestival();
    }

    static ISpringFestivalTimeProvider fromURL(final String url, final String name) {
        return new SpringFestivalTimeProviderCached(sink -> {
            try {
                IOUtils.readLines(new URL(url).openStream(), StandardCharsets.UTF_8)
                            .stream()
                            .map(LocalDate::parse)
                            .forEach(sink::add);
            } catch (Exception e) {
                SpringFestivalConstants.logger.catching(e);
            }
        }, name);
    }

    static ISpringFestivalTimeProvider impossible() {
        return SpringFestivalTimeProviderImpossible.INSTANCE;
    }
}
