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

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.stream.Collectors;

public enum SpringFestivalModuleController {

    INSTANCE;

    private Map<String, ISpringFestivalModule> modules = Collections.emptyMap();

    public static boolean isModuleLoaded(String moduleId) {
        return INSTANCE.modules.containsKey(moduleId);
    }

    private static <T> BinaryOperator<T> failMerger() {
        return (a, b) -> { throw new UnsupportedOperationException(); };
    }

    public void onConstruct(FMLConstructionEvent event) {
        this.modules = ModuleLoader.readASMDataTable(event.getASMHarvestedData())
                .stream()
                .peek(MinecraftForge.EVENT_BUS::register)
                .collect(Collectors.toMap(ModuleLoader::getModuleName, Function.identity(), failMerger(), LinkedHashMap::new));
        SpringFestivalTimeChecker.INSTANCE.reset();
    }

    public void onPreInit(FMLPreInitializationEvent event) {
        this.modules.values().forEach(ISpringFestivalModule::onPreInit);
    }

    public void onInit(FMLInitializationEvent event) {
        this.modules.values().forEach(ISpringFestivalModule::onInit);
    }

    public void onServerStarting(FMLServerStartingEvent event) {
        this.modules.values().forEach(ISpringFestivalModule::onServerStarting);
    }

    public void onServerStopping(FMLServerStoppingEvent event) {
        this.modules.values().forEach(ISpringFestivalModule::onServerStopping);
    }
}
