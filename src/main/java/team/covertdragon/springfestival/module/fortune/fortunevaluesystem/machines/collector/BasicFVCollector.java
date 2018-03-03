package team.covertdragon.springfestival.module.fortune.fortunevaluesystem.machines.collector;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import team.covertdragon.springfestival.SpringFestivalConstants;

import javax.annotation.Nullable;

public class BasicFVCollector extends Block {
    public BasicFVCollector() {
        super(Material.IRON, MapColor.RED);
        setHardness(10.0F);
        setHarvestLevel("pickaxe", 2);
        setSoundType(SoundType.METAL);
        setRegistryName(SpringFestivalConstants.MOD_ID, "basic_fv_collector");
        setUnlocalizedName(SpringFestivalConstants.MOD_ID + ".basic_fv_collector");
        setCreativeTab(SpringFestivalConstants.CREATIVE_TAB);
    }

    @Override
    public boolean hasTileEntity(IBlockState state) {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(World world, IBlockState state) {
        return new TileBasicFVCollector();
    }
}
