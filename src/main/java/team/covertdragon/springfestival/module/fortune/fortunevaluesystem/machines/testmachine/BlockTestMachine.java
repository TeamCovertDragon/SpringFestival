package team.covertdragon.springfestival.module.fortune.fortunevaluesystem.machines.testmachine;

import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import team.covertdragon.springfestival.SpringFestivalConstants;

import javax.annotation.Nullable;

public class BlockTestMachine extends Block {
    public BlockTestMachine() {
        super(Material.IRON, MapColor.RED);
        setCreativeTab(SpringFestivalConstants.CREATIVE_TAB);
        setRegistryName(SpringFestivalConstants.MOD_ID, "test_machine");
    }

    @Override
    public boolean hasTileEntity(IBlockState state) {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(World world, IBlockState state) {
        return new TileTestMachine();
    }
}
