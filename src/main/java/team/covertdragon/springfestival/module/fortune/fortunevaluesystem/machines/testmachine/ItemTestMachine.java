package team.covertdragon.springfestival.module.fortune.fortunevaluesystem.machines.testmachine;

import team.covertdragon.springfestival.module.fortune.FortuneRegistry;
import team.covertdragon.springfestival.module.fortune.fortunevaluesystem.machines.AbstractItemBlockFVMachine;

public class ItemTestMachine extends AbstractItemBlockFVMachine{

    public ItemTestMachine() {
        super(FortuneRegistry.blockTestMachine);
    }
}
