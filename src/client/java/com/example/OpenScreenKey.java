package com.example;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.KeyMapping;
import org.lwjgl.glfw.GLFW;

public class OpenScreenKey {
    private static KeyMapping keyBinding;

    public static void register() {
        keyBinding = KeyBindingHelper.registerKeyBinding(new KeyMapping(
                "key.template-mod.open_screen",
                GLFW.GLFW_KEY_M,
                "category.template-mod"
        ));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (keyBinding.consumeClick()) {
                client.setScreen(new MessageScreen());
            }
        });
    }
}