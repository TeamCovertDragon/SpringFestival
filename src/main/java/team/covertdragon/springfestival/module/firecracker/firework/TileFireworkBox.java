/*
 * Copyright (c) 2018 CovertDragon Team.
 * Copyright (c) 2018 Contributors of SpringFestival.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package team.covertdragon.springfestival.module.firecracker.firework;

import net.minecraft.entity.item.EntityFireworkRocket;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Items;
import net.minecraft.item.ItemDye;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import team.covertdragon.springfestival.module.firecracker.FirecrackerRegistry;

import javax.annotation.Nonnull;

public class TileFireworkBox extends TileEntity implements ITickable {
    private int count;
    private int tick;
    private boolean isActive;

    public TileFireworkBox() {
        count = 64;
        tick = 30;
        isActive = false;
    }

    @Override
    public void update() {
        if (world.isBlockPowered(pos) || world.getRedstonePowerFromNeighbors(pos) > 0) {
            setActive(true);
        }

        if (isActive) {
            tick--;
            if (tick <= 0 && count > 0) {//But when will tick < 0?
                count--;
                launchFireWork(63 - count);
                tick = 30;
            }
            if (count == 0) {
                setActive(false);
            }
        }
    }

    @Override
    @Nonnull
    public NBTTagCompound writeToNBT(@Nonnull NBTTagCompound tag) {
        super.writeToNBT(tag);
        tag.setInteger("count", count);
        tag.setBoolean("active", isActive);
        return tag;
    }

    @Override
    public void readFromNBT(@Nonnull NBTTagCompound tag) {
        super.readFromNBT(tag);
        this.count = tag.getInteger("count");
        this.isActive = tag.getBoolean("active");
    }

    public void setCount(int count) {
        this.count = count;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public int getCount() {
        return count;
    }

    public boolean isActive() {
        return isActive;
    }

    public void dropBlockAsItem() {
        ItemStack stack = new ItemStack(FirecrackerRegistry.itemFireWorkBox, 1);
        NBTTagCompound tag = new NBTTagCompound();
        tag.setInteger("count", count);
        stack.setTagCompound(tag);

        EntityItem entity = new EntityItem(world, pos.getX(), pos.getY(), pos.getZ(), stack);
        entity.setDefaultPickupDelay();
        world.spawnEntity(entity);
    }

    private void launchFireWork(double count) {
        if (world.isRemote)
            return;
        EntityFireworkRocket firework = new EntityFireworkRocket(world,
                pos.getX() + 0.111111 * (count % 8 + 1),
                pos.getY(),
                pos.getZ() + 0.111111 * ((int) (count / 8) + 1),
                makeFirework());
        world.spawnEntity(firework);
    }

    private ItemStack makeFirework() {
        NBTTagCompound tag = new NBTTagCompound();
        ItemStack stack = new ItemStack(Items.FIREWORKS);
        NBTTagList list = new NBTTagList();

        for (int i = 0; i < 5; i++) {
            list.appendTag(makeFireworkCharge());
        }

        NBTTagCompound fireworks = new NBTTagCompound();
        fireworks.setByte("Flight", (byte) ((world.rand.nextInt(2)) + 3));
        fireworks.setTag("Explosions", list);
        tag.setTag("Fireworks", fireworks);
        stack.setTagCompound(tag);

        return stack;
    }

    private NBTTagCompound makeFireworkCharge() {
        NBTTagCompound compound = new NBTTagCompound();
        int[] colors = new int[7];
        for (int i = 0; i < colors.length; i++) {
            colors[i] = ItemDye.DYE_COLORS[this.world.rand.nextInt(ItemDye.DYE_COLORS.length)];
        }

        compound.setIntArray("Colors", colors);
        compound.setByte("Type", (byte) world.rand.nextInt());

        return compound;
    }
}
