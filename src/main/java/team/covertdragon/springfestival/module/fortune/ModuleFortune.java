package team.covertdragon.springfestival.module.fortune;

import com.google.gson.Gson;
import team.covertdragon.springfestival.module.AbstractSpringFestivalModule;
import team.covertdragon.springfestival.module.SpringFestivalModule;
import team.covertdragon.springfestival.module.fortune.fortunevalue.capability.CapabilityLoader;

@SpringFestivalModule(name = "fortune", dependencies = {"material"})
public class ModuleFortune extends AbstractSpringFestivalModule {

    @Override
    public void onPreInit() {
        CapabilityLoader.initCapabilities();
    }
}
