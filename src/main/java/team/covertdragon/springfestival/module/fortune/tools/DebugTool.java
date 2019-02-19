package team.covertdragon.springfestival.module.fortune.tools;

import net.minecraft.item.Item;
import net.minecraft.item.ItemUseContext;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.text.TextComponentTranslation;
import team.covertdragon.springfestival.module.fortune.machines.AbstractTileFVMachine;

public class DebugTool extends Item {

    public DebugTool(Properties properties) {
        super(properties);
    }

    @Override
    public EnumActionResult onItemUse(ItemUseContext context) {
        if (context.getWorld().isRemote) {
            return EnumActionResult.SUCCESS;
        }

        TileEntity te = context.getWorld().getTileEntity(context.getPos());
        if ((!(te instanceof  AbstractTileFVMachine))) {
            return EnumActionResult.FAIL;
        }
        AbstractTileFVMachine cast = (AbstractTileFVMachine) te;
        context.getPlayer().sendMessage(new TextComponentTranslation("Owner: " + cast.getOwner()));
        context.getPlayer().sendMessage(new TextComponentTranslation("FVID: " + cast.getId()));
        return EnumActionResult.SUCCESS;
    }

}
