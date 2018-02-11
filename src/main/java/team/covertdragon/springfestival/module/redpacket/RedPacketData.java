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
    private boolean hasPasscode;
    private String name;
    private List<ItemStack> contents;

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public UUID getOwner() {
        return owner;
    }

    public void setOwner(UUID owner) {
        this.owner = owner;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public boolean isHasPasscode() {
        return hasPasscode;
    }

    public void setHasPasscode(boolean hasPasscode) {
        this.hasPasscode = hasPasscode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ItemStack> getContents() {
        return contents;
    }

    public void setContents(List<ItemStack> contents) {
        this.contents = contents;
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof RedPacketData && ((RedPacketData) o).name.equals(this.name) && ((RedPacketData) o).owner.equals(this.owner);
    }

    @Override
    public int hashCode() {
        return this.owner.hashCode() * 31 + this.name.hashCode();
    }

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
        tag.setString("name", this.name);
        tag.setBoolean("passcode", this.hasPasscode);
        tag.setInteger("type", this.type.ordinal());
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
        this.name = nbt.getString("name");
        this.hasPasscode = nbt.getBoolean("passcode");
        this.type = Type.values()[nbt.getInteger("type")];
    }
}
