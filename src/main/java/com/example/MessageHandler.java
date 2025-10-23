package com.example;

import com.example.database.MessageRepository;

import java.util.UUID;

public class MessageHandler {
    private static final MessageRepository repository = new MessageRepository();

    public static void handleMessage(UUID playerUuid, String text) {
        repository.save(playerUuid, text);
    }
}