package team.covertdragon.springfestival.module.decoration;

import net.minecraft.item.ItemArmor;
import net.minecraftforge.fml.common.registry.GameRegistry;
import team.covertdragon.springfestival.SpringFestivalConstants;

@GameRegistry.ObjectHolder(SpringFestivalConstants.MOD_ID)
public class DecorationAnnotation {
    @GameRegistry.ObjectHolder("red_hat")
    public static final ItemArmor red_hat = null;

    @GameRegistry.ObjectHolder("red_gown")
    public static final ItemArmor red_gown = null;

    @GameRegistry.ObjectHolder("red_trousers")
    public static final ItemArmor red_trousers = null;

    @GameRegistry.ObjectHolder("red_shoes")
    public static final ItemArmor red_shoes = null;
}
