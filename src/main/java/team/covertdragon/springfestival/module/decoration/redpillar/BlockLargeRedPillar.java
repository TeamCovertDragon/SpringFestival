package team.covertdragon.springfestival.module.decoration.redpillar;

import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import team.covertdragon.springfestival.SpringFestivalConstants;

public class BlockLargeRedPillar extends Block {
    public BlockLargeRedPillar() {
        super(Material.WOOD, MapColor.RED);
        setRegistryName(SpringFestivalConstants.MOD_ID, "large_red_pillar");
        setUnlocalizedName(SpringFestivalConstants.MOD_ID + ".large_red_pillar");
        setHardness(2.5F);
        setHarvestLevel("axe", 0);
        setResistance(10.0F);
        setCreativeTab(SpringFestivalConstants.CREATIVE_TAB);
    }
}
