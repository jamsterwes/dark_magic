package wesley.magic.registries;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import wesley.magic.utils.DarkMagicItem;
import wesley.magic.utils.DarkMagicItem.Lore;

public class DarkMagicItems {

    public static final Lore SUPERPOSITION_LORE = new Lore()
        .add(Text.translatable("item.dark_magic.superposition_book.lore1").formatted(Formatting.AQUA).formatted(Formatting.ITALIC))
        .add(Text.translatable("item.dark_magic.superposition_book.lore2").formatted(Formatting.AQUA).formatted(Formatting.ITALIC));

    public static final DarkMagicItem SUPERPOSITION_BOOK = Registry.register(
        Registries.ITEM, new Identifier("dark_magic", "superposition_book"),
        new DarkMagicItem(SUPERPOSITION_LORE, new FabricItemSettings())
    );

    public static final Lore PARACAUSAL_CORE_LORE = new Lore()
        .add(Text.translatable("item.dark_magic.paracausal_core.lore1").formatted(Formatting.AQUA).formatted(Formatting.ITALIC))
        .add(Text.translatable("item.dark_magic.paracausal_core.lore2").formatted(Formatting.AQUA).formatted(Formatting.ITALIC));

    public static final DarkMagicItem PARACAUSAL_CORE = Registry.register(
        Registries.ITEM, new Identifier("dark_magic", "paracausal_core"),
        new DarkMagicItem(PARACAUSAL_CORE_LORE, new FabricItemSettings())
    );

    public static final Lore CRYSTAL_OF_BALOR_LORE = new Lore()
        .add(Text.translatable("item.dark_magic.crystal_of_balor.lore1").formatted(Formatting.AQUA).formatted(Formatting.ITALIC))
        .add(Text.translatable("item.dark_magic.crystal_of_balor.lore2").formatted(Formatting.AQUA).formatted(Formatting.ITALIC))
        .add(Text.translatable("item.dark_magic.crystal_of_balor.lore3").formatted(Formatting.GREEN));

    public static final DarkMagicItem CRYSTAL_OF_BALOR = Registry.register(
        Registries.ITEM, new Identifier("dark_magic", "crystal_of_balor"),
        new DarkMagicItem(CRYSTAL_OF_BALOR_LORE, new FabricItemSettings())
    );

	// Items Tab
	public static final ItemGroup ITEMS_GROUP = FabricItemGroup.builder(
		new Identifier("dark_magic", "items"))
		.icon(() -> new ItemStack(SUPERPOSITION_BOOK))
		.build();

    public static void register() {
        // Register scepters item group
		ItemGroupEvents.modifyEntriesEvent(ITEMS_GROUP).register(content -> {
            content.add(SUPERPOSITION_BOOK);
            content.add(PARACAUSAL_CORE);
            content.add(CRYSTAL_OF_BALOR);
        });
    }
}
