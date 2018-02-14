package team.covertdragon.springfestival.module.fortune.fortunevaluesystem.capability;

import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;

public class CapabilityLoader {
    @CapabilityInject(IFortuneValueSystem.class)
    public static Capability<IFortuneValueSystem> fortuneValue;

    public static void initCapabilities() {
        CapabilityManager.INSTANCE.register(IFortuneValueSystem.class, new CapabilityFortuneValueSystem.Storage(), new CapabilityFortuneValueSystem.Factory());
    }
}
