package team.covertdragon.springfestival.module.decoration;

import net.minecraft.block.BlockDoor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import team.covertdragon.springfestival.SpringFestivalConstants;
import team.covertdragon.springfestival.module.material.ModuleMaterial;

import javax.annotation.Nonnull;
import java.util.Random;

public class BlockFuDoor extends BlockDoor {

    // TODO: Maybe we should make it accessible by using NBT?

    public BlockFuDoor() {
        super(Material.WOOD);
        setHarvestLevel("axe", 0);
        setHardness(1.5F);
        setUnlocalizedName(SpringFestivalConstants.MOD_ID + "_block_fu_door");
    }

    @Override
    @Nonnull
    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        return state.getValue(HALF) == BlockDoor.EnumDoorHalf.UPPER ? ModuleMaterial.redPaper : Items.AIR;
    }

    @Override
    public boolean canSilkHarvest(World world, BlockPos pos, IBlockState state, EntityPlayer player) {
        return true;
    }

    @Override
    public boolean hasTileEntity(IBlockState state) {
        return state.getValue(HALF) == EnumDoorHalf.UPPER;
    }

    @Override
    public TileEntity createTileEntity(World world, IBlockState state) {
        return state.getValue(HALF) == EnumDoorHalf.UPPER ? new TileFuDoor(world, null, null) : null;
    }
}
