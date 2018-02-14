package team.covertdragon.springfestival.module.decoration.redpillar;

import net.minecraft.block.BlockRotatedPillar;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import team.covertdragon.springfestival.SpringFestivalConstants;

public class BlockLargeRedPillarAlt extends BlockRotatedPillar{
    public BlockLargeRedPillarAlt() {
        super(Material.ROCK, MapColor.RED);
        setSoundType(SoundType.STONE);
        setRegistryName(SpringFestivalConstants.MOD_ID, "large_red_pillar_alt");
        setUnlocalizedName(SpringFestivalConstants.MOD_ID + ".large_red_pillar_alt");
        setHardness(2.5F);
        setHarvestLevel("pickaxe", 2);
        setResistance(10.0F);
        setCreativeTab(SpringFestivalConstants.CREATIVE_TAB);
    }
}
