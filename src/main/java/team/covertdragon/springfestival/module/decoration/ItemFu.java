package team.covertdragon.springfestival.module.decoration;

import net.minecraft.block.BlockDoor;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import team.covertdragon.springfestival.SpringFestivalConstants;

public class ItemFu extends Item {
    public ItemFu() {
        setMaxStackSize(16);
        setCreativeTab(DecorationConstants.tabSFDecoration);
        setUnlocalizedName(SpringFestivalConstants.MOD_ID + "_item_fu");
    }

    //TODO change fu door
    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (!world.isRemote && world.getBlockState(pos).getBlock() instanceof BlockDoor) {
            IBlockState state = world.getBlockState(pos);

        }
        return EnumActionResult.PASS;
    }
}
