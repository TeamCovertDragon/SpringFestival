/*
 * Copyright (c) 2018 CovertDragon Team.
 * Copyright (c) 2018 Contributors of SpringFestival.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package team.covertdragon.springfestival.internal.time;

public enum QueryStatus {

    /**
     * Indicating the status of not being fully initialized.
     */
    UNINITIALIZED,

    /**
     * Indicating the status of querying operation working in progress.
     */
    PENDING,

    /**
     * Indicating the status of ready for determine the date.
     */
    AVAILABLE,

    /**
     * Indicating the status of being in error and inability to work properly.
     */
    ERROR

}
