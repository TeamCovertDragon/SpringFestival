package team.covertdragon.springfestival.module.decoration;

import com.sun.org.apache.xml.internal.security.utils.I18n;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import team.covertdragon.springfestival.SpringFestivalConstants;

public class DecorationConstants {
    public static final CreativeTabs tabSFDecoration = new CreativeTabs(I18n.translate("springfestival.tabs.decoration")) {
        @Override
        public ItemStack getTabIconItem() {
            return new ItemStack(itemFu);
        }
    };

    public static final BlockFuDoor blockFuDoor = (BlockFuDoor) new BlockFuDoor().setRegistryName(SpringFestivalConstants.MOD_ID, "block_fu_door");

    public static final ItemFuDoor itemFuDoor = (ItemFuDoor) new ItemFuDoor(blockFuDoor).setRegistryName(SpringFestivalConstants.MOD_ID, "item_fu_door");
    public static final ItemFu itemFu = (ItemFu) new ItemFu().setRegistryName(SpringFestivalConstants.MOD_ID, "item_fu");
}
