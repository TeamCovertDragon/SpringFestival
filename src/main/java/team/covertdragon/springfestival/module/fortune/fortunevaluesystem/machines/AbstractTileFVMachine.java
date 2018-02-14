package team.covertdragon.springfestival.module.fortune.fortunevaluesystem.machines;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

import javax.annotation.Nonnull;
import javax.annotation.OverridingMethodsMustInvokeSuper;

public abstract class AbstractTileFVMachine extends TileEntity implements IFVMachine {
    private int id;

    @Override
    public abstract int getRequiredFV();

    @Override
    public abstract void onFVProvided();

    @Override
    public int getId() {
        return id;
    }

    @Override
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
}
