package me.fan87.appleclient.gui;

import imgui.ImGui;
import lombok.Getter;
import me.fan87.appleclient.Client;
import me.fan87.appleclient.module.Category;
import me.fan87.appleclient.module.Module;
import me.fan87.appleclient.sdk.ClassMapper;
import me.fan87.appleclient.sdk.FieldMapper;
import me.fan87.appleclient.sdk.MethodMapper;

public class MainGui {

    @Getter
    private final Client client;

    public MainGui(Client client) {
        this.client = client;
    }

    public void render() {

        for (Category value : Category.values()) {
            ImGui.begin(value.getName());
            for (Module module : client.getModuleManager().getModulesByCategory(value)) {
                module.renderModule();
            }
            ImGui.end();
        }

        ImGui.begin("Loader");
        if (ImGui.button("Scan Classes")) {
            client.getMappingManager().getClient().getMappingManager().scanClass();
        }
        for (ClassMapper classMapper : client.getMappingManager().getClassMappers()) {
            if (classMapper.getObfuscatedClass() != null) {
                ImGui.textColored(0.2f, 1f, 0.2f, 1f, classMapper.getOriginalClassName() + "  ( " + classMapper.getObfuscatedClass().getName() + " )");
            } else {
                ImGui.textColored(1f, 0.2f, 0.2f, 1f, classMapper.getOriginalClassName() + "  ( UNMAPPED )");
            }
            for (MethodMapper methodMapper : classMapper.getMethodMappers()) {
                ImGui.setCursorPosX(30f);
                if (methodMapper.getObfuscatedMethod() != null) {
                    ImGui.textColored(0.2f, 1f, 0.2f, 1f, methodMapper.getOriginalMethodName() + "  ( " + methodMapper.getObfuscatedMethod().getName() + " )");
                } else {
                    ImGui.textColored(1f, 0.2f, 0.2f, 1f, methodMapper.getOriginalMethodName() + "  ( UNMAPPED )");
                }
            }
            for (FieldMapper fieldMapper : classMapper.getFieldMappers()) {
                ImGui.setCursorPosX(30f);
                if (fieldMapper.getObfuscatedField() != null) {
                    ImGui.textColored(0.2f, 1f, 0.2f, 1f, fieldMapper.getOriginalFieldName() + "  ( " + fieldMapper.getObfuscatedField().getName() + " )");
                } else {
                    ImGui.textColored(1f, 0.2f, 0.2f, 1f, fieldMapper.getOriginalFieldName() + "  ( UNMAPPED )");
                }
            }
        }
        ImGui.end();

    }

}
