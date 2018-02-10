package team.covertdragon.springfestival.module.decoration;

import jline.internal.Nullable;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class TileFuDoor extends TileEntity {
    private ItemStack originalDoor;
    private IBlockState originalBlockState;

    public TileFuDoor(World world, ItemStack originalDoor, IBlockState state) {
        this.world = world;
        this.originalDoor = originalDoor;
        this.originalBlockState = state;
    }

    public ItemStack getOriginalDoor() {
        return originalDoor.copy();
    }

    @Nullable
    public IBlockState getOriginalBlockState() {
        return originalBlockState;
    }

    public void setOriginalDoor(ItemStack door) {
        originalDoor = door;
    }

    public void setOriginalBlockState(IBlockState state) {
        originalBlockState = state;
    }
}
