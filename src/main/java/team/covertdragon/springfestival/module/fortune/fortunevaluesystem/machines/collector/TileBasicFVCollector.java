package team.covertdragon.springfestival.module.fortune.fortunevaluesystem.machines.collector;

import team.covertdragon.springfestival.module.fortune.fortunevaluesystem.machines.AbstractTileFVMachine;

public class TileBasicFVCollector extends AbstractTileFVMachine {
    @Override
    public int getRequiredFV() {
        return -5;
    }

    @Override
    public void onFVProvided() {

    }
}
