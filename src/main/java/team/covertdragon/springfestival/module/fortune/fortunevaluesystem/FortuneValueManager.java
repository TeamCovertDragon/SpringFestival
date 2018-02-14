package team.covertdragon.springfestival.module.fortune.fortunevaluesystem;

import net.minecraft.entity.player.EntityPlayer;
import team.covertdragon.springfestival.module.fortune.fortunevaluesystem.capability.CapabilityLoader;

public class FortuneValueManager implements Runnable{

    @Override
    public void run() {

    }

    private void updatePlayerFortuneValue(EntityPlayer player) {
        if (player.hasCapability(CapabilityLoader.fortuneValue,null)) {

        } else {
            //TODO Throw what?
        }
    }
}
