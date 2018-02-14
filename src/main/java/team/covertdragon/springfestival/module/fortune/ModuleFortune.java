package team.covertdragon.springfestival.module.fortune;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import team.covertdragon.springfestival.SpringFestivalConstants;
import team.covertdragon.springfestival.module.AbstractSpringFestivalModule;
import team.covertdragon.springfestival.module.SpringFestivalModule;
import team.covertdragon.springfestival.module.fortune.fortunevaluesystem.FortuneValueManager;
import team.covertdragon.springfestival.module.fortune.fortunevaluesystem.capability.CapabilityLoader;

@SpringFestivalModule(name = "fortune", dependencies = {"material"})
public class ModuleFortune extends AbstractSpringFestivalModule {
    public static FortuneValueManager manager;

    @Override
    public void onPreInit() {
        CapabilityLoader.initCapabilities();
    }

    @Override
    public void onServerStarting() {
        if (manager == null)
            manager = new FortuneValueManager(SpringFestivalConstants.server);
        manager.updatePlayerList();
        manager.alive = true;
        manager.run();
    }

    @Override
    public void onServerStopping() {
        manager.alive = false;
    }

    @SubscribeEvent
    public void onAttachCapabilitiesEntity(AttachCapabilitiesEvent<EntityPlayer> event) {
        //TODO add CapabilityFortuneValueSystem for EntityPlayer
    }
}
