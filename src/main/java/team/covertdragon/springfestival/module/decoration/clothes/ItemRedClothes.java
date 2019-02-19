/*
 * Copyright (c) 2018 CovertDragon Team.
 * Copyright (c) 2018 Contributors of SpringFestival.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package team.covertdragon.springfestival.module.decoration.clothes;

import net.minecraft.entity.Entity;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.SoundEvent;
import team.covertdragon.springfestival.SpringFestivalConstants;

public class ItemRedClothes extends ItemArmor{

    public static final class RedClothMaterial implements IArmorMaterial {

        public static final RedClothMaterial INSTANCE = new RedClothMaterial();

        @Override
        public int getDurability(EntityEquipmentSlot slot) {
            return 10;
        }

        @Override
        public int getDamageReductionAmount(EntityEquipmentSlot slot) {
            switch (slot) {
                case HEAD: return 1;
                case CHEST: return 3;
                case LEGS: return 4;
                case FEET: return 2;
                default: return 0;
            }
        }

        @Override
        public int getEnchantability() {
            return 10;
        }

        @Override
        public SoundEvent getSoundEvent() {
            return SoundEvents.ITEM_ARMOR_EQUIP_LEATHER;
        }

        @Override
        public Ingredient getRepairMaterial() {
            return Ingredient.EMPTY;
        }

        @Override
        public String getName() {
            return SpringFestivalConstants.MOD_ID + ":" + "red_cloth";
        }

        @Override
        public float getToughness() {
            return 0F;
        }
    }

    ItemRedClothes(EntityEquipmentSlot equipmentSlot, Properties properties) {
        super(RedClothMaterial.INSTANCE, equipmentSlot, properties);
    }

    public String getArmorTexture(ItemStack stack, Entity entity, EntityEquipmentSlot slot, String type) {
        return "springfestival:textures/armor/red_armor_1.png";
    }

    public static class RedHat extends ItemRedClothes{
        public RedHat(Properties properties) {
            super(EntityEquipmentSlot.HEAD, properties);
        }
    }

    public static class RedGown extends ItemRedClothes{
        public RedGown(Properties properties) {
            super(EntityEquipmentSlot.CHEST, properties);
        }
    }

    public static class RedTrousers extends ItemRedClothes{
        public RedTrousers(Properties properties) {
            super(EntityEquipmentSlot.LEGS, properties);
        }

        public String getArmorTexture(ItemStack stack, Entity entity, EntityEquipmentSlot slot, String type) {
            return "springfestival:textures/armor/red_armor_2.png";
        }
    }

    public static class RedShoes extends ItemRedClothes{
        public RedShoes(Properties properties) {
            super(EntityEquipmentSlot.FEET, properties);
        }
    }
}
