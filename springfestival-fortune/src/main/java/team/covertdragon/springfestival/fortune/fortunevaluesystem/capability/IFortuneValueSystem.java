/*
 * Copyright (c) 2018 CovertDragon Team.
 * Copyright (c) 2018 Contributors of SpringFestival.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package team.covertdragon.springfestival.fortune.fortunevaluesystem.capability;

import team.covertdragon.springfestival.fortune.machines.AbstractTileFVMachine;
import team.covertdragon.springfestival.fortune.utils.TEDefinition;

import java.util.List;

public interface IFortuneValueSystem {
    int getFortuneValue();

    void setFortuneValue(int quality);

    void addFortune(int quality);

    boolean shrinkFortune(int quality);

    int getIncreasingPoint();

    void setBufPoint(int quality);

    void addBufPoint(int quality);

    boolean shrinkBuffPoint(int quality);

    List<TEDefinition> getFVMachines();

    void setDefinitions(List<TEDefinition> definitions);

    void registerFVMachine(AbstractTileFVMachine machine);

    int getCurrentlyNextMachineId();

    void setNextMachineId(int id);

    void deleteMachine(int id);
}
