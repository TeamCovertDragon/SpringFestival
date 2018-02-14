package team.covertdragon.springfestival.module.fortune.fortunevaluesystem.machines;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.INBTSerializable;

import javax.annotation.Nonnull;
import javax.annotation.OverridingMethodsMustInvokeSuper;

public abstract class AbstractTileFVMachine extends TileEntity implements IFVMachine, INBTSerializable<NBTTagCompound> {
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

    @Override
    public NBTTagCompound serializeNBT(){
        NBTTagCompound tag = new NBTTagCompound();
        tag.setInteger("fvid", getId());
    }

    @Override
    public void deserializeNBT(NBTTagCompound nbt){
        if(nbt.hasKey("fvid")){
            this.setId(nbt.getInteger("fvid"));
        }
    }
}
