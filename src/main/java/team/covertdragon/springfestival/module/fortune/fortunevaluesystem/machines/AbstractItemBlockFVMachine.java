package team.covertdragon.springfestival.module.fortune.fortunevaluesystem.machines;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import team.covertdragon.springfestival.module.fortune.fortunevaluesystem.capability.CapabilityLoader;

import javax.annotation.OverridingMethodsMustInvokeSuper;

public class AbstractItemBlockFVMachine extends ItemBlock {
    public AbstractItemBlockFVMachine(Block block) {
        super(block);
        setRegistryName(block.getRegistryName());
        setUnlocalizedName(block.getUnlocalizedName().replaceAll("tile.", ""));
    }

    @Override
    @OverridingMethodsMustInvokeSuper
    public boolean placeBlockAt(ItemStack stack, EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ, IBlockState newState) {
        if (super.placeBlockAt(stack, player, world, pos, side, hitX, hitY, hitZ, newState)) {
            TileEntity te = world.getTileEntity(pos);
            if (te != null && te instanceof AbstractTileFVMachine) {
                try {                                                          //Excuse me???↓
                    player.getCapability(CapabilityLoader.fortuneValue, null).registerFVMachine((AbstractTileFVMachine) te);
                } catch (NullPointerException e) {
                    throw new RuntimeException("Unable to read fv info for player " + player.getGameProfile().getName());
                }
            } else {
                throw new RuntimeException(String.format("Unable to read info for machine at %d, %d, %d", pos.getX(), pos.getY(), pos.getZ()));
            }
        }
        return false;
    }
}
