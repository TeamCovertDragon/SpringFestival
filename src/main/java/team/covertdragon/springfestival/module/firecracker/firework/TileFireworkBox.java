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
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemDye;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.ITickable;
import team.covertdragon.springfestival.module.firecracker.FirecrackerRegistry;

import javax.annotation.Nonnull;

public class TileFireworkBox extends TileEntity implements ITickable {

    public static final TileEntityType<TileFireworkBox> TYPE_TOKEN = new TileEntityType<>(TileFireworkBox::new, null);

    private int count;
    private int tick;
    private boolean isActive;

    public TileFireworkBox() {
        super(TYPE_TOKEN);
        count = 64;
        tick = 30;
        isActive = false;
    }

    @Override
    public void tick() {
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
    public NBTTagCompound write(@Nonnull NBTTagCompound tag) {
        tag.putInt("count", count);
        tag.putBoolean("active", isActive);
        return super.write(tag);
    }

    @Override
    public void read(@Nonnull NBTTagCompound tag) {
        super.read(tag);
        this.count = tag.getInt("count");
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
        ItemStack stack = new ItemStack(FirecrackerRegistry.FIREWORK_BOX, 1);
        NBTTagCompound tag = new NBTTagCompound();
        tag.putInt("count", count);
        stack.setTag(tag);

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
        ItemStack stack = new ItemStack(Items.FIREWORK_ROCKET);
        NBTTagList list = new NBTTagList();

        for (int i = 0; i < 5; i++) {
            list.add(makeFireworkCharge());
        }

        NBTTagCompound fireworks = new NBTTagCompound();
        fireworks.putByte("Flight", (byte) ((world.rand.nextInt(2)) + 3));
        fireworks.put("Explosions", list);
        tag.put("Fireworks", fireworks);
        stack.setTag(tag);

        return stack;
    }

    private NBTTagCompound makeFireworkCharge() {
        NBTTagCompound compound = new NBTTagCompound();
        int[] colors = new int[7];
        for (int i = 0; i < colors.length; i++) {
            // TODO (3TUSK): should we just assume upper bound to be 16?
            colors[i] = EnumDyeColor.byId(this.world.rand.nextInt(EnumDyeColor.values().length)).getId();
        }

        compound.putIntArray("Colors", colors);
        compound.putByte("Type", (byte) world.rand.nextInt());

        return compound;
    }
}
