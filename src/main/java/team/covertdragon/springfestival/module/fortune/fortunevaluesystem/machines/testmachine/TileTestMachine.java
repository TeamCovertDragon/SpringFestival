package team.covertdragon.springfestival.module.fortune.fortunevaluesystem.machines.testmachine;

import team.covertdragon.springfestival.module.fortune.fortunevaluesystem.machines.AbstractTileFVMachine;

public class TileTestMachine extends AbstractTileFVMachine{

    @Override
    public int getRequiredFV() {
        System.out.println("required 1 FV!");
        return 1;
    }

    @Override
    public void onFVProvided() {
        System.out.println("FV Provided!");
    }
}
