/*
 * Copyright (c) 2018 CovertDragon Team.
 * Copyright (c) 2018 Contributors of SpringFestival.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package team.covertdragon.springfestival.module.decoration;

import net.minecraft.entity.Entity;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.util.EnumHelper;
import team.covertdragon.springfestival.SpringFestivalConstants;

public class ItemRedClothes extends ItemArmor{
    public static final ItemArmor.ArmorMaterial RED_CLOTHES = EnumHelper.addArmorMaterial("RED_CLOTH", SpringFestivalConstants.MOD_ID + ":" + "red_cloth", 10, new int[] {1,1,1,1}, 10, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, 0.0F);

    public ItemRedClothes(EntityEquipmentSlot equipmentSlotIn) {
        super(RED_CLOTHES, 0, equipmentSlotIn);
        this.setCreativeTab(SpringFestivalConstants.CREATIVE_TAB);
    }

    public String getArmorTexture(ItemStack stack, Entity entity, EntityEquipmentSlot slot, String type) {
        return "springfestival:textures/armor/red_armor_1.png";
    }

    public static class RedHat extends ItemRedClothes{
        public RedHat() {
            super(EntityEquipmentSlot.HEAD);
            this.setUnlocalizedName(SpringFestivalConstants.MOD_ID + ".red_hat");
            this.setRegistryName(SpringFestivalConstants.MOD_ID, "red_hat");
        }
    }

    public static class RedGown extends ItemRedClothes{
        public RedGown() {
            super(EntityEquipmentSlot.CHEST);
            this.setUnlocalizedName(SpringFestivalConstants.MOD_ID + ".red_gown");
            this.setRegistryName(SpringFestivalConstants.MOD_ID, "red_gown");
        }
    }

    public static class RedTrousers extends ItemRedClothes{
        public RedTrousers() {
            super(EntityEquipmentSlot.LEGS);
            this.setUnlocalizedName(SpringFestivalConstants.MOD_ID + ".red_trousers");
            this.setRegistryName(SpringFestivalConstants.MOD_ID, "red_trousers");
        }

        public String getArmorTexture(ItemStack stack, Entity entity, EntityEquipmentSlot slot, String type) {
            return "springfestival:textures/armor/red_armor_2.png";
        }
    }

    public static class RedShoes extends ItemRedClothes{
        public RedShoes() {
            super(EntityEquipmentSlot.FEET);
            this.setUnlocalizedName(SpringFestivalConstants.MOD_ID + ".red_shoes");
            this.setRegistryName(SpringFestivalConstants.MOD_ID, "red_shoes");
        }
    }
}
