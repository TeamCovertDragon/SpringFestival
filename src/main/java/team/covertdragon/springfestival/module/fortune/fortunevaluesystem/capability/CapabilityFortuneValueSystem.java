/*
 * Copyright (c) 2018 CovertDragon Team.
 * Copyright (c) 2018 Contributors of SpringFestival.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package team.covertdragon.springfestival.module.fortune.fortunevaluesystem.capability;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import team.covertdragon.springfestival.module.fortune.machines.AbstractTileFVMachine;
import team.covertdragon.springfestival.module.fortune.utils.TEDefinition;

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
            tag.setInteger("id", instance.getCurrentlyNextMachineId());
            tag.setTag("definitions", writeMachinesToNBT(instance));
            return tag;
        }

        @Override
        public void readNBT(Capability<IFortuneValueSystem> capability, IFortuneValueSystem instance, EnumFacing side, NBTBase nbt) {
            instance.setFortuneValue(((NBTTagCompound) nbt).getInteger("fv"));
            instance.setBufPoint(((NBTTagCompound) nbt).getInteger("incp"));
            instance.setNextMachineId(((NBTTagCompound) nbt).getInteger("id"));
            instance.setDefinitions(readMachinesFromNBT((NBTTagCompound) nbt));
        }

        private NBTTagList writeMachinesToNBT(IFortuneValueSystem instance) {
            NBTTagList tagList = new NBTTagList();

            for (TEDefinition definition : instance.getFVMachines()) {
                if (!definition.shouldClean()) {
                    tagList.appendTag(definition.serializeNBT());
                }
            }
            return tagList;
        }

        private List<TEDefinition> readMachinesFromNBT(NBTTagCompound nbt) {
            List<TEDefinition> ret = new LinkedList<>();
            NBTTagList tagList = (NBTTagList) nbt.getTag("definitions");
            if (tagList != null) {
                for (NBTBase base : tagList) {
                    ret.add(TEDefinition.fromNBT((NBTTagCompound) base));
                }
            }
            return ret;
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
        private List<TEDefinition> definitions = new LinkedList<>();
        private int nextMachineId = 0;

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
        public List<TEDefinition> getFVMachines() {
            return definitions;
        }

        @Override
        public void setDefinitions(List<TEDefinition> definitions) {
            this.definitions = definitions;
        }

        @Override
        public void registerFVMachine(AbstractTileFVMachine machine) {
            machine.setId(nextMachineId++);
            definitions.add(new TEDefinition(machine));
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
            for (int i = 0; i < definitions.size(); i++) {
                TEDefinition def = definitions.get(i);
                if (def.available()) {
                    AbstractTileFVMachine te = (AbstractTileFVMachine) def.getTE();
                    if (te.getId() == id) {
                        definitions.remove(i);
                        i--;
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
