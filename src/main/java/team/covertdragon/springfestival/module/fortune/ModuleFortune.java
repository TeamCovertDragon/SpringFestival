/*
 * Copyright (c) 2018 CovertDragon Team.
 * Copyright (c) 2018 Contributors of SpringFestival.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package team.covertdragon.springfestival.module.fortune;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.nbt.NBTBase;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import team.covertdragon.springfestival.SpringFestivalConstants;
import team.covertdragon.springfestival.internal.model.ModelUtil;
import team.covertdragon.springfestival.module.AbstractSpringFestivalModule;
import team.covertdragon.springfestival.module.SpringFestivalModule;
import team.covertdragon.springfestival.module.fortune.fortunevaluesystem.FortuneValueManager;
import team.covertdragon.springfestival.module.fortune.fortunevaluesystem.capability.CapabilityFortuneValueSystem;
import team.covertdragon.springfestival.module.fortune.fortunevaluesystem.capability.CapabilityLoader;
import team.covertdragon.springfestival.module.fortune.fortunevaluesystem.capability.IFortuneValueSystem;
import team.covertdragon.springfestival.module.fortune.machines.ItemBlockFVMachine;
import team.covertdragon.springfestival.module.fortune.machines.collector.BasicFVCollector;
import team.covertdragon.springfestival.module.fortune.machines.collector.TileBasicFVCollector;
import team.covertdragon.springfestival.module.fortune.tools.DebugTool;
import team.covertdragon.springfestival.module.fortune.tools.FortuneStone;

@SpringFestivalModule(name = "fortune", dependencies = {"material"})
public class ModuleFortune extends AbstractSpringFestivalModule {
    public static FortuneValueManager manager;
    private static Thread FV_MANAGER_THREAD;

    @Override
    public void onPreInit() {
        CapabilityLoader.init();
    }

    @Override
    public void onInit() {
        FortuneNetwork.init();
    }

    @Override
    public void onServerStarting() {
        // Start Fortune Thread
        manager = new FortuneValueManager();
        FV_MANAGER_THREAD = new Thread(manager, "SpringFestival-FVManager");
        FV_MANAGER_THREAD.setDaemon(true);
        manager.alive = true;
        FV_MANAGER_THREAD.start();
    }

    @Override
    public void onServerStopping() {
        SpringFestivalConstants.logger.info("Shutting down fv manager...");
        manager.alive = false;
        try {
            FV_MANAGER_THREAD.join();
        } catch (InterruptedException e) {
            SpringFestivalConstants.logger.error("Fail to shutdown FV_MANAGER thread", e);
        }
    }

    @SubscribeEvent
    public void onModelRegister(ModelRegistryEvent event) {
        ModelUtil.mapItemModel(FortuneRegistry.fortuneStone);
        ModelUtil.mapItemModel(FortuneRegistry.fortuneStone, 233);
        ModelUtil.mapItemModel(FortuneRegistry.itemBasicFVCollector);
        ModelUtil.mapItemModel(FortuneRegistry.debugTool);
    }

    @SubscribeEvent
    public void onBlockRegistry(RegistryEvent.Register<Block> event) {
        GameRegistry.registerTileEntity(TileBasicFVCollector.class, SpringFestivalConstants.MOD_ID + ".fv_collector");
        event.getRegistry().registerAll(
                new BasicFVCollector()
        );
    }

    @SubscribeEvent
    public void onItemRegistry(RegistryEvent.Register<Item> event) {
        event.getRegistry().registerAll(
                new FortuneStone(),
                new ItemBlockFVMachine(FortuneRegistry.basicFVCollector),
                new DebugTool()
        );
    }

    @SubscribeEvent
    public void onAttachCapabilitiesEntity(AttachCapabilitiesEvent<Entity> event) {
        if (event.getObject() instanceof EntityPlayer) {
            event.addCapability(new ResourceLocation(SpringFestivalConstants.MOD_ID, "fv_system"),
                    new CapabilityFortuneValueSystem.PlayerProvider());
        }
    }

    @SubscribeEvent
    public void onPlayerClone(net.minecraftforge.event.entity.player.PlayerEvent.Clone event) {
        Capability<IFortuneValueSystem> capability = CapabilityLoader.fortuneValue;
        Capability.IStorage<IFortuneValueSystem> storage = capability.getStorage();

        if (event.getOriginal().hasCapability(capability, null) && event.getEntityPlayer().hasCapability(capability, null)) {
            NBTBase nbt = storage.writeNBT(capability, event.getOriginal().getCapability(capability, null), null);
            storage.readNBT(capability, event.getEntityPlayer().getCapability(capability, null), null, nbt);
        }
    }
}
