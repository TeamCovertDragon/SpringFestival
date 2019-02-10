package team.covertdragon.springfestival;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLConstructionEvent;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.event.FMLServerStoppingEvent;
import team.covertdragon.springfestival.internal.time.SpringFestivalTimeChecker;
import team.covertdragon.springfestival.module.ISpringFestivalModule;
import team.covertdragon.springfestival.module.ModuleLoader;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum SpringFestivalModuleController {

    INSTANCE;

    // TODO (3TUSK): LinkedHashMap? So that we can have deterministic iteration order and we unify two thing together
    private final Map<String, ISpringFestivalModule> modules = new HashMap<>();
    private List<ISpringFestivalModule> moduleList;

    public static boolean isModuleLoaded(String moduleId) {
        return INSTANCE.modules.containsKey(moduleId);
    }

    public void onConstruct(FMLConstructionEvent event) {
        this.moduleList = ModuleLoader.readASMDataTable(event.getASMHarvestedData());
        this.moduleList.forEach(mod -> this.modules.put(ModuleLoader.getNameByInstance(mod), mod));
        this.moduleList.forEach(MinecraftForge.EVENT_BUS::register);
        SpringFestivalTimeChecker.INSTANCE.reset();
    }

    public void onPreInit(FMLPreInitializationEvent event) {
        this.moduleList.forEach(ISpringFestivalModule::onPreInit);
    }

    public void onInit(FMLInitializationEvent event) {
        this.moduleList.forEach(ISpringFestivalModule::onInit);
    }

    public void onServerStarting(FMLServerStartingEvent event) {
        this.moduleList.forEach(ISpringFestivalModule::onServerStarting);
    }

    public void onServerStopping(FMLServerStoppingEvent event) {
        this.moduleList.forEach(ISpringFestivalModule::onServerStopping);
    }
}
