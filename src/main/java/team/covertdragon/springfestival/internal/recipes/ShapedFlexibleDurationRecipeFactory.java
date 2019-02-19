package team.covertdragon.springfestival.internal.recipes;

// TODO (3TUSK/Yesterday17/SeraphJack): Write a proper IRecipeSerializer
/*
public class ShapedFlexibleDurationRecipeFactory implements IRecipeFactory{
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
            this.output.setItemDamage(this.damage == 0 ? SpringFestivalUtil.getDamage(this.output.getMaxDamage()) : this.damage);
            return this.output;
        }
    }
}*/
