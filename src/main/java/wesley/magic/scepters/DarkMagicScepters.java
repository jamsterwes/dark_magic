package wesley.magic.scepters;

import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.client.render.entity.FlyingItemEntityRenderer;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class DarkMagicScepters {
    public static final ScepterItem DIAMOND_SCEPTER = Registry.register(
        Registries.ITEM, new Identifier("dark_magic", "diamond_scepter"),
        new ScepterItem(new FabricItemSettings().maxCount(1))
    );

	// Scepter Tab
	public static final ItemGroup SCEPTERS_GROUP = FabricItemGroup.builder(
		new Identifier("dark_magic", "scepters"))
		.icon(() -> new ItemStack(DIAMOND_SCEPTER))
		.build();

    public static void register() {
        // Register scepters item group
		ItemGroupEvents.modifyEntriesEvent(SCEPTERS_GROUP).register(content -> {
            content.add(DIAMOND_SCEPTER);
        });
    }
}
