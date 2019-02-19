/*
 * Copyright (c) 2018 CovertDragon Team.
 * Copyright (c) 2018 Contributors of SpringFestival.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package team.covertdragon.springfestival.module.decoration.fudoor;

import io.netty.buffer.ByteBuf;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import team.covertdragon.springfestival.internal.network.AbstractSpringFestivalPacket;

public class ServerPacketFuDoorCreation implements AbstractSpringFestivalPacket {

    private TileFuDoor te;

    public ServerPacketFuDoorCreation() {}

    public ServerPacketFuDoorCreation(TileFuDoor te) {
        this.te = te;
    }

    @Override
    public void writeDataTo(ByteBuf buffer) {
        BlockPos pos = te.getPos();
        buffer.writeInt(pos.getX());
        buffer.writeInt(pos.getY());
        buffer.writeInt(pos.getZ());
        PacketBuffer wrapped = new PacketBuffer(buffer);
        wrapped.writeCompoundTag(NBTUtil.writeBlockState(te.getOriginalBlockStateUpper()));
        wrapped.writeCompoundTag(NBTUtil.writeBlockState(te.getOriginalBlockStateLower()));
    }

    @Override
    public void readDataFrom(ByteBuf buffer, EntityPlayer player) {
        int x = buffer.readInt(), y = buffer.readInt(), z = buffer.readInt();
        BlockPos pos = new BlockPos(x, y, z);
        TileEntity te = player.getEntityWorld().getTileEntity(pos);
        if (te instanceof TileFuDoor) {
            PacketBuffer wrapped = new PacketBuffer(buffer);
            IBlockState upper = NBTUtil.readBlockState(wrapped.readCompoundTag());
            IBlockState lower = NBTUtil.readBlockState(wrapped.readCompoundTag());
            ((TileFuDoor) te).setOriginalBlockStateUpper(upper);
            ((TileFuDoor) te).setOriginalBlockStateLower(lower);
        }
    }
}
