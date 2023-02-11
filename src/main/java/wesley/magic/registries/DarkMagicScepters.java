package wesley.magic.registries;

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
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import wesley.magic.scepters.ScepterItem;
import wesley.magic.utils.DarkMagicItem.Lore;

public class DarkMagicScepters {

    public static final Lore DIAMOND_SCEPTER_LORE = new Lore()
        .add(Text.translatable("item.dark_magic.diamond_scepter.lore1").formatted(Formatting.AQUA).formatted(Formatting.ITALIC));

    public static final ScepterItem DIAMOND_SCEPTER = Registry.register(
        Registries.ITEM, new Identifier("dark_magic", "diamond_scepter"),
        new ScepterItem(DIAMOND_SCEPTER_LORE, new FabricItemSettings().maxCount(1))
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
