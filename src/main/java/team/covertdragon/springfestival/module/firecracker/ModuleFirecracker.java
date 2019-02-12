/*
 * Copyright (c) 2018 CovertDragon Team.
 * Copyright (c) 2018 Contributors of SpringFestival.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package team.covertdragon.springfestival.module.firecracker;

import java.lang.reflect.Field;

import net.minecraft.block.Block;
import net.minecraft.block.BlockDispenser;
import net.minecraft.block.state.IBlockState;
import net.minecraft.dispenser.BehaviorProjectileDispense;
import net.minecraft.dispenser.IBehaviorDispenseItem;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.dispenser.IPosition;
import net.minecraft.entity.Entity;
import net.minecraft.entity.IProjectile;
import net.minecraft.entity.ai.EntityAIAvoidEntity;
import net.minecraft.entity.ai.EntityAITasks.EntityAITaskEntry;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.init.Bootstrap;
import net.minecraft.init.Bootstrap.BehaviorDispenseOptional;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.world.ExplosionEvent;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.fml.common.registry.EntityEntryBuilder;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import team.covertdragon.springfestival.SpringFestivalConstants;
import team.covertdragon.springfestival.internal.model.ModelUtil;
import team.covertdragon.springfestival.module.AbstractSpringFestivalModule;
import team.covertdragon.springfestival.module.SpringFestivalModule;
import team.covertdragon.springfestival.module.firecracker.entity.EntityFirecracker;
import team.covertdragon.springfestival.module.firecracker.entity.ItemFirecrackerEgg;
import team.covertdragon.springfestival.module.firecracker.entity.RenderEntityFirecracker;
import team.covertdragon.springfestival.module.firecracker.firework.BlockFireworkBox;
import team.covertdragon.springfestival.module.firecracker.firework.ItemFireworkBox;
import team.covertdragon.springfestival.module.firecracker.firework.TileFireworkBox;
import team.covertdragon.springfestival.module.firecracker.hanging.BlockHangingFirecracker;
import team.covertdragon.springfestival.module.firecracker.hanging.TileHangingFirecracker;

@SpringFestivalModule(name = "firecracker", dependencies = {"material"})
public class ModuleFirecracker extends AbstractSpringFestivalModule {
    // TODO Albedo support? Are we sure on this one?
    // TODO The compatibility should be done via {@link net.minecraftforge.fml.common.Optional.Interface}
//    public static Boolean useFancyLighting;
    /*
     * We use the ObfuscationReflectionHelper because ReflectionHelper under relauncher package is in the Land of C***mod,
     * implying that those two classes are on different class loaders. Since we are regular FML mods, we use the one
     * that is on the same class loader as of which we are on.
     */
    private static final Field FIELD_AVOID_CLASS;
    // TODO (3TUSK): I guess we can actually cast that instance of anonymous class to Bootstrap.BehaviorDispenseOptional,
    //  so we don't need extra reflection.
    private static final Field FIELD_DISPENSE_RESULT;

    static {
        FIELD_AVOID_CLASS = ObfuscationReflectionHelper.findField(EntityAIAvoidEntity.class, "field_181064_i");
        FIELD_DISPENSE_RESULT = ObfuscationReflectionHelper.findField(Bootstrap.BehaviorDispenseOptional.class, "field_190911_b");
        FIELD_AVOID_CLASS.setAccessible(true);
        FIELD_DISPENSE_RESULT.setAccessible(true);
    }

    @SubscribeEvent
    public void entityRegistration(RegistryEvent.Register<EntityEntry> event) {
        event.getRegistry().register(EntityEntryBuilder.create()
                .entity(EntityFirecracker.class)
                .id(new ResourceLocation(SpringFestivalConstants.MOD_ID, "firecracker"), 0)
                .name("springfestival.firecracker")
                .tracker(80, 3, true)
                .build());
    }

    @Override
    public void onInit() {
        BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(FirecrackerRegistry.FIRECRACKER_EGG, new BehaviourFirecrackerDispense());
        BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(
                Items.FLINT_AND_STEEL,
                new BehaviourFlintAndSteelDispense(BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.getObject(Items.FLINT_AND_STEEL))
        );
//        useFancyLighting = Loader.isModLoaded("albedo");
    }

    @SubscribeEvent
    public void onEntityJoin(EntityJoinWorldEvent event) {
        if (!event.getWorld().isRemote && event.getEntity() instanceof EntityMob) {
            EntityMob mob = (EntityMob) event.getEntity();
            for (EntityAITaskEntry task : mob.tasks.taskEntries)
                try {
                    if (task.action instanceof EntityAIAvoidEntity) {
                        if (FIELD_AVOID_CLASS.get(task.action) == EntityFirecracker.class) {
                            return;
                        }
                    }
                } catch (Exception e) {
                    SpringFestivalConstants.logger.catching(e);
                }

            mob.tasks.addTask(1, new EntityAIAvoidEntity<>(mob, EntityFirecracker.class, 6.0F, 1.0D, 1.2D));
        }
    }

    @SubscribeEvent
    public void onExplosionDetonate(ExplosionEvent.Detonate event) {
        event.getAffectedEntities().removeIf(e -> e instanceof EntityXPOrb || e instanceof EntityItem);
    }

    @SubscribeEvent
    public void onBlockRegister(RegistryEvent.Register<Block> event) {
        GameRegistry.registerTileEntity(TileFireworkBox.class, new ResourceLocation(SpringFestivalConstants.MOD_ID, "tile_firework_box"));
        GameRegistry.registerTileEntity(TileHangingFirecracker.class, new ResourceLocation(SpringFestivalConstants.MOD_ID, "tile_hanging_firecracker"));
        event.getRegistry().registerAll(
                new BlockHangingFirecracker(),
                new BlockFireworkBox()
        );
    }

    @SubscribeEvent
    public void onItemRegister(RegistryEvent.Register<Item> event) {
        event.getRegistry().registerAll(
                new ItemFireworkBox(FirecrackerRegistry.FIREWORK_BOX)
                        .setTranslationKey(SpringFestivalConstants.MOD_ID + ".firework_box")
                        .setRegistryName(SpringFestivalConstants.MOD_ID, "firework_box"),
                new ItemFirecrackerEgg().setRegistryName(SpringFestivalConstants.MOD_ID, "firecracker_egg"),
                new ItemBlock(FirecrackerRegistry.HANGING_FIRECRACKER).setRegistryName(SpringFestivalConstants.MOD_ID, "hanging_firecracker")
        );
    }

    @SubscribeEvent
    public void onSoundEventRegistry(RegistryEvent.Register<SoundEvent> event) {
        event.getRegistry().registerAll(
                new SoundEvent(new ResourceLocation(SpringFestivalConstants.MOD_ID, "firecracker.throw"))
                        .setRegistryName(SpringFestivalConstants.MOD_ID, "firecracker_throw"),
                new SoundEvent(new ResourceLocation(SpringFestivalConstants.MOD_ID, "firecracker.explode"))
                        .setRegistryName(SpringFestivalConstants.MOD_ID, "firecracker_explode")
        );
    }

    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public void onModelRegister(ModelRegistryEvent event) {
        ModelUtil.mapItemModel(FirecrackerRegistry.FIREWORK_BOX);
        ModelUtil.mapItemModel(FirecrackerRegistry.FIRECRACKER_EGG);
        ModelUtil.mapItemModel(FirecrackerRegistry.HANGING_FIRECRACKER);
        RenderingRegistry.registerEntityRenderingHandler(EntityFirecracker.class, RenderEntityFirecracker.FACTORY);
    }

    public class BehaviourFirecrackerDispense extends BehaviorProjectileDispense {

        @Override
        public ItemStack dispenseStack(IBlockSource source, ItemStack stack) {
            World world = source.getWorld();
            IPosition position = BlockDispenser.getDispensePosition(source);
            EnumFacing facing = source.getBlockState().getValue(BlockDispenser.FACING);
            IProjectile projectile = this.getProjectileEntity(world, position, stack);
            projectile.shoot(facing.getXOffset(), facing.getYOffset() + 0.1F, facing.getZOffset(), facing != EnumFacing.UP ? 0.943F : 1.2450F, 0.233F);
            world.spawnEntity((Entity) projectile);
            stack.shrink(1);
            return stack;
        }

        @Override
        protected IProjectile getProjectileEntity(World worldIn, IPosition position, ItemStack stackIn) {
            return new EntityFirecracker(worldIn, position.getX(), position.getY(), position.getZ(), null);
        }

    }

    public class BehaviourFlintAndSteelDispense extends BehaviorDispenseOptional {
        private final IBehaviorDispenseItem behavior;

        protected BehaviourFlintAndSteelDispense(IBehaviorDispenseItem behavior) {
            this.behavior = behavior;
        }

        protected ItemStack dispenseStack(IBlockSource source, ItemStack stack) {
            behavior.dispense(source, stack);
            try {
                if (!FIELD_DISPENSE_RESULT.getBoolean(behavior)) {
                    World world = source.getWorld();
                    BlockPos pos = source.getBlockPos().offset(source.getBlockState().getValue(BlockDispenser.FACING));
                    IBlockState state = world.getBlockState(pos);
                    if (state.getBlock() == FirecrackerRegistry.HANGING_FIRECRACKER && state.getValue(BlockHangingFirecracker.COUNT) == 0) {
                        // TODO (3TUSK): need a way to avoid casting
                        ((BlockHangingFirecracker)FirecrackerRegistry.HANGING_FIRECRACKER).ignite(world, pos, state, false, null);
                        this.successful = true;
                    }
                }
            } catch (Exception e) {
                SpringFestivalConstants.logger.catching(e);
            }
            return stack;
        }
    }
}
