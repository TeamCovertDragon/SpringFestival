/*
 * Copyright (c) 2018 CovertDragon Team.
 * Copyright (c) 2018 Contributors of SpringFestival.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package team.covertdragon.springfestival.module.redpacket;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.INBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTUtil;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

/**
 * A POJO that represents a red packet object.
 * <p>
 *     It may be serialized as a NBTTagCompound.
 * </p>
 */
public class RedPacketData implements INBTSerializable<NBTTagCompound> {

    // A crude factory method
    static RedPacketData fromItemStack(@Nonnull final ItemStack stack) {
        final RedPacketData data = new RedPacketData();
        NBTTagCompound rawData = stack.getTag();
        data.deserializeNBT(rawData);
        if (data.contents.isEmpty()) {
            IItemHandler itemHandler = stack.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null).orElseThrow(NullPointerException::new);
            if (itemHandler != null) {
                final List<ItemStack> contents = new ArrayList<>();
                for (int i = 0; i < itemHandler.getSlots(); i++) {
                    ItemStack s = itemHandler.getStackInSlot(i);
                    if (!stack.isEmpty()) {
                        contents.add(s);
                    }
                }
                data.contents = contents;
            }
        }
        return data;
    }

    private long timestamp;
    private UUID owner;
    private UUID receiver;
    private boolean hasPasscode;
    private String message;
    private List<ItemStack> contents = Collections.emptyList();

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    @Nonnull
    public UUID getOwner() {
        return owner;
    }

    public void setOwner(UUID owner) {
        this.owner = owner;
    }

    @Nullable
    public UUID getReceiver() {
        return receiver;
    }

    public void setReceiver(UUID receiver) {
        this.receiver = receiver;
    }

    public boolean isHasPasscode() {
        return hasPasscode;
    }

    public void setHasPasscode(boolean hasPasscode) {
        this.hasPasscode = hasPasscode;
    }

    @Nonnull
    public String getMessage() {
        return message;
    }

    public void setMessage(@Nonnull String message) {
        this.message = message;
    }

    @Nonnull
    public List<ItemStack> getContents() {
        return contents;
    }

    public void setContents(@Nonnull List<ItemStack> contents) {
        this.contents = contents;
    }

    public boolean isEmpty() {
        return this.contents.isEmpty();
    }

    /**
     * @return A new RedPacketData instance that contains a portion of contents of this RedPacketData instance.
     */
    public RedPacketData randomSplit() {
        final RedPacketData newData = new RedPacketData();
        newData.timestamp = System.currentTimeMillis();
        newData.owner = this.owner;
        newData.hasPasscode = this.hasPasscode;
        newData.message = this.message;
        newData.contents = new ArrayList<>();
        // TODO We need a splitter...
        return newData;
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof RedPacketData && ((RedPacketData) o).message.equals(this.message) && ((RedPacketData) o).owner.equals(this.owner);
    }

    @Override
    public int hashCode() {
        return this.owner.hashCode() * 31 + this.message.hashCode();
    }

    @Override
    public NBTTagCompound serializeNBT() {
        NBTTagCompound tag = new NBTTagCompound();
        tag.putLong("timestamp", this.timestamp);
        tag.put("owner", NBTUtil.writeUniqueId(this.owner));
        tag.put("receiver", NBTUtil.writeUniqueId(this.receiver));
        NBTTagList list = new NBTTagList();
        for (ItemStack stack : contents) {
            list.add(stack.serializeNBT());
        }
        tag.put("contents", list);
        tag.putString("message", this.message);
        tag.putBoolean("passcode", this.hasPasscode);
        return tag;
    }

    @Override
    public void deserializeNBT(NBTTagCompound nbt) {
        if (nbt.contains("timestamp", Constants.NBT.TAG_LONG)) {
            this.timestamp = nbt.getLong("timestamp");
        } else {
            this.timestamp = System.currentTimeMillis();
        }
        this.owner = NBTUtil.readUniqueId(nbt.getCompound("owner"));
        if (nbt.contains("receiver", Constants.NBT.TAG_COMPOUND)) {
            this.receiver = NBTUtil.readUniqueId(nbt.getCompound("receiver"));
        }
        if (nbt.contains("contents", Constants.NBT.TAG_LIST)) {
            NBTTagList list = nbt.getList("contents", nbt.getId());
            ArrayList<ItemStack> contents = new ArrayList<>();
            for (INBTBase tag : list) {
                contents.add(ItemStack.read((NBTTagCompound)tag));
            }
            this.contents = contents;
        }
        this.message = nbt.getString("message");
        this.hasPasscode = nbt.getBoolean("passcode");
    }
}
