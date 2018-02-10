package team.covertdragon.springfestival.module.decoration;

import net.minecraft.block.state.IBlockState;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import team.covertdragon.springfestival.SpringFestivalConstants;

@Mod.EventBusSubscriber(modid = SpringFestivalConstants.MOD_ID)
public class EventHandler {
    @SubscribeEvent
    public void onPlayerInteract(PlayerInteractEvent event) {
        IBlockState state = event.getWorld().getBlockState(event.getPos());
        if (!event.getWorld().isRemote && state.getBlock() instanceof BlockFuDoor && event.getEntityPlayer().isSneaking()) {
            World world = event.getWorld();
            TileFuDoor te = (TileFuDoor) world.getTileEntity(event.getPos());
            if (te != null && te.getOriginalDoor() != null) {
                world.setBlockState(event.getPos(), te.getOriginalBlockState());
                //TODO drop Fu here
            }
        }
    }
}
