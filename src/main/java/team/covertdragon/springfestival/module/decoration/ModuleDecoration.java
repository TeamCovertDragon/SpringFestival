/*
 * Copyright (c) 2018 CovertDragon Team.
 * Copyright (c) 2018 Contributors of SpringFestival.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package team.covertdragon.springfestival.module.decoration;

import net.minecraft.block.Block;
import net.minecraft.block.BlockDoor;
import net.minecraft.client.renderer.block.statemap.StateMap;
import net.minecraft.client.renderer.tileentity.TileEntityChestRenderer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import team.covertdragon.springfestival.SpringFestival;
import team.covertdragon.springfestival.SpringFestivalConstants;
import team.covertdragon.springfestival.internal.model.ModelUtil;
import team.covertdragon.springfestival.internal.network.SpringFestivalNetworkHandler;
import team.covertdragon.springfestival.module.AbstractSpringFestivalModule;
import team.covertdragon.springfestival.module.SpringFestivalModule;
import team.covertdragon.springfestival.module.decoration.clothes.ItemRedClothes;
import team.covertdragon.springfestival.module.decoration.fudoor.BlockFuDoor;
import team.covertdragon.springfestival.module.decoration.fudoor.ItemFu;
import team.covertdragon.springfestival.module.decoration.fudoor.ItemFuDoor;
import team.covertdragon.springfestival.module.decoration.fudoor.ServerPacketFuDoorCreation;
import team.covertdragon.springfestival.module.decoration.fudoor.TESRFuDoor;
import team.covertdragon.springfestival.module.decoration.fudoor.TileFuDoor;
import team.covertdragon.springfestival.module.decoration.redpillar.BlockLargeRedPillar;
import team.covertdragon.springfestival.module.decoration.redpillar.BlockLargeRedPillarAlt;

import java.lang.reflect.Field;

@SpringFestivalModule(name = "decoration", dependencies = {"material"})
public class ModuleDecoration extends AbstractSpringFestivalModule {

    public void onInit() {
        SpringFestivalNetworkHandler.INSTANCE.registerPacket(ServerPacketFuDoorCreation.class);
        if (SpringFestival.proxy.isPhysicalClient() && SpringFestival.proxy.isDuringSpringFestivalSeason()) {
            try {
                Field textureChestSingle = TileEntityChestRenderer.class.getDeclaredField("TEXTURE_NORMAL");
                Field textureChestDouble = TileEntityChestRenderer.class.getDeclaredField("TEXTURE_NORMAL_DOUBLE");
                Field textureTrappedSingle = TileEntityChestRenderer.class.getDeclaredField("TEXTURE_TRAPPED");
                Field textureTrappedDouble = TileEntityChestRenderer.class.getDeclaredField("TEXTURE_TRAPPED_DOUBLE");
                EnumHelper.setFailsafeFieldValue(textureChestSingle, null, new ResourceLocation(SpringFestivalConstants.MOD_ID, "textures/entity/chest.png"));
                EnumHelper.setFailsafeFieldValue(textureChestDouble, null, new ResourceLocation(SpringFestivalConstants.MOD_ID, "textures/entity/chest_double.png"));
                EnumHelper.setFailsafeFieldValue(textureTrappedSingle, null, new ResourceLocation(SpringFestivalConstants.MOD_ID, "textures/entity/chest.png"));
                EnumHelper.setFailsafeFieldValue(textureTrappedDouble, null, new ResourceLocation(SpringFestivalConstants.MOD_ID, "textures/entity/chest_double.png"));
            } catch (Exception e) {
                SpringFestivalConstants.logger.catching(e);
            }
        }
    }

    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public void onModelRegister(ModelRegistryEvent event) {
        ModelLoader.setCustomStateMapper(DecorationRegistry.FU_DOOR, new StateMap.Builder().ignore(BlockDoor.POWERED).build());
        ModelUtil.mapItemModel(DecorationRegistry.FU_DOOR_ITEM);
        ModelUtil.mapItemModel(DecorationRegistry.RED_HAT);
        ModelUtil.mapItemModel(DecorationRegistry.RED_GOWN);
        ModelUtil.mapItemModel(DecorationRegistry.RED_TROUSERS);
        ModelUtil.mapItemModel(DecorationRegistry.RED_SHOES);
        ModelUtil.mapItemModel(DecorationRegistry.itemLargeRedPillar);
        ModelUtil.mapItemModel(DecorationRegistry.itemLargeRedPillarAlt);
        ModelUtil.mapItemModel(DecorationRegistry.itemLargeRedPillar);
        ModelUtil.mapItemModel(DecorationRegistry.itemLargeRedPillarAlt);

        ClientRegistry.bindTileEntitySpecialRenderer(TileFuDoor.class, new TESRFuDoor());
    }

    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public void onTextureStitch(TextureStitchEvent event) {
        // Inject the Fu character overlay to the texture map
        event.getMap().registerSprite(new ResourceLocation(TESRFuDoor.FU_TEXTURE_LOCATION));
    }

    @SubscribeEvent
    public void onItemRegister(RegistryEvent.Register<Item> event) {
        event.getRegistry().registerAll(
                new ItemFu(),
                new ItemFuDoor(DecorationRegistry.FU_DOOR),
                new ItemRedClothes.RedHat(),
                new ItemRedClothes.RedGown(),
                new ItemRedClothes.RedTrousers(),
                new ItemRedClothes.RedShoes(),
                new ItemBlock(DecorationRegistry.blockLargeRedPillar)
                        .setRegistryName(SpringFestivalConstants.MOD_ID,"large_red_pillar")
                        .setUnlocalizedName(SpringFestivalConstants.MOD_ID+".large_red_pillar"),
                new ItemBlock(DecorationRegistry.blockLargeRedPillarAlt)
                        .setRegistryName(SpringFestivalConstants.MOD_ID,"large_red_pillar_alt")
                        .setUnlocalizedName(SpringFestivalConstants.MOD_ID+".large_red_pillar_alt")
        );
    }

    @SubscribeEvent
    public void onBlockRegister(RegistryEvent.Register<Block> event) {
        GameRegistry.registerTileEntity(TileFuDoor.class, "tile_fu_door");
        event.getRegistry().registerAll(
                new BlockFuDoor(),
                new BlockLargeRedPillar(),
                new BlockLargeRedPillarAlt()
        );
    }

    @SubscribeEvent
    public void onBlockBreak(BlockEvent.BreakEvent event) {
        if (!event.getWorld().isRemote) {
            if (event.getState().getBlock() instanceof BlockFuDoor && event.getState().getValue(BlockDoor.HALF) == BlockDoor.EnumDoorHalf.LOWER) {
                event.setCanceled(true);
                DecorationRegistry.FU_DOOR.harvestBlock(event.getWorld(), event.getPlayer(), event.getPos().add(0, 1, 0), event.getState(), event.getWorld().getTileEntity(event.getPos().add(0, 1, 0)), event.getPlayer().getHeldItem(event.getPlayer().getActiveHand()));
                event.getWorld().setBlockToAir(event.getPos());
            }
        }
    }
}
