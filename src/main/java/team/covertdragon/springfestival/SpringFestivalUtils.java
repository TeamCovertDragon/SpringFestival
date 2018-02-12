package team.covertdragon.springfestival;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import team.covertdragon.springfestival.module.decoration.DecorationRegistry;

/**
 * @deprecated Use {@link team.covertdragon.springfestival.internal.model.ModelUtil}
 */
@Deprecated
public class SpringFestivalUtils {
    public static void registryModel(Item item){
        ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(SpringFestivalConstants.MOD_ID + ":" + item.getRegistryName(), "inventory"));
    }
}
