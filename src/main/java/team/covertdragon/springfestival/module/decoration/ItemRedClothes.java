package team.covertdragon.springfestival.module.decoration;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import net.minecraftforge.common.util.EnumHelper;
import team.covertdragon.springfestival.SpringFestivalConstants;

public class ItemRedClothes extends ItemArmor{
    public static final ItemArmor.ArmorMaterial RED_CLOTHES = EnumHelper.addArmorMaterial("RED_CLOTH", SpringFestivalConstants.MOD_ID + ":" + "red_cloth", 10, new int[] {1,1,1,1}, 10, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, 0.0F);

    public ItemRedClothes(EntityEquipmentSlot equipmentSlotIn) {
        super(RED_CLOTHES, 0, equipmentSlotIn);

        // TODO: Move this(↓) to the mod's CreativeTab
        this.setCreativeTab(CreativeTabs.INVENTORY);
    }

    public static class RedHat extends ItemRedClothes{
        public RedHat() {
            super(EntityEquipmentSlot.HEAD);
            this.setUnlocalizedName("red_hat");
        }
    }

    public static class RedGown extends ItemRedClothes{
        public RedGown() {
            super(EntityEquipmentSlot.CHEST);
            this.setUnlocalizedName("red_gown");
        }
    }

    public static class RedTrousers extends ItemRedClothes{
        public RedTrousers() {
            super(EntityEquipmentSlot.LEGS);
            this.setUnlocalizedName("red_trousers");
        }
    }

    public static class RedShoes extends ItemRedClothes{
        public RedShoes() {
            super(EntityEquipmentSlot.FEET);
            this.setUnlocalizedName("red_shoes");
        }
    }
}
