package team.covertdragon.springfestival.module.decoration;

import net.minecraft.block.BlockDoor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemDoor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockFuDoor extends BlockDoor{

    // TODO: Maybe we should make it accessible by using NBT?

    private ItemDoor originalDoor;

    public BlockFuDoor(){
        super(Material.WOOD);
        this.setHarvestLevel("axe", 0);
        this.setHardness(1.5F);
    }

    public BlockFuDoor(ItemDoor original)
    {
        this();
        this.originalDoor = original;
    }

    public ItemDoor getOriginalDoor()
    {
        return originalDoor;
    }

    @Override
    public void getDrops(NonNullList<ItemStack> drops, IBlockAccess world, BlockPos pos, IBlockState state, int fortune)
    {
        // TODO: Add Fu here.
        drops.add(new ItemStack(originalDoor));
    }

    @Override
    public boolean canSilkHarvest(World world, BlockPos pos, IBlockState state, EntityPlayer player)
    {
        return true;
    }

    @Override
    protected ItemStack getSilkTouchDrop(IBlockState state)
    {
        return new ItemStack(this);
    }
}
