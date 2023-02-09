package wesley.magic.tomes;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import wesley.magic.ExampleMod;
import wesley.magic.tomes.special.DevourTomeItem;
import wesley.magic.tomes.special.EffectTomeItem;
import wesley.magic.tomes.special.EndlessVoidTomeItem;
import wesley.magic.tomes.special.FlockTomeItem;
import wesley.magic.tomes.special.RiftTomeItem;

public class DarkMagicTomes {

	public static final TomeProperties DECAY_PROPERTIES = new TomeProperties(
		10,
		SoundEvents.ENTITY_SPIDER_HURT,
		"decay_tome",
		20 * 4
	)
	.addLore(Text.translatable("item.dark_magic.tome_spider_eye.tooltip1").formatted(Formatting.DARK_RED, Formatting.ITALIC))
	.addLore(Text.translatable("item.dark_magic.tome_spider_eye.tooltip2").formatted(Formatting.DARK_RED, Formatting.ITALIC));

	public static final TomeProperties DEVOUR_PROPERTIES = new TomeProperties(
		20,
		SoundEvents.ENTITY_SPIDER_HURT,
		"devour_tome",
		20 * 3
	)
	.addLore(Text.translatable("item.dark_magic.tome_devour.tooltip1").formatted(Formatting.DARK_RED, Formatting.ITALIC))
	.addLore(Text.translatable("item.dark_magic.tome_devour.tooltip2").formatted(Formatting.DARK_RED, Formatting.ITALIC));

	public static final TomeProperties BLOODLUST_PROPERTIES = new TomeProperties(
		20,
		SoundEvents.ENTITY_SPIDER_HURT,
		"bloodlust_tome",
		20 * 3
	)
	.addLore(Text.translatable("item.dark_magic.tome_bloodlust.tooltip1").formatted(Formatting.DARK_RED, Formatting.ITALIC))
	.addLore(Text.translatable("item.dark_magic.tome_bloodlust.tooltip2").formatted(Formatting.DARK_RED, Formatting.ITALIC));

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
		new DevourTomeItem(DEVOUR_PROPERTIES, ExampleMod.DEVOUR, false)
	);
	
	// SPIDER (Tier III)
	public static final DevourTomeItem TOME_BLOODLUST = Registry.register(
		Registries.ITEM, new Identifier("dark_magic", "tome_bloodlust"),
		new DevourTomeItem(BLOODLUST_PROPERTIES, ExampleMod.DEVOUR, true)
	);

	///////////////////////////////////////////////////////////////////////

	public static final TomeProperties RIFT_PROPERTIES = new TomeProperties(
		5,
		SoundEvents.ENTITY_ENDERMAN_TELEPORT,
		"rift_tome",
		20 * 5)
		.addLore(Text.translatable("item.dark_magic.tome_rifts.tooltip1").formatted(Formatting.DARK_AQUA, Formatting.ITALIC))
		.addLore(Text.translatable("item.dark_magic.tome_rifts.tooltip2").formatted(Formatting.DARK_AQUA, Formatting.ITALIC));

	// ENDER_PEARL (Tier II)
	public static final RiftTomeItem TOME_RIFTS = Registry.register(
		Registries.ITEM, new Identifier("dark_magic", "tome_rifts"),
		new RiftTomeItem(RIFT_PROPERTIES, new FabricItemSettings().maxCount(1))
	);

	///////////////////////////////////////////////////////////////////////

	public static final TomeProperties FLOCK_PROPERTIES = new TomeProperties(
		5,
		SoundEvents.ENTITY_GENERIC_EAT,
		"flock_tome",
		20 * 5);
		// .addLore(Text.translatable("item.dark_magic.flock_tome.tooltip1").formatted(Formatting.DARK_AQUA, Formatting.ITALIC))
		// .addLore(Text.translatable("item.dark_magic.flock_tome.tooltip2").formatted(Formatting.DARK_AQUA, Formatting.ITALIC));
	
	public static final FlockTomeItem TOME_FLOCK = Registry.register(
		Registries.ITEM, new Identifier("dark_magic", "tome_flock"),
		new FlockTomeItem(FLOCK_PROPERTIES, new FabricItemSettings().maxCount(1))
	);

	///////////////////////////////////////////////////////////////////////

	public static final TomeProperties ENDLESS_VOID_PROPERTIES = new TomeProperties(
		5,
		SoundEvents.ENTITY_ENDERMAN_TELEPORT,
		"endless_void_tome",
		20 * 1);
		// .addLore(Text.translatable("item.dark_magic.flock_tome.tooltip1").formatted(Formatting.DARK_AQUA, Formatting.ITALIC))
		// .addLore(Text.translatable("item.dark_magic.flock_tome.tooltip2").formatted(Formatting.DARK_AQUA, Formatting.ITALIC));
	
	public static final EndlessVoidTomeItem TOME_ENDLESS_VOID = Registry.register(
		Registries.ITEM, new Identifier("dark_magic", "tome_endless_void"),
		new EndlessVoidTomeItem(ENDLESS_VOID_PROPERTIES, new FabricItemSettings().maxCount(1))
	);

	///////////////////////////////////////////////////////////////////////

	// Dark Magic Tab
	public static final ItemGroup TOMES_GROUP = FabricItemGroup.builder(
		new Identifier("dark_magic", "tomes"))
		.icon(() -> new ItemStack(TOME_DEVOUR))
		.build();

    public static void register() {
        // Set up tomes item group
		ItemGroupEvents.modifyEntriesEvent(TOMES_GROUP).register(content -> {
			content.add(TOME_SPIDER_EYE);
			content.add(TOME_DEVOUR);
			content.add(TOME_BLOODLUST);
			content.add(TOME_RIFTS);
			content.add(TOME_ENDLESS_VOID);
			content.add(TOME_FLOCK);
		});
    }
}
