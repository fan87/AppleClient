package me.fan87.appleclient.module.impl;

import imgui.ImGui;
import me.fan87.appleclient.Client;
import me.fan87.appleclient.module.Category;
import me.fan87.appleclient.module.Module;

public class TestModule extends Module {
    public TestModule(Client client) {
        super(client, "Test", "A module for testing", Category.MISC);
    }

    private final float[] value = new float[1];

    @Override
    protected void onEnable() {
        Client.log("Enabled! Value is " + value[0]);
    }

    @Override
    protected void onDisable() {
        Client.log("Disabled! Value is " + value[0]);
    }

    @Override
    protected void render() {
        ImGui.sliderFloat("Example Value", value, 0, 10);
    }
}
