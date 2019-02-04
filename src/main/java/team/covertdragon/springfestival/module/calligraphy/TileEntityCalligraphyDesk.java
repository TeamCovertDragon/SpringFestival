/*
 * Copyright (c) 2018 CovertDragon Team.
 * Copyright (c) 2018 Contributors of SpringFestival.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package team.covertdragon.springfestival.module.calligraphy;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.items.ItemStackHandler;
import team.covertdragon.springfestival.internal.IGuiAccessible;

public class TileEntityCalligraphyDesk extends TileEntity implements IGuiAccessible {

    // And no, this block does not require tick at any time

    // Where is my C/C++ style macro
    private static final int SLOT_INK = 0, SLOT_PEN = 1, SLOT_PAPER = 2;

    private final ItemStackHandler inv = new ItemStackHandler(3);

    /**
     * The default 256 by 256 pixels canvas, mimicking the red paper for writing words.
     * <p>
     * A boolean value of true indicates a black (RGB [0, 0, 0]) pixel; whereas a boolean
     * value of false indicates a white (RGB [255, 255, 255]) pixel. Because it's hand-writing
     * with ink and pen, we only need to consider about 2 colors.
     * </p>
     */
    private boolean canvas[][] = new boolean[256][256];

    @Override
    public void readFromNBT(NBTTagCompound tag) {
        super.readFromNBT(tag);
        inv.deserializeNBT(tag.getCompoundTag("inventory"));
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound tag) {
        tag.setTag("inventory", inv.serializeNBT());
        return super.writeToNBT(tag);
    }

    @Override
    public Object getServerGuiObject(World world, EntityPlayer player) {
        return new ContainerCalligraphy();
    }

    @Override
    public Object getClientGuiObject(World world, EntityPlayer player) {
        return new GuiCalligraphy();
    }
}
