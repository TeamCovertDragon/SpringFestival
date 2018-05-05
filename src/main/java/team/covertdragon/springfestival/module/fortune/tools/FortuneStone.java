package team.covertdragon.springfestival.module.fortune.tools;

import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import team.covertdragon.springfestival.SpringFestivalConstants;
import team.covertdragon.springfestival.internal.network.SpringFestivalNetworkHandler;
import team.covertdragon.springfestival.module.fortune.FortuneClientHelper;
import team.covertdragon.springfestival.module.fortune.FortuneNetwork;

import javax.annotation.Nullable;
import java.util.List;

public class FortuneStone extends Item {
    public FortuneStone() {
        setRegistryName(SpringFestivalConstants.MOD_ID, "fortune_stone");
        setUnlocalizedName(SpringFestivalConstants.MOD_ID + ".fortune_stone");
        setCreativeTab(SpringFestivalConstants.CREATIVE_TAB);
        setMaxStackSize(1);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        super.addInformation(stack, worldIn, tooltip, flagIn);
        SpringFestivalNetworkHandler.INSTANCE.sendToServer(new FortuneNetwork.packetRequestFortuneValue());
        tooltip.add(I18n.format("tooltip.springfestival.fv", FortuneClientHelper.fortune_value));
    }
}
