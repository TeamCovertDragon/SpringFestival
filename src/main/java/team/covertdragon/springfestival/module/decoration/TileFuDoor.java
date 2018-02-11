package team.covertdragon.springfestival.module.decoration;

import jline.internal.Nullable;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;

import java.util.Random;

public class TileFuDoor extends TileEntity {
    private IBlockState originalBlockStateUpper;
    private IBlockState originalBlockStateLower;

    public ItemStack getOriginalDoor() {
        return new ItemStack(originalBlockStateLower.getBlock().getItemDropped(originalBlockStateUpper, new Random(), 0));
    }

    @Nullable
    public IBlockState getOriginalBlockStateUpper() {
        return originalBlockStateUpper;
    }

    @Nullable
    public IBlockState getOriginalBlockStateLower() {
        return originalBlockStateLower;
    }

    public void setOriginalBlockStateUpper(IBlockState state) {
        this.originalBlockStateUpper = state;
    }

    public void setOriginalBlockStateLower(IBlockState state) {
        this.originalBlockStateLower = state;
    }
}
