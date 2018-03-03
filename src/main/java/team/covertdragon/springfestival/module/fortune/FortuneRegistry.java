package team.covertdragon.springfestival.module.fortune;

import net.minecraftforge.fml.common.registry.GameRegistry;
import team.covertdragon.springfestival.SpringFestivalConstants;
import team.covertdragon.springfestival.module.fortune.fortunevaluesystem.tools.FortuneStone;

@GameRegistry.ObjectHolder(SpringFestivalConstants.MOD_ID)
public class FortuneRegistry {
    static {
        fortuneStone = null;
    }

    @GameRegistry.ObjectHolder("fortune_stone")
    public static final FortuneStone fortuneStone;
}
