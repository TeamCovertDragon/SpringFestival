package team.covertdragon.springfestival.module.material;

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
import team.covertdragon.springfestival.module.decoration.BlockFuDoor;
import team.covertdragon.springfestival.module.decoration.DecorationConstants;
import team.covertdragon.springfestival.module.decoration.ItemFuDoor;
import team.covertdragon.springfestival.module.decoration.TileFuDoor;

public class ItemRedPaper extends Item {
    public ItemRedPaper() {
        setUnlocalizedName(SpringFestivalConstants.MOD_ID + ".red_paper");
        setCreativeTab(SpringFestivalConstants.CREATIVE_TAB);
    }

    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        IBlockState state = world.getBlockState(pos);
        if (!world.isRemote && state.getBlock() instanceof BlockDoor && player.isSneaking() && !(state.getBlock() instanceof BlockFuDoor)) {
            IBlockState originalUpper;
            IBlockState originalLower;
            if (state.getValue(BlockDoor.HALF) == BlockDoor.EnumDoorHalf.UPPER) {
                originalUpper = state;
                originalLower = world.getBlockState(pos.add(0, -1, 0));
            } else {
                originalLower = state;
                pos = pos.add(0, 1, 0);
                originalUpper = world.getBlockState(pos);
            }

            //Delete original door
            world.setBlockToAir(pos);
            world.setBlockToAir(pos.add(0, -1, 0));
            //Set Tile Entity
            ItemFuDoor.placeDoor(world, pos.add(0, -1, 0), state.getValue(BlockDoor.FACING), DecorationConstants.blockFuDoor, state.getValue(BlockDoor.HINGE) == BlockDoor.EnumHingePosition.RIGHT);
            TileFuDoor te = (TileFuDoor) world.getTileEntity(pos);
            if (te != null) {
                te.setOriginalBlockStateLower(originalLower);
                te.setOriginalBlockStateUpper(originalUpper);
            } else {
                throw new NullPointerException();
            }

            player.getHeldItem(hand).shrink(1);

            return EnumActionResult.SUCCESS;
        }
        return EnumActionResult.PASS;
    }
}
