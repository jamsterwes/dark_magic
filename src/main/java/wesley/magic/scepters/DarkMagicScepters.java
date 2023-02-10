package wesley.magic.scepters;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class DarkMagicScepters {
    public static final Item DIAMOND_SCEPTER = Registry.register(
        Registries.ITEM, new Identifier("dark_magic", "diamond_scepter"),
        new Item(new FabricItemSettings().maxCount(1))
    );

	// Scepter Tab
	public static final ItemGroup SCEPTERS_GROUP = FabricItemGroup.builder(
		new Identifier("dark_magic", "scepters"))
		.icon(() -> new ItemStack(DIAMOND_SCEPTER))
		.build();

    public static void register() {
		ItemGroupEvents.modifyEntriesEvent(SCEPTERS_GROUP).register(content -> {
            content.add(DIAMOND_SCEPTER);
        });
    }
}
