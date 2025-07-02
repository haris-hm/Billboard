package com.harismehuljic.billboard;

import net.fabricmc.api.ModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Billboard implements ModInitializer {
	public static final String MOD_ID = "billboard";

	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		Registries.registerAll();
		LOGGER.info("Hello Fabric world!");
	}
}
