package com.example;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public class MessageScreen extends Screen {
    private EditBox textField;

    public MessageScreen() {
        super(Component.literal("Send Message"));
    }

    @Override
    protected void init() {
        this.textField = new EditBox(this.font, this.width / 2 - 100, this.height / 2 - 10, 200, 20, Component.literal(""));
        this.textField.setMaxLength(256);
        this.addWidget(this.textField);

        this.addRenderableWidget(Button.builder(Component.literal("Send"), button -> {
            String message = this.textField.getValue();
            if (!message.isEmpty()) {
                ProtobufMessageSender.sendToServer(message);
                this.onClose();
            }
        }).bounds(this.width / 2 - 50, this.height / 2 + 20, 100, 20).build());
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float delta) {
        super.render(graphics, mouseX, mouseY, delta);
        this.textField.render(graphics, mouseX, mouseY, delta);
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
}