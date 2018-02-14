package team.covertdragon.springfestival.module.fortune.fortunevalue.capability;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;

import javax.annotation.Nullable;

public class CapabilityFortuneValue {
    public static class Storage implements Capability.IStorage<IFortuneValue> {

        @Nullable
        @Override
        public NBTBase writeNBT(Capability<IFortuneValue> capability, IFortuneValue instance, EnumFacing side) {
            NBTTagCompound tag = new NBTTagCompound();
            tag.setInteger("fv", instance.getFortuneValue());
            return tag;
        }

        @Override
        public void readNBT(Capability<IFortuneValue> capability, IFortuneValue instance, EnumFacing side, NBTBase nbt) {
            instance.setFortuneValue(((NBTTagCompound) nbt).getInteger("fv"));
        }
    }

    public static class Implementation implements IFortuneValue {
        private int fortuneValue;

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
        public void shrinkFortune(int quality) {
            this.fortuneValue -= quality;
        }
    }
}
