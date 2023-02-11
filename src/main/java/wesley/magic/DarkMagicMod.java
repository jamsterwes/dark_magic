package wesley.magic;

import net.fabricmc.api.ModInitializer;
import wesley.magic.scepters.DarkMagicScepters;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DarkMagicMod implements ModInitializer {
	
	public static final Logger LOGGER = LoggerFactory.getLogger("dark_magic");

	@Override
	public void onInitialize() {
		// Register tomes
		DarkMagicScepters.register();
	}
}
