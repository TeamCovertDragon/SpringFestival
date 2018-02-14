package team.covertdragon.springfestival.module.fortune.fortunevaluesystem.capability;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import team.covertdragon.springfestival.module.fortune.fortunevaluesystem.machines.IFVMachine;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Callable;

public class CapabilityFortuneValueSystem {
    public static class Storage implements Capability.IStorage<IFortuneValueSystem> {

        @Nullable
        @Override
        public NBTBase writeNBT(Capability<IFortuneValueSystem> capability, IFortuneValueSystem instance, EnumFacing side) {
            NBTTagCompound tag = new NBTTagCompound();
            tag.setInteger("fv", instance.getFortuneValue());
            tag.setInteger("incp", instance.getIncreasingPoint());
            tag.setTag("machines", writeMachinesToNBT(instance));
            return tag;
        }

        @Override
        public void readNBT(Capability<IFortuneValueSystem> capability, IFortuneValueSystem instance, EnumFacing side, NBTBase nbt) {
            instance.setFortuneValue(((NBTTagCompound) nbt).getInteger("fv"));
            instance.setIncreasingPoint(((NBTTagCompound) nbt).getInteger("incp"));
        }

        private NBTTagList writeMachinesToNBT(IFortuneValueSystem instance) {
            NBTTagList tagList = new NBTTagList();

            for (IFVMachine machine : instance.getFVMachines()) {
                NBTTagCompound compound = new NBTTagCompound();
                //TODO serialize machines
                tagList.appendTag(compound);
            }
            return tagList;
        }

        private List<IFVMachine> readMachinesFromNBT(NBTTagList tagList) {
            List<IFVMachine> list = new LinkedList<>();
            for (NBTBase base : tagList) {
                NBTTagCompound tag = (NBTTagCompound) base;
                //TODO deserialize machines
            }
            return list;
        }
    }

    public static class Factory implements Callable<IFortuneValueSystem> {

        @Override
        public IFortuneValueSystem call() throws Exception {
            return new Implementation();
        }
    }

    public static class Implementation implements IFortuneValueSystem {
        private int fortuneValue = 0;
        private int increasingPoint = 1;//TODO increase more quickly in springfestival?
        private List<IFVMachine> machines = new LinkedList<>();
        private int nextMachineId;

        @Override
        public int getFortuneValue() {
            return fortuneValue;
        }

        @Override
        public void setFortuneValue(int quality) {
            this.fortuneValue = quality;
        }

        @Override
        public void addFortune(int quality) {
            this.fortuneValue += quality;
        }

        @Override
        public boolean shrinkFortune(int quality) {
            if (this.fortuneValue >= quality) {
                this.fortuneValue -= quality;
                return true;
            }
            return false;
        }

        @Override
        public int getIncreasingPoint() {
            return increasingPoint;
        }

        @Override
        public void setIncreasingPoint(int quality) {
            this.increasingPoint = quality;
        }

        @Override
        public void addIncreasingPoint(int quality) {
            this.increasingPoint += quality;
        }

        @Override
        public boolean shrinkIncreasingPoint(int quality) {
            if (this.increasingPoint >= quality) {
                this.increasingPoint -= quality;
                return true;
            }
            return false;
        }

        @Override
        public List<IFVMachine> getFVMachines() {
            return machines;
        }

        @Override
        public void setMachines(List<IFVMachine> machines) {
            this.machines = machines;
        }

        @Override
        public void registerFVMachine(IFVMachine machine) {
            machine.setId(nextMachineId++);
            machines.add(machine);
        }

        @Override
        public void deleteFVMachine(IFVMachine machine) {
            for (int i = 0; i < machines.size(); i++) {
                if (machines.get(i).getId() == machine.getId()) {
                    machines.remove(i);
                    return;
                }
            }
        }

        @Override
        public int getCurrentlyNextMachineId() {
            return this.nextMachineId;
        }

        @Override
        public void setNextMachineId(int id) {
            this.nextMachineId = id;
        }
    }

    public static class PlayerProvider implements ICapabilitySerializable<NBTTagCompound> { //TODO

        @Override
        public boolean hasCapability(@Nonnull Capability<?> capability, @Nullable EnumFacing facing) {
            return false;
        }

        @Nullable
        @Override
        public <T> T getCapability(@Nonnull Capability<T> capability, @Nullable EnumFacing facing) {
            return null;
        }

        @Override
        public NBTTagCompound serializeNBT() {
            return null;
        }

        @Override
        public void deserializeNBT(NBTTagCompound nbt) {

        }
    }
}
