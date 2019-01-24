package team.covertdragon.springfestival.fortune.tools;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import team.covertdragon.springfestival.SpringFestivalConstants;
import team.covertdragon.springfestival.fortune.machines.AbstractTileFVMachine;

public class DebugTool extends Item {

    public DebugTool() {
        setCreativeTab(SpringFestivalConstants.CREATIVE_TAB);
        setUnlocalizedName(SpringFestivalConstants.MOD_ID + ".debug_tool");
        setRegistryName(SpringFestivalConstants.MOD_ID, "debug_tool");
    }

    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (world.isRemote) return EnumActionResult.SUCCESS;
        TileEntity te = world.getTileEntity(pos);
        if (!(te instanceof AbstractTileFVMachine)) return EnumActionResult.FAIL;
        AbstractTileFVMachine cast = (AbstractTileFVMachine) te;
        player.sendMessage(new TextComponentTranslation("Owner: " + cast.getOwner()));
        player.sendMessage(new TextComponentTranslation("FVID: " + cast.getId()));
        return EnumActionResult.SUCCESS;
    }
}
