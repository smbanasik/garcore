package github.spencerb.garcore.chestcraft;

import net.minecraft.world.item.ItemStack;

public record CultChestRecipe(
        RecipeKey input,
        SlotKey output, int outputSlot) {
	
    public static final int DEFAULT_OUTPUT_SLOT = 13;

    public CultChestRecipe(
            RecipeKey input,
            SlotKey output) {
        this(input, output, DEFAULT_OUTPUT_SLOT);
    }

    public ItemStack assemble() {
    	return output.toItemStack();
    }
}
