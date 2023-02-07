package wesley.magic;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;
import wesley.magic.effects.DecayStatusEffect;
import wesley.magic.networking.combat.TomeCombatListener;
import wesley.magic.tomes.*;

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

	// TODO: move this somewhere else?
	// Spider Tome
	public static final EffectTomeItem TOME_SPIDER_EYE = Registry.register(
		Registries.ITEM, new Identifier("dark_magic", "tome_spider_eye"),
		new EffectTomeItem(
			"spider_tome",
			DECAY, 5 * 20, 1,
			20, SoundEvents.ENTITY_SPIDER_HURT, new FabricItemSettings().maxCount(1))
	);

	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.

		LOGGER.info("Hello Fabric world!");

		// Register server listener
		TomeCombatListener.register();
	}
}
