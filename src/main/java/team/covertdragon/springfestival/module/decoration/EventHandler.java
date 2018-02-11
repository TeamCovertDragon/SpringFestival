package team.covertdragon.springfestival.module.decoration;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import team.covertdragon.springfestival.SpringFestivalConstants;
import team.covertdragon.springfestival.module.material.ModuleMaterial;

@Mod.EventBusSubscriber(modid = SpringFestivalConstants.MOD_ID)
public class EventHandler {
    @SubscribeEvent
    public static void onPlayerInteract(PlayerInteractEvent event) {
        IBlockState state = event.getWorld().getBlockState(event.getPos());
        if (!event.getWorld().isRemote && state.getBlock() instanceof BlockFuDoor && event.getEntityPlayer().isSneaking()) {
            World world = event.getWorld();
            TileFuDoor te = (TileFuDoor) world.getTileEntity(event.getPos());
            BlockPos position = event.getPos();
            //TileEntity will be null if the block is the lower part of the door
            if (te == null) {
                position.add(0, 1, 0);
                te = (TileFuDoor) world.getTileEntity(position);
            }

            if (te != null && te.getOriginalDoor() != null) {
                //Set door
                world.setBlockState(position, te.getOriginalBlockStateUpper());
                position.add(0, -1, 0);
                world.setBlockState(position, te.getOriginalBlockStateLower());

                //Drop Fu
                EntityItem entityItem = new EntityItem(world, event.getPos().getX(), event.getPos().getY(), event.getPos().getZ(), new ItemStack(ModuleMaterial.redPaper, 1));
                entityItem.setDefaultPickupDelay();
                world.spawnEntity(entityItem);
            }
        }
    }

    @SubscribeEvent
    public static void onBlockHarvest(BlockEvent.HarvestDropsEvent event) {
        if (!event.getWorld().isRemote && event.getState().getBlock() instanceof BlockFuDoor) {
            //TODO add drops
        }
    }
}
