package team.covertdragon.springfestival.module.fortune.fortunevaluesystem.capability;

import team.covertdragon.springfestival.module.fortune.machines.AbstractTileFVMachine;

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

    List<AbstractTileFVMachine> getFVMachines();

    void setMachines(List<AbstractTileFVMachine> machines);

    void registerFVMachine(AbstractTileFVMachine machine);

    int getCurrentlyNextMachineId();

    void setNextMachineId(int id);

    void deleteMachine(int id);
}
