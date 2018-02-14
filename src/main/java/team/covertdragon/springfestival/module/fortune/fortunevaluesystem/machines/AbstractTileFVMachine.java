package team.covertdragon.springfestival.module.fortune.fortunevaluesystem.machines;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.util.INBTSerializable;

import javax.annotation.Nonnull;
import javax.annotation.OverridingMethodsMustInvokeSuper;

public abstract class AbstractTileFVMachine extends TileEntity implements INBTSerializable<NBTTagCompound> {
    private int id;

    protected int requiredFV;
    protected BlockPos position;
    protected World world;

    public abstract int getRequiredFV();

    public abstract void onFVProvided();

    public AbstractTileFVMachine(World world, BlockPos pos){
        this(world, pos, 0);
    }

    public AbstractTileFVMachine(World world, BlockPos pos, int requiredFV){
        this.world = world;
        this.position = pos;
        this.requiredFV = requiredFV;
    }

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
