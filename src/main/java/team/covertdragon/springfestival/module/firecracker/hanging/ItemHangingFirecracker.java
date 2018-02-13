package team.covertdragon.springfestival.module.firecracker.hanging;

import net.minecraft.item.ItemBlock;
import team.covertdragon.springfestival.SpringFestivalConstants;
import team.covertdragon.springfestival.module.firecracker.FirecrackerRegistry;

public class ItemHangingFirecracker extends ItemBlock {
    public ItemHangingFirecracker() {
        super(FirecrackerRegistry.blockHangingFireCracker);
        setRegistryName(SpringFestivalConstants.MOD_ID, "hanging_firecracker");
        setUnlocalizedName(SpringFestivalConstants.MOD_ID + ".hanging_firecracker");
    }
}
