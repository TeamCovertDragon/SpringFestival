package team.covertdragon.springfestival.module.fortune.fortunevaluesystem.capability;

import team.covertdragon.springfestival.module.fortune.fortunevaluesystem.machines.IFVMachine;

import java.util.List;

public interface IFortuneValueSystem {
    public int getFortuneValue();

    public void setFortuneValue(int quality);

    public void addFortune(int quality);

    public boolean shrinkFortune(int quality);

    public int getIncreasingPoint();

    public void setIncreasingPoint(int quality);

    public void addIncreasingPoint(int quality);

    public boolean shrinkIncreasingPoint(int quality);

    public List<IFVMachine> getFVMachines();

    public void setMachines(List<IFVMachine> machines);

    public void registerFVMachine(IFVMachine machine);

    public int getCurrentlyNextMachineId();

    public void setNextMachineId(int id);
}
