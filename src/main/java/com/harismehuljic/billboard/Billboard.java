package com.harismehuljic.billboard;

import com.harismehuljic.billboard.util.Registries;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.loader.api.FabricLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Billboard implements ModInitializer {
	public static final String MOD_ID = "billboard";

	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
	public static final String VERSION = FabricLoader.getInstance().getModContainer(MOD_ID).get().getMetadata().getVersion().getFriendlyString();

	@Override
	public void onInitialize() {
		Registries.registerAll();
		LOGGER.info("Billboard version {} succesfully loaded.", VERSION);
	}
}
