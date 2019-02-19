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
import net.minecraft.block.BlockRotatedPillar;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.loading.FMLEnvironment;
import team.covertdragon.springfestival.SpringFestivalConstants;
import team.covertdragon.springfestival.internal.network.SpringFestivalNetworkHandler;
import team.covertdragon.springfestival.internal.time.SpringFestivalTimeChecker;
import team.covertdragon.springfestival.module.AbstractSpringFestivalModule;
import team.covertdragon.springfestival.module.SpringFestivalModule;
import team.covertdragon.springfestival.module.decoration.clothes.ItemRedClothes;
import team.covertdragon.springfestival.module.decoration.fudoor.BlockFuDoor;
import team.covertdragon.springfestival.module.decoration.fudoor.ItemFu;
import team.covertdragon.springfestival.module.decoration.fudoor.ServerPacketFuDoorCreation;
import team.covertdragon.springfestival.module.decoration.fudoor.TileFuDoor;

@Mod.EventBusSubscriber(modid = SpringFestivalConstants.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
@SpringFestivalModule(name = "decoration", dependencies = {"material"})
public class ModuleDecoration extends AbstractSpringFestivalModule {

    public void onInit() {
        SpringFestivalNetworkHandler.INSTANCE.registerPacket(ServerPacketFuDoorCreation.class);
        if (FMLEnvironment.dist == Dist.CLIENT && SpringFestivalTimeChecker.INSTANCE.isDuringSpringFestivalSeason()) {
            /*try {
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
            }*/
        }
    }

    /*@OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void onModelRegister(ModelRegistryEvent event) {

        ClientRegistry.bindTileEntitySpecialRenderer(TileFuDoor.class, new TESRFuDoor());
    }

    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public void onTextureStitch(TextureStitchEvent event) {
        // Inject the Fu character overlay to the texture map
        event.getMap().registerSprite(new ResourceLocation(TESRFuDoor.FU_TEXTURE_LOCATION));
    }*/

    @SubscribeEvent
    public static void onItemRegister(RegistryEvent.Register<Item> event) {
        event.getRegistry().registerAll(
                new ItemFu(new Item.Properties().group(SpringFestivalConstants.SPRING_GROUP))
                        .setRegistryName(SpringFestivalConstants.MOD_ID, "fu"),
                new ItemRedClothes.RedHat(new Item.Properties().group(SpringFestivalConstants.SPRING_GROUP))
                        .setRegistryName(SpringFestivalConstants.MOD_ID, "red_hat"),
                new ItemRedClothes.RedGown(new Item.Properties().group(SpringFestivalConstants.SPRING_GROUP))
                        .setRegistryName(SpringFestivalConstants.MOD_ID, "red_gown"),
                new ItemRedClothes.RedTrousers(new Item.Properties().group(SpringFestivalConstants.SPRING_GROUP))
                        .setRegistryName(SpringFestivalConstants.MOD_ID, "red_trousers"),
                new ItemRedClothes.RedShoes(new Item.Properties().group(SpringFestivalConstants.SPRING_GROUP))
                        .setRegistryName(SpringFestivalConstants.MOD_ID, "red_shoes"),
                new ItemBlock(DecorationRegistry.LARGE_RED_PILLAR, new Item.Properties().group(SpringFestivalConstants.SPRING_GROUP))
                        .setRegistryName(SpringFestivalConstants.MOD_ID, "large_red_pillar"),
                new ItemBlock(DecorationRegistry.LARGE_RED_PILLAR_ALT, new Item.Properties().group(SpringFestivalConstants.SPRING_GROUP))
                        .setRegistryName(SpringFestivalConstants.MOD_ID, "large_red_pillar_alt")
        );
    }

    @SubscribeEvent
    public static void onBlockRegister(RegistryEvent.Register<Block> event) {
        event.getRegistry().registerAll(
                new BlockFuDoor(Block.Properties.create(Material.WOOD).hardnessAndResistance(1.5F))
                        .setRegistryName(SpringFestivalConstants.MOD_ID, "fu_door"),
                new BlockRotatedPillar(Block.Properties // setHarvestLevel("pickaxe", 2)
                        .create(Material.ROCK, MaterialColor.RED)
                        .hardnessAndResistance(2.5F, 10F)
                        .sound(SoundType.STONE))
                        .setRegistryName(SpringFestivalConstants.MOD_ID, "large_red_pillar"),
                new BlockRotatedPillar(Block.Properties // setHarvestLevel("pickaxe", 2)
                        .create(Material.ROCK, MaterialColor.RED)
                        .hardnessAndResistance(2.5F, 10F)
                        .sound(SoundType.STONE))
                        .setRegistryName(SpringFestivalConstants.MOD_ID, "large_red_pillar_alt")
        );
    }

    @SubscribeEvent
    public static void onTileEntityRegister(RegistryEvent.Register<TileEntityType<?>> event) {
        event.getRegistry().register(TileFuDoor.TYPE_TOKEN.setRegistryName(SpringFestivalConstants.MOD_ID, "tile_fu_door"));
    }
/*
    @SubscribeEvent
    public void onBlockBreak(BlockEvent.BreakEvent event) {
        if (!event.getWorld().isRemote) {
            if (event.getState().getBlock() instanceof BlockFuDoor && event.getState().getValue(BlockDoor.HALF) == BlockDoor.EnumDoorHalf.LOWER) {
                event.setCanceled(true);
                DecorationRegistry.FU_DOOR.harvestBlock(event.getWorld(), event.getPlayer(), event.getPos().add(0, 1, 0), event.getState(), event.getWorld().getTileEntity(event.getPos().add(0, 1, 0)), event.getPlayer().getHeldItem(event.getPlayer().getActiveHand()));
                event.getWorld().setBlockToAir(event.getPos());
            }
        }
    }*/
}
