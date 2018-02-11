package team.covertdragon.springfestival.module.redpacket;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import team.covertdragon.springfestival.SpringFestivalConstants;

@SideOnly(Side.CLIENT)
@Mod.EventBusSubscriber(modid = SpringFestivalConstants.MOD_ID, value = Side.CLIENT)
public class ModelRegistriesRedPacket {
    public static void registerItem(Item item) {
        ModelLoader.setCustomModelResourceLocation(item, 0,
                new ModelResourceLocation(item.getRegistryName().toString(), "inventory"));
    }

    @SubscribeEvent
    public static void onModelRegistries(ModelRegistryEvent event) {
        registerItem(ModuleRedPacket.redpacket);
    }
}
