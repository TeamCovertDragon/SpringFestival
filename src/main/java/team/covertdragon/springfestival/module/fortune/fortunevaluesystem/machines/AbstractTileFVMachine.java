package team.covertdragon.springfestival.module.fortune.fortunevaluesystem.machines;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.INBTSerializable;

import javax.annotation.Nonnull;
import javax.annotation.OverridingMethodsMustInvokeSuper;

public abstract class AbstractTileFVMachine extends TileEntity implements INBTSerializable<NBTTagCompound> {
    private int id;

    public abstract int getRequiredFV();

    public abstract void onFVProvided();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    @OverridingMethodsMustInvokeSuper
    @Nonnull
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        compound.setInteger("fvid", id);
        return compound;
    }

    @Override
    @OverridingMethodsMustInvokeSuper
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        this.id = compound.getInteger("fvid");
    }

    @Override
    public NBTTagCompound serializeNBT() {
        NBTTagCompound tag = super.serializeNBT();
        tag.setInteger("fvid", getId());
        return tag;
    }

    @Override
    public void deserializeNBT(NBTTagCompound nbt) {
        super.deserializeNBT(nbt);
        if (nbt.hasKey("fvid")) {
            this.setId(nbt.getInteger("fvid"));
        }
    }
}
