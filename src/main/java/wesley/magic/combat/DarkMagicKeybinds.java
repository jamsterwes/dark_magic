package wesley.magic.combat;

import org.lwjgl.glfw.GLFW;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;

public class DarkMagicKeybinds {
    @FunctionalInterface
    public static interface KeyHandler {
        public void onPressed(MinecraftClient client);
    }

    public static KeyBinding altFire = KeyBindingHelper.registerKeyBinding(new KeyBinding(
        "key.dark_magic.alt_fire",
        InputUtil.Type.KEYSYM,
        GLFW.GLFW_KEY_Z,
        "category.dark_magic.magic_combat"
    ));

    public static void listen(KeyBinding binding, KeyHandler handler) {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            // For each press during this tick
            while (binding.wasPressed()) {
                handler.onPressed(client);
            }
        });
    }
}
