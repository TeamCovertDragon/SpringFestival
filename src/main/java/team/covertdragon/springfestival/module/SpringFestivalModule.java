/*
 * Copyright (c) 2018 CovertDragon Team.
 * Copyright (c) 2018 Contributors of SpringFestival.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package team.covertdragon.springfestival.module;


import javax.annotation.Nonnull;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * The annotation of marking an implementation of {@link ISpringFestivalModule}
 * as a loadable module.
 *
 * Spring Festival Mod will make following assumptions for any class that is marked
 * with this annotation:
 * <ol>
 *     <li>Proposition <code>instanceof ISpringFestival</code> holds true.</li>
 *     <li>Has zero-parameter (i.e. default) constructor.</li>
 *     <li>{@link #name()} is not null nor empty.</li>
 * </ol>
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface SpringFestivalModule {

    @Nonnull String name();

    String[] dependencies() default {};
}
