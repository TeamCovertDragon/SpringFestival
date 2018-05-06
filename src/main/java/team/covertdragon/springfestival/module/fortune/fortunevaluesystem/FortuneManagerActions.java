/*
 * Copyright (c) 2018 CovertDragon Team.
 * Copyright (c) 2018 Contributors of SpringFestival.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package team.covertdragon.springfestival.module.fortune.fortunevaluesystem;

import team.covertdragon.springfestival.module.fortune.fortunevaluesystem.capability.IFortuneValueSystem;
import team.covertdragon.springfestival.module.fortune.machines.AbstractTileFVMachine;

public class FortuneManagerActions {
    public static class ActionRegisterMachine implements Runnable {
        AbstractTileFVMachine machine;
        IFortuneValueSystem system;

        public ActionRegisterMachine(AbstractTileFVMachine machine, IFortuneValueSystem system) {
            this.machine = machine;
            this.system = system;
        }

        @Override
        public void run() {
            system.registerFVMachine(machine);
        }
    }
}
