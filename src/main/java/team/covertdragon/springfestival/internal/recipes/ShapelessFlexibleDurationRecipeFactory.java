package team.covertdragon.springfestival.internal.recipes;

// TODO (3TUSK/Yesterday17/SeraphJack): Write a proper IRecipeSerializer
/*
public class ShapelessFlexibleDurationRecipeFactory implements IRecipeFactory{
    @Override
    public IRecipe parse(JsonContext context, JsonObject json) {
        ShapelessOreRecipe recipe = ShapelessOreRecipe.factory(context, json);

        CraftingHelper.ShapedPrimer primer = new CraftingHelper.ShapedPrimer();
        primer.mirrored = JsonUtils.getBoolean(json, "mirrored", true);
        primer.input = recipe.getIngredients();

        int damage = JsonUtils.getInt(json, "damage", 0);

        return new ShapelessFlexibleEnduranceShapedRecipe(new ResourceLocation(SpringFestivalConstants.MOD_ID , "shapeless_flexible_duration"), recipe.getRecipeOutput(), primer, damage);
    }

    public static class ShapelessFlexibleEnduranceShapedRecipe extends ShapelessOreRecipe {
        protected int damage;

        public ShapelessFlexibleEnduranceShapedRecipe(ResourceLocation group, ItemStack result, CraftingHelper.ShapedPrimer primer, int damage) {
            super(group, result, primer);
            this.damage = damage;
        }

        @Override
        public ItemStack getRecipeOutput(){
            this.output.setItemDamage(this.damage == 0 ? SpringFestivalUtil.getDamage(this.output.getMaxDamage()) : this.damage);
            return this.output;
        }
    }
}*/