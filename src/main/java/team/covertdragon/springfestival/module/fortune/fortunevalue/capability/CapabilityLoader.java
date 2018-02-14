package team.covertdragon.springfestival.module.fortune.fortunevalue.capability;

import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;

public class CapabilityLoader {
    @CapabilityInject(IFortuneValue.class)
    public static Capability<IFortuneValue> fortuneValue;

    public static void initCapabilities() {
        CapabilityManager.INSTANCE.register(IFortuneValue.class, new CapabilityFortuneValue.Storage(), CapabilityFortuneValue.Implementation.class);
    }
}
