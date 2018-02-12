package team.covertdragon.springfestival;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import team.covertdragon.springfestival.module.material.ModuleMaterial;

public class SpringFestivalConstants {

    public static final String MOD_ID = "springfestival";

    public static final String NAME = "SpringFestival";

    public static final CreativeTabs CREATIVE_TAB = new CreativeTabs("spring_festival") {
        @Override
        public ItemStack getTabIconItem() {
            return new ItemStack(ModuleMaterial.RED_PAPER); // Yeah... we have hard-dep on this one
        }
    };

    public static org.apache.logging.log4j.Logger logger;
}
