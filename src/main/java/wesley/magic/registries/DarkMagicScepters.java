package wesley.magic.registries;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import wesley.magic.scepters.ScepterCrafting;
import wesley.magic.scepters.ScepterItem;
import wesley.magic.scepters.ScepterMaterials;
import wesley.magic.scepters.special.EmeraldScepterItem;
import wesley.magic.utils.DarkMagicItem.Lore;

public class DarkMagicScepters {

    public static final Lore DIAMOND_SCEPTER_LORE = new Lore()
        .add(Text.translatable("item.dark_magic.diamond_scepter.lore1").formatted(Formatting.AQUA).formatted(Formatting.ITALIC));

        public static final Lore EMERALD_SCEPTER_LORE = new Lore()
            .add(Text.translatable("item.dark_magic.emerald_scepter.lore1").formatted(Formatting.GREEN).formatted(Formatting.ITALIC));

    public static final ScepterItem DIAMOND_SCEPTER = Registry.register(
        Registries.ITEM, new Identifier("dark_magic", "diamond_scepter"),
        new ScepterItem(ScepterMaterials.DIAMOND, DIAMOND_SCEPTER_LORE, new FabricItemSettings().maxCount(1))
    );

    public static final EmeraldScepterItem EMERALD_SCEPTER = Registry.register(
        Registries.ITEM, new Identifier("dark_magic", "emerald_scepter"),
        new EmeraldScepterItem(ScepterMaterials.EMERALD, EMERALD_SCEPTER_LORE, new FabricItemSettings().maxCount(1))
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
            content.add(EMERALD_SCEPTER);
        });

        // Register cursed book recipe
        ScepterCrafting.addRecipe(ScepterMaterials.DIAMOND, Items.BOOK, DarkMagicItems.SUPERPOSITION_BOOK);
    }
}
