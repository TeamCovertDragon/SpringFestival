package team.covertdragon.springfestival.module.fortune.fortunevaluesystem.capability;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import team.covertdragon.springfestival.SpringFestivalConstants;
import team.covertdragon.springfestival.module.fortune.fortunevaluesystem.machines.AbstractTileFVMachine;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.lang.ref.WeakReference;
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
            instance.setBufPoint(((NBTTagCompound) nbt).getInteger("incp"));
            instance.setMachines(readMachinesFromNBT((NBTTagCompound) nbt));
        }

        private NBTTagList writeMachinesToNBT(IFortuneValueSystem instance) {
            NBTTagList tagList = new NBTTagList();

            for (AbstractTileFVMachine machine : instance.getFVMachines()) {
                if (machine != null) {
                    NBTTagCompound tag = machine.serializeNBT();
                    tag.setInteger("world", machine.getWorld().provider.getDimension());
                    tagList.appendTag(tag);
                }
            }
            return tagList;
        }

        private List<AbstractTileFVMachine> readMachinesFromNBT(NBTTagCompound nbt) {
            List<AbstractTileFVMachine> list = new LinkedList<>();
            NBTTagList tagList = (NBTTagList) nbt.getTag("machines");
            for (NBTBase base : tagList) {
                NBTTagCompound tag = (NBTTagCompound) base;
                World world = SpringFestivalConstants.server.getWorld(tag.getInteger("world"));
                BlockPos pos = new BlockPos(tag.getInteger("x"), tag.getInteger("y"), tag.getInteger("z"));
                list.add((AbstractTileFVMachine) world.getTileEntity(pos));
            }
            return list;
        }
    }

    public static class Factory implements Callable<IFortuneValueSystem> {

        @Override
        public IFortuneValueSystem call() {
            return new Implementation();
        }
    }

    public static class Implementation implements IFortuneValueSystem {
        private int fortuneValue = 0;
        private int increasingPoint = 1;//TODO increase more quickly in springfestival?
        private int increasingBuff = 0;
        private List<WeakReference<AbstractTileFVMachine>> machines = new LinkedList<>();
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
            return increasingPoint + increasingBuff;
        }

        @Override
        public void setBufPoint(int quality) {
            this.increasingBuff = quality;
        }

        @Override
        public void addBufPoint(int quality) {
            this.increasingBuff += quality;
        }

        @Override
        public boolean shrinkBuffPoint(int quality) {
            if (this.increasingBuff >= quality) {
                this.increasingBuff -= quality;
                return true;
            }
            return false;
        }

        @Override
        public List<AbstractTileFVMachine> getFVMachines() {
            List<AbstractTileFVMachine> machines = new LinkedList<>();
            for (WeakReference<AbstractTileFVMachine> reference : this.machines) {
                if (reference.get() != null) {
                    machines.add(reference.get());
                }
            }
            return machines;
        }

        @Override
        public void setMachines(List<AbstractTileFVMachine> machines) {
            for (AbstractTileFVMachine machine : machines) {
                this.machines.add(new WeakReference<>(machine));
            }
        }

        @Override
        public void registerFVMachine(AbstractTileFVMachine machine) {
            machine.setId(nextMachineId++);
            machines.add(new WeakReference<>(machine));
        }

        @Override
        public int getCurrentlyNextMachineId() {
            return this.nextMachineId;
        }

        @Override
        public void setNextMachineId(int id) {
            this.nextMachineId = id;
        }

        @Override
        public void deleteMachine(int id) {
            for (int i = 0; i < machines.size(); i++) {
                AbstractTileFVMachine machine = machines.get(i).get();
                if (machine != null) {
                    if (machine.getId() == id) {
                        machines.remove(i);
                        return;
                    }
                }
            }
        }
    }

    public static class PlayerProvider implements ICapabilitySerializable<NBTTagCompound> {
        private IFortuneValueSystem instance = new Implementation();
        private Capability.IStorage<IFortuneValueSystem> storage = new Storage();

        @Override
        public boolean hasCapability(@Nonnull Capability<?> capability, @Nullable EnumFacing facing) {
            return CapabilityLoader.fortuneValue.equals(capability);
        }

        @Nullable
        @Override
        @SuppressWarnings("unchecked")
        public <T> T getCapability(@Nonnull Capability<T> capability, @Nullable EnumFacing facing) {
            if (hasCapability(capability, facing)) {
                return (T) instance;
            }
            return null;
        }

        @Override
        public NBTTagCompound serializeNBT() {
            NBTTagCompound compound = new NBTTagCompound();
            compound.setTag("fv_system", storage.writeNBT(CapabilityLoader.fortuneValue, instance, null));
            return compound;
        }

        @Override
        public void deserializeNBT(NBTTagCompound nbt) {
            storage.readNBT(CapabilityLoader.fortuneValue, instance, null, nbt.getTag("fv_system"));
        }
    }
}
