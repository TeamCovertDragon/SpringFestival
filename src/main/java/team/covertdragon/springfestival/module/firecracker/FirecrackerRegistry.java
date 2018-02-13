package team.covertdragon.springfestival.module.firecracker;

import net.minecraft.item.ItemBlock;
import net.minecraftforge.fml.common.registry.GameRegistry;
import team.covertdragon.springfestival.SpringFestivalConstants;
import team.covertdragon.springfestival.module.firecracker.firework.BlockFireworkBox;
import team.covertdragon.springfestival.module.firecracker.firework.ItemFireworkBox;

@GameRegistry.ObjectHolder(SpringFestivalConstants.MOD_ID)
public class FirecrackerRegistry {

    static {
        blockFirework = null;
        blockHangingFireCracker = null;
        itemFireWorkBox = null;
        itemHangingFirecracker = null;
    }

    @GameRegistry.ObjectHolder("firework_box")
    public static final BlockFireworkBox blockFirework;

    @GameRegistry.ObjectHolder("hanging_firecracker")
    public static final BlockHangingFirecracker blockHangingFireCracker;

    @GameRegistry.ObjectHolder("firework_box")
    public static final ItemFireworkBox itemFireWorkBox;

    @GameRegistry.ObjectHolder("hanging_firecracker")
    public static final ItemBlock itemHangingFirecracker;
}
