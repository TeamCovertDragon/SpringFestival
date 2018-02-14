package team.covertdragon.springfestival.module.fortune;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.GameRegistry;
import team.covertdragon.springfestival.SpringFestivalConstants;

@GameRegistry.ObjectHolder(SpringFestivalConstants.MOD_ID)
public class FortuneRegistry {
    @GameRegistry.ObjectHolder("test_machine")
    public static final Block blockTestMachine = null;

    @GameRegistry.ObjectHolder("test_machine")
    public static final Item itemTestMachine = null;
}
