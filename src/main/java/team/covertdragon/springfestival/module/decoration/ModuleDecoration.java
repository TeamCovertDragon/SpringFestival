/*
 * Copyright (c) 2018 CovertDragon Team.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package team.covertdragon.springfestival.module.decoration;

import net.minecraft.block.Block;
import net.minecraft.block.BlockDoor;
import net.minecraft.client.renderer.tileentity.TileEntityChestRenderer;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import team.covertdragon.springfestival.SpringFestival;
import team.covertdragon.springfestival.SpringFestivalConstants;
import team.covertdragon.springfestival.internal.client.SpringFestivalProxyClient;
import team.covertdragon.springfestival.internal.model.ModelUtil;
import team.covertdragon.springfestival.module.AbstractSpringFestivalModule;
import team.covertdragon.springfestival.module.SpringFestivalModule;

import java.lang.reflect.Field;

@SpringFestivalModule(name = "decoration", dependencies = {"material"})
public class ModuleDecoration extends AbstractSpringFestivalModule {

    public static final BlockFuDoor blockFuDoor = (BlockFuDoor) new BlockFuDoor().setRegistryName(SpringFestivalConstants.MOD_ID, "block_fu_door");
    public static final ItemFuDoor itemFuDoor = (ItemFuDoor) new ItemFuDoor(blockFuDoor).setRegistryName(SpringFestivalConstants.MOD_ID, "item_fu_door");

    // TODO We need a way to call this guy
    public void onInit() {
        if (SpringFestival.proxy instanceof SpringFestivalProxyClient && SpringFestival.proxy.isDuringSpringFestivalSeason()) {
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
    @SideOnly(Side.CLIENT)
    public void onModelRegister(ModelRegistryEvent event) {
        ModelUtil.mapItemModel(DecorationRegistry.red_hat);
        ModelUtil.mapItemModel(DecorationRegistry.red_gown);
        ModelUtil.mapItemModel(DecorationRegistry.red_trousers);
        ModelUtil.mapItemModel(DecorationRegistry.red_shoes);
    }

    @SubscribeEvent
    public void onItemRegister(RegistryEvent.Register<Item> event) {
        event.getRegistry().registerAll(
                itemFuDoor,
                new ItemRedClothes.RedHat(),
                new ItemRedClothes.RedGown(),
                new ItemRedClothes.RedTrousers(),
                new ItemRedClothes.RedShoes()
        );
    }

    @SubscribeEvent
    public void onBlockRegister(RegistryEvent.Register<Block> event) {
        GameRegistry.registerTileEntity(TileFuDoor.class, "tile_fu_door");
        event.getRegistry().registerAll(
                blockFuDoor
        );
    }

    @SubscribeEvent
    public void onBlockBreak(BlockEvent.BreakEvent event) {
        if (!event.getWorld().isRemote) {
            if (event.getState().getBlock() instanceof BlockFuDoor && event.getState().getValue(BlockDoor.HALF) == BlockDoor.EnumDoorHalf.LOWER) {
                event.setCanceled(true);
                blockFuDoor.harvestBlock(event.getWorld(), event.getPlayer(), event.getPos().add(0, 1, 0), event.getState(), event.getWorld().getTileEntity(event.getPos().add(0, 1, 0)), event.getPlayer().getHeldItem(event.getPlayer().getActiveHand()));
                event.getWorld().setBlockToAir(event.getPos());
            }
        }
    }
}
