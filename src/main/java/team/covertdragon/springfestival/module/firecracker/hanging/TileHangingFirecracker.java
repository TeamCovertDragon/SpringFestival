/*
 * Copyright (c) 2018 CovertDragon Team.
 * Copyright (c) 2018 Contributors of SpringFestival.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package team.covertdragon.springfestival.module.firecracker.hanging;

import javax.annotation.Nullable;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import team.covertdragon.springfestival.module.firecracker.entity.EntityFirecracker;
import team.covertdragon.springfestival.module.material.MaterialRegistry;

public class TileHangingFirecracker extends TileEntity implements ITickable {
    @Nullable
    private EntityLivingBase igniter;
    public int tick;

    public TileHangingFirecracker() {
        tick = 100;
    }

    public void setIgniter(EntityLivingBase igniter) {
        this.igniter = igniter;
    }
    
    public EntityLivingBase getIgniter() {
        return this.igniter;
    }

    @Override
    public void update() {
        --tick;
        if (!world.isRemote)
        {
            if (tick > 0 && tick <= 60 && (tick % 5 == 2 || tick % 5 == 0))
            {
                double height = tick >= 60 ? 0D : 1 - tick / 60D;
                EntityFirecracker entityfirecracker = new EntityFirecracker(world, pos.getX() + 0.5D, pos.getY() + height, pos.getZ() + 0.5D, getIgniter());
                world.spawnEntity(entityfirecracker);
                if (tick % 15 == 0)
                {
                    IBlockState state = world.getBlockState(pos);
                    world.setBlockState(pos, state.withProperty(BlockHangingFirecracker.COUNT, 6 - tick / 15), 3);
                }
            }
            else if (tick <= 0)
            {
                IBlockState state = world.getBlockState(pos.up());
                if (state.getBlock() instanceof BlockHangingFirecracker)
                {
                    ((BlockHangingFirecracker)state.getBlock()).ignite(world, pos.up(), state, true, igniter);
                }
                world.setBlockToAir(pos);
            }
        }
        else if (tick % 4 == 0 && tick > 0)
        {
            double height = tick >= 60 ? 0D : 1 - tick / 60D;
            world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, pos.getX() + 0.5D, pos.getY() + height, pos.getZ() + 0.5D, 0.0D, -0.2D, 0.0D);
        }
    }
    
    @Override
    public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newState)
    {
        return oldState.getBlock() != newState.getBlock();
    }
    
    public void dropItems()
    {
        int amount = tick / 10;
        if (amount > 6) amount = 6;
        ItemStack stack = new ItemStack(MaterialRegistry.itemRedPaperBroken, amount);
        EntityItem entity = new EntityItem(world, pos.getX(), pos.getY(), pos.getZ(), stack);
        entity.setDefaultPickupDelay();
        world.spawnEntity(entity);
    }
}
