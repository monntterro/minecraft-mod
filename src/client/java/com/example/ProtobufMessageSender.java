package com.example;

import com.example.proto.MessageProto;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;

public class ProtobufMessageSender {
    public static void sendToServer(String text) {
        MessageProto.Message message = MessageProto.Message.newBuilder()
                .setText(text)
                .build();

        byte[] data = message.toByteArray();
        ClientPlayNetworking.send(new ProtobufMessagePacket(data));
    }
}