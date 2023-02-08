package wesley.magic.tomes;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;
import wesley.magic.ExampleMod;
import wesley.magic.tomes.special.DevourTomeItem;
import wesley.magic.tomes.special.EffectTomeItem;
import wesley.magic.tomes.special.RiftTomeItem;

public class DarkMagicTomes {

	public static final TomeProperties DECAY_PROPERTIES = new TomeProperties(
		10,
		SoundEvents.ENTITY_SPIDER_HURT,
		"decay_tome",
		20 * 4
	);

	public static final TomeProperties DEVOUR_PROPERTIES = new TomeProperties(
		20,
		SoundEvents.ENTITY_SPIDER_HURT,
		"devour_tome",
		20 * 3
	);

	// SPIDER (Tier I)
	public static final EffectTomeItem TOME_SPIDER_EYE = Registry.register(
		Registries.ITEM, new Identifier("dark_magic", "tome_spider_eye"),
		new EffectTomeItem(
			DECAY_PROPERTIES,
			ExampleMod.DECAY, 20 * 5, 0,
			new FabricItemSettings().maxCount(1))
	);
	
	// SPIDER (Tier II)
	public static final DevourTomeItem TOME_DEVOUR = Registry.register(
		Registries.ITEM, new Identifier("dark_magic", "tome_devour"),
		new DevourTomeItem(ExampleMod.DEVOUR)
	);

	///////////////////////////////////////////////////////////////////////

	public static final TomeProperties RIFT_PROPERTIES = new TomeProperties(
		5,
		SoundEvents.ENTITY_ENDERMAN_TELEPORT,
		"rift_tome",
		20 * 5
	);

	// ENDER_PEARL (Tier II)
	public static final RiftTomeItem TOME_RIFTS = Registry.register(
		Registries.ITEM, new Identifier("dark_magic", "tome_rifts"),
		new RiftTomeItem(RIFT_PROPERTIES, new FabricItemSettings().maxCount(1))
	);

	// Dark Magic Tab
	private static final ItemGroup TOMES_GROUP = FabricItemGroup.builder(
		new Identifier("dark_magic", "tomes"))
		.icon(() -> new ItemStack(TOME_DEVOUR))
		.build();

    public static void register() {
        // Set up tomes item group
		ItemGroupEvents.modifyEntriesEvent(TOMES_GROUP).register(content -> {
			content.add(TOME_SPIDER_EYE);
			content.add(TOME_DEVOUR);
			content.add(TOME_RIFTS);
		});
    }
}
