package me.fan87.appleclient.gui;

import imgui.ImGui;
import lombok.Getter;
import me.fan87.appleclient.Client;

public class MainGui {

    @Getter
    private final Client client;

    public MainGui(Client client) {
        this.client = client;
    }

    public void render() {
        ImGui.text("Hello World!");
    }

}
