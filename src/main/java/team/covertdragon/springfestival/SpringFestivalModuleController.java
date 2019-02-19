package team.covertdragon.springfestival;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.event.server.FMLServerStoppingEvent;
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

    public void onConstruct(FMLCommonSetupEvent event) {
        this.modules = ModuleLoader.readASMDataTable()
                .stream()
                .peek(MinecraftForge.EVENT_BUS::register)
                .collect(Collectors.toMap(ModuleLoader::getModuleName, Function.identity(), failMerger(), LinkedHashMap::new));
        SpringFestivalTimeChecker.INSTANCE.reset();
    }

    public void onServerStarting(FMLServerStartingEvent event) {
        this.modules.values().forEach(module -> module.onServerStarting(event));
    }

    public void onServerStopping(FMLServerStoppingEvent event) {
        this.modules.values().forEach(ISpringFestivalModule::onServerStopping);
    }
}
