package team.covertdragon.springfestival.module.fortune.fortunevaluesystem;

import team.covertdragon.springfestival.module.fortune.fortunevaluesystem.capability.IFortuneValueSystem;
import team.covertdragon.springfestival.module.fortune.fortunevaluesystem.machines.AbstractTileFVMachine;

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
