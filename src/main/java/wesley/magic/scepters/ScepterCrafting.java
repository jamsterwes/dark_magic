package wesley.magic.scepters;

import java.util.HashMap;
import net.minecraft.item.Item;

public class ScepterCrafting {

    private static HashMap<ScepterMaterials, HashMap<Item, Item>> _recipes = new HashMap<>();

    // TODO: Item -> ItemStack and support (>1) in/out items
    public static void addRecipe(ScepterMaterials material, Item from, Item to) {
        // If this material's crafting "page" doesn't exist yet, create it
        if (!_recipes.containsKey(material)) _recipes.put(material, new HashMap<>());

        // Add this recipe to the material's crafting "page"
        _recipes.get(material).put(from, to);
    }

    public static Item tryCraft(ScepterMaterials material, Item from) {
        if (!_recipes.containsKey(material)) return null;
        if (!_recipes.get(material).containsKey(from)) return null;
        return _recipes.get(material).get(from);
    }
}
