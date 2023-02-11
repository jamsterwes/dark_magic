package wesley.magic;

import net.fabricmc.api.ModInitializer;
import wesley.magic.combat.CombatNetworking;
import wesley.magic.registries.DarkMagicScepters;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DarkMagicMod implements ModInitializer {

	public static final Logger LOGGER = LoggerFactory.getLogger("dark_magic");

	@Override
	public void onInitialize() {
		// Register networking
		CombatNetworking.register();

		// Register items
		DarkMagicScepters.register();
	}
}
