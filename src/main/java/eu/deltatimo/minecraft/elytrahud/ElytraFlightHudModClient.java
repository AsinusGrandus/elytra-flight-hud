package eu.deltatimo.minecraft.elytrahud;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.text.Text;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ElytraFlightHudModClient implements ClientModInitializer {
	/**
	 * This logger is used to write text to the console and the log file.
	 * It is considered best practice to use your mod id as the logger's name.
	 * That way, it's clear which mod wrote info, warnings, and errors.
 	 */

	public static final String MOD_ID = "elytra-flight-hud";
	public static final Logger LOGGER = LogManager.getLogger(MOD_ID);

	@Override
	public void onInitializeClient() {
		/**
		 * This code runs as soon as Minecraft is in a mod-load-ready state.
		 * However, some things (like resources) may still be uninitialized.
		 * Proceed with mild caution.
		 */

		// Add the overlay to the hud
		HudRenderCallback.EVENT.register(HudRenderer::onHudRender);

		// Add a keybind to toggle the overlay
		var keyToggle = KeyBindingHelper.registerKeyBinding(new KeyBinding("key.elytra_flight_hud.toggle", InputUtil.Type.KEYSYM, InputUtil.GLFW_KEY_U, "category.elytra_flight_hud.general"));

		ClientTickEvents.END_CLIENT_TICK.register(client -> {
			if (keyToggle.wasPressed()) {
				HudRenderer.switchHudEnabled();
				if (client.player != null) {
					client.player.sendMessage(HudRenderer.shouldRenderHud() ? Text.translatable("hud.elytra_flight_hud.hud_enabled") : Text.translatable("hud.elytra_flight_hud.hud_disabled"), true);
				}
			}
		});

		LOGGER.info("Skyass Elytra Flight Hud initialized!");
	}

	private void renderLine(float x, float y, float x2, float y2, float r, float g, float b, float a) {
	}
}
