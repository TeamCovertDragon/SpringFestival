package team.covertdragon.springfestival.module.fortune.fortunevaluesystem.capability;

import team.covertdragon.springfestival.module.fortune.fortunevaluesystem.machines.AbstractTileFVMachine;

import java.util.List;

public interface IFortuneValueSystem {
    public int getFortuneValue();

    public void setFortuneValue(int quality);

    public void addFortune(int quality);

    public boolean shrinkFortune(int quality);

    public int getIncreasingPoint();

    public void setBufPoint(int quality);

    public void addBufPoint(int quality);

    public boolean shrinkBuffPoint(int quality);

    public List<AbstractTileFVMachine> getFVMachines();

    public void setMachines(List<AbstractTileFVMachine> machines);

    public void registerFVMachine(AbstractTileFVMachine machine);

    public int getCurrentlyNextMachineId();

    public void setNextMachineId(int id);

    public void deleteMachine(int id);
}
