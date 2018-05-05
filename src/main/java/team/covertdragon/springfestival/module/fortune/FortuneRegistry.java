package team.covertdragon.springfestival.module.fortune;

import net.minecraftforge.fml.common.registry.GameRegistry;
import team.covertdragon.springfestival.SpringFestivalConstants;
import team.covertdragon.springfestival.module.fortune.machines.ItemBlockFVMachine;
import team.covertdragon.springfestival.module.fortune.machines.collector.BasicFVCollector;
import team.covertdragon.springfestival.module.fortune.tools.FortuneStone;

@GameRegistry.ObjectHolder(SpringFestivalConstants.MOD_ID)
public class FortuneRegistry {
    static {
        fortuneStone = null;
        basicFVCollector = null;
        itemBasicFVCollector = null;
    }

    @GameRegistry.ObjectHolder("fortune_stone")
    public static final FortuneStone fortuneStone;

    @GameRegistry.ObjectHolder("basic_fv_collector")
    public static final BasicFVCollector basicFVCollector;

    @GameRegistry.ObjectHolder("basic_fv_collector")
    public static final ItemBlockFVMachine itemBasicFVCollector;
}
