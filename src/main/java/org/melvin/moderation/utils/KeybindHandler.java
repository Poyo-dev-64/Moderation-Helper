package org.melvin.moderation.util;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import org.lwjgl.input.Keyboard;
import org.melvin.moderation.events.RenderHandler;

public class KeybindHandler {

    public static KeyBinding toggleStats;
    public static KeyBinding toggleFlags;

    public static void init() {
        toggleStats = new KeyBinding(
                "Toggle Stats UI",
                Keyboard.KEY_P,
                "Moderation Mod"
        );

        toggleFlags = new KeyBinding(
                "Toggle Flag UI",
                Keyboard.KEY_O,
                "Moderation Mod"
        );

        ClientRegistry.registerKeyBinding(toggleStats);
        ClientRegistry.registerKeyBinding(toggleFlags);
    }

    @SubscribeEvent
    public void onKey(InputEvent.KeyInputEvent event) {

        if (toggleStats.isPressed()) {
            if (RenderHandler.INSTANCE != null) {
                RenderHandler.INSTANCE.toggleStats();
            }
        }

        if (toggleFlags.isPressed()) {
            if (RenderHandler.INSTANCE != null) {
                RenderHandler.INSTANCE.toggleFlags();
            }
        }
    }
}
