/*
 * Copyright (c) 2018 CovertDragon Team.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package team.covertdragon.springfestival.module.decoration;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.tileentity.TileEntityChestRenderer;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import team.covertdragon.springfestival.SpringFestival;
import team.covertdragon.springfestival.SpringFestivalConstants;
import team.covertdragon.springfestival.module.AbstractSpringFestivalModule;

import java.lang.reflect.Field;

@Mod.EventBusSubscriber(modid = SpringFestivalConstants.MOD_ID)
public class ModuleDecoration extends AbstractSpringFestivalModule {

    public static final BlockFuDoor blockFuDoor = (BlockFuDoor) new BlockFuDoor().setRegistryName(SpringFestivalConstants.MOD_ID, "block_fu_door");
    public static final ItemFuDoor itemFuDoor = (ItemFuDoor) new ItemFuDoor(blockFuDoor).setRegistryName(SpringFestivalConstants.MOD_ID, "item_fu_door");

    // TODO We need a way to call this guy
    public void onInit() {
        if (SpringFestival.proxy.isDuringSpringFestivalSeason()) {
            try {
                Field textureChestSingle = TileEntityChestRenderer.class.getDeclaredField("TEXTURE_NORMAL");
                Field textureChestDouble = TileEntityChestRenderer.class.getDeclaredField("TEXTURE_NORMAL_DOUBLE");
                // TODO This is it, now we only need texture.
                EnumHelper.setFailsafeFieldValue(textureChestSingle, null, new ResourceLocation("springfestival", "texture/tile/chest_single.png"));
                EnumHelper.setFailsafeFieldValue(textureChestDouble, null, new ResourceLocation("springfestival", "texture/tile/chest_double.png"));
            } catch (Exception e) {
                SpringFestivalConstants.logger.catching(e);
            }
        }
    }

    @SubscribeEvent
    public static void onItemRegister(RegistryEvent.Register<Item> event) {
        event.getRegistry().registerAll(
                itemFuDoor,
                new ItemRedClothes.RedHat(),
                new ItemRedClothes.RedGown(),
                new ItemRedClothes.RedTrousers(),
                new ItemRedClothes.RedShoes()
        );
    }

    @SubscribeEvent
    public static void onBlockRegister(RegistryEvent.Register<Block> event) {
        GameRegistry.registerTileEntity(TileFuDoor.class, "tile_fu_door");
        event.getRegistry().registerAll(
                blockFuDoor
        );
    }
}
