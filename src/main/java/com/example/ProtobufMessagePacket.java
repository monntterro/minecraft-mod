package com.example;

import com.example.proto.MessageProto;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public record ProtobufMessagePacket(byte[] data) implements CustomPacketPayload {
    public static final CustomPacketPayload.Type<ProtobufMessagePacket> TYPE =
            new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath("template-mod", "protobuf_message"));

    public static final StreamCodec<FriendlyByteBuf, ProtobufMessagePacket> CODEC = StreamCodec.of(
            (buf, packet) -> {
                buf.writeInt(packet.data.length);
                buf.writeBytes(packet.data);
            },
            buf -> {
                int length = buf.readInt();
                byte[] data = new byte[length];
                buf.readBytes(data);
                return new ProtobufMessagePacket(data);
            }
    );

    @Override
    public @NotNull Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void register() {
        PayloadTypeRegistry.playC2S().register(TYPE, CODEC);
    }

    public static void registerServer() {
        ServerPlayNetworking.registerGlobalReceiver(TYPE, (packet, context) -> {
            context.server().execute(() -> {
                try {
                    MessageProto.Message message = MessageProto.Message.parseFrom(packet.data);
                    MessageHandler.handleMessage(context.player().getUUID(), message.getText());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        });
    }
}