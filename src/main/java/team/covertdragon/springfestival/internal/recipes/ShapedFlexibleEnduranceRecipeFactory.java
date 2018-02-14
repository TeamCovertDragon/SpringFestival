package team.covertdragon.springfestival.internal.recipes;

import com.google.gson.JsonObject;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.crafting.CraftingHelper.ShapedPrimer;
import net.minecraftforge.common.crafting.IRecipeFactory;
import net.minecraftforge.common.crafting.JsonContext;
import net.minecraftforge.oredict.ShapedOreRecipe;
import team.covertdragon.springfestival.SpringFestivalConstants;

public class ShapedFlexibleEnduranceRecipeFactory implements IRecipeFactory{
    @Override
    public IRecipe parse(JsonContext context, JsonObject json) {
        ShapedOreRecipe recipe = ShapedOreRecipe.factory(context, json);

        ShapedPrimer primer = new ShapedPrimer();
        primer.width = recipe.getRecipeWidth();
        primer.height = recipe.getRecipeHeight();
        primer.mirrored = JsonUtils.getBoolean(json, "mirrored", true);
        primer.input = recipe.getIngredients();

        int damage = JsonUtils.getInt(json, "damage", 0);

        return new ShapedFlexibleEnduranceShapedRecipe(new ResourceLocation(SpringFestivalConstants.MOD_ID , "shaped_flexible_duration"), recipe.getRecipeOutput(), primer, damage);
    }

    public static class ShapedFlexibleEnduranceShapedRecipe extends ShapedOreRecipe {
        protected int damage;

        public ShapedFlexibleEnduranceShapedRecipe(ResourceLocation group, ItemStack result, ShapedPrimer primer, int damage) {
            super(group, result, primer);
            this.damage = damage;
        }

        @Override
        public ItemStack getRecipeOutput(){
            this.output.setItemDamage(this.damage == 0 ? this.getDamage(this.output.getMaxDamage()) : this.damage);
            return this.output;
        }

        public static int getDamage(int maxDamage){
            // TODO: Return a number which indicates how long Spring Festival has passed
            return -10;
        }
    }
}
