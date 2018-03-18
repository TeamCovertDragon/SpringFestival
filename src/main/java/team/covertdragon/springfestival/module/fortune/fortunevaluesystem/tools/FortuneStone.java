package team.covertdragon.springfestival.module.fortune.fortunevaluesystem.tools;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import team.covertdragon.springfestival.SpringFestivalConstants;
import team.covertdragon.springfestival.internal.network.SpringFestivalNetworkHandler;
import team.covertdragon.springfestival.module.fortune.FortuneNetwork;
import team.covertdragon.springfestival.module.fortune.fortunevaluesystem.capability.CapabilityLoader;

import javax.annotation.Nonnull;

public class FortuneStone extends Item {
    public FortuneStone() {
        setRegistryName(SpringFestivalConstants.MOD_ID, "fortune_stone");
        setUnlocalizedName(SpringFestivalConstants.MOD_ID + ".fortune_stone");
        setCreativeTab(SpringFestivalConstants.CREATIVE_TAB);
    }

    @Override
    @Nonnull
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, @Nonnull EnumHand hand) {
        if (!world.isRemote) {
            SpringFestivalNetworkHandler.INSTANCE.sendToPlayer(
                    new FortuneNetwork.packetFortuneValue(player.getCapability(CapabilityLoader.fortuneValue, null)
                            .getFortuneValue()), (EntityPlayerMP) player);
        }
        return new ActionResult<>(EnumActionResult.SUCCESS, player.getHeldItem(hand));
    }
}
