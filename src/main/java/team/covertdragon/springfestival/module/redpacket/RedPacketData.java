/*
 * Copyright (c) 2018 CovertDragon Team.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package team.covertdragon.springfestival.module.redpacket;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTUtil;
import net.minecraftforge.common.util.INBTSerializable;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

// TODO Should we have support for this one?
public class RedPacketData implements INBTSerializable<NBTTagCompound> {

    public enum Type {
        RECEIVED, POSTED
    }

    private long timestamp;

    private UUID owner;

    private Type type;

    List<ItemStack> contents; // TODO Non-null type parameter, because you don't want a red packet containing null

    @Override
    public NBTTagCompound serializeNBT() {
        NBTTagCompound tag = new NBTTagCompound();
        tag.setLong("timestamp", this.timestamp);
        tag.setTag("uuid", NBTUtil.createUUIDTag(this.owner));
        NBTTagList list = new NBTTagList();
        for (ItemStack stack : contents) {
            list.appendTag(stack.serializeNBT());
        }
        tag.setTag("contents", list);
        return tag;
    }

    @Override
    public void deserializeNBT(NBTTagCompound nbt) {
        this.timestamp = nbt.getLong("timestamp");
        this.owner = NBTUtil.getUUIDFromTag(nbt.getCompoundTag("uuid"));
        NBTTagList list = nbt.getTagList("contents", nbt.getId());
        ArrayList<ItemStack> contents = new ArrayList<>();
        for (int i = 0; i < contents.size(); i++) {
            contents.add(new ItemStack(list.getCompoundTagAt(i)));
        }
        this.contents = contents;
    }
}
