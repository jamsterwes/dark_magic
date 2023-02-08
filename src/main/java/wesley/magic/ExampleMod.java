package wesley.magic;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;
import wesley.magic.effects.DecayStatusEffect;
import wesley.magic.effects.DevourStatusEffect;
import wesley.magic.networking.combat.TomeCombatListener;
import wesley.magic.tomes.*;
import wesley.magic.tomes.special.DevourTomeItem;
import wesley.magic.tomes.special.EffectTomeItem;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExampleMod implements ModInitializer {
	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final Logger LOGGER = LoggerFactory.getLogger("modid");

	// Decay Effect
	public static final DecayStatusEffect DECAY = Registry.register(
		Registries.STATUS_EFFECT, new Identifier("dark_magic", "decay"),
		new DecayStatusEffect());

	// TODO: CHANGE TO DEVOUR
	// Decay Effect
	public static final DevourStatusEffect DEVOUR = Registry.register(
		Registries.STATUS_EFFECT, new Identifier("dark_magic", "devour"),
		new DevourStatusEffect());

	public static final Item FLESHY_PASTE = Registry.register(
		Registries.ITEM, new Identifier("dark_magic", "fleshy_paste"),
		new Item(new FabricItemSettings())
	);

	public static final Item ESSENCE_OF_THE_UNDEAD = Registry.register(
		Registries.ITEM, new Identifier("dark_magic", "essence_of_the_undead"),
		new Item(new FabricItemSettings())
	);

	// Dark Magic Tab
	private static final ItemGroup DARK_MAGIC_GROUP = FabricItemGroup.builder(
		new Identifier("dark_magic", "dark_ingredients"))
		.icon(() -> new ItemStack(ESSENCE_OF_THE_UNDEAD))
		.build();

	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.

		LOGGER.info("Hello Fabric world!");

		// Register server listener
		TomeCombatListener.register();

		// Add items to dark magic group
		ItemGroupEvents.modifyEntriesEvent(DARK_MAGIC_GROUP).register(content -> {
			content.add(FLESHY_PASTE);
			content.add(ESSENCE_OF_THE_UNDEAD);
		});

		// Register tomes
		DarkMagicTomes.register();
	}
}
