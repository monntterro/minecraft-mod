package com.example;

import net.fabricmc.api.ClientModInitializer;

public class TemplateModClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		OpenScreenKey.register();
	}
}