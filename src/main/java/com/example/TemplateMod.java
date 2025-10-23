package com.example;

import com.example.database.DatabaseManager;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;

public class TemplateMod implements ModInitializer {

    @Override
    public void onInitialize() {
        ProtobufMessagePacket.register();
        ProtobufMessagePacket.registerServer();

        ServerLifecycleEvents.SERVER_STARTING.register(server -> {
            String jdbcUrl = System.getProperty("PG_URL", "jdbc:postgresql://localhost:5433/minecraft");
            String username = System.getProperty("PG_USER", "myuser");
            String password = System.getProperty("PG_PASSWORD", "mypassword");

            DatabaseManager.initialize(jdbcUrl, username, password);
        });

        ServerLifecycleEvents.SERVER_STOPPING.register(server -> DatabaseManager.shutdown());
    }
}