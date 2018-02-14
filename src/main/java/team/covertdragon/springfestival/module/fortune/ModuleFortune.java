package team.covertdragon.springfestival.module.fortune;

import team.covertdragon.springfestival.module.AbstractSpringFestivalModule;
import team.covertdragon.springfestival.module.SpringFestivalModule;
import team.covertdragon.springfestival.module.fortune.fortunevaluesystem.capability.CapabilityLoader;

@SpringFestivalModule(name = "fortune", dependencies = {"material"})
public class ModuleFortune extends AbstractSpringFestivalModule {

    @Override
    public void onPreInit() {
        CapabilityLoader.initCapabilities();
    }
}
