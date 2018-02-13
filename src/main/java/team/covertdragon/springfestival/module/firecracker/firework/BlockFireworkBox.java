package team.covertdragon.springfestival.module.firecracker.firework;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityFireworkRocket;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import team.covertdragon.springfestival.SpringFestivalConstants;

import java.util.Random;

public class BlockFireworkBox extends Block {
    public BlockFireworkBox() {
        super(Material.WOOD, MapColor.RED);
        setSoundType(SoundType.WOOD);
        setUnlocalizedName(SpringFestivalConstants.MOD_ID + ".firework_box");
        setRegistryName(SpringFestivalConstants.MOD_ID, "firework_box");
        setHarvestLevel("axe", 0);
        setHardness(3.0f);
    }

    @Override
    public boolean hasTileEntity(IBlockState state) {
        return true;
    }

    @Override
    public TileEntity createTileEntity(World world, IBlockState state) {
        return new TileFireworkBox();
    }

    @Override
    public void breakBlock(World world, BlockPos pos, IBlockState state) {
        TileEntity te = world.getTileEntity(pos);
        if (te instanceof TileFireworkBox) {
            ((TileFireworkBox) te).dropBlockAsItem();
        }
        super.breakBlock(world, pos, state);
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        TileEntity te = world.getTileEntity(pos);
        if (te == null || !(te instanceof TileFireworkBox)) {
            return false;
        }
        TileFireworkBox cast = (TileFireworkBox) te;
        if (!cast.isActive()) {
            cast.setActive(true);
            return true;
        }
        return false;
    }

    @Override
    public int quantityDropped(Random random) {
        return 0;
    }

    public void launchFireWork(World world, BlockPos pos, double count) {
        //Dirty implementation
        EntityFireworkRocket firework = new EntityFireworkRocket(world);
        firework.setPosition(pos.getX() + 0.111111 * (count % 8 + 1), pos.getY(), pos.getZ() + 0.111111 * (new Double(count / 8).intValue() + 1));
        world.spawnEntity(firework);
    }
}
