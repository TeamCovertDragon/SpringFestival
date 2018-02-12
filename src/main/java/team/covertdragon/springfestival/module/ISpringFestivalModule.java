package team.covertdragon.springfestival.module;

public interface ISpringFestivalModule {

    /**
     * Called when FMLInitializationEvent is dispatched.
     */
    void onInit();

    /**
     * Called when FMLServerStartingEvent is dispatched.
     */
    default void onServerStarting() {}

    default void onServerStopping() {}

    /**
     * @deprecated Directly use ModelRegistryEvent instead, ModuleLoader will handle
     */
    @Deprecated
    default void registryModels() {}
}
