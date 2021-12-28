package me.fan87.appleclient.module;

import imgui.ImGui;
import imgui.type.ImBoolean;
import lombok.Getter;
import me.fan87.appleclient.Client;
import me.fan87.appleclient.event.EventManager;

public abstract class Module {

    protected final Client client;
    @Getter
    private final String name;
    @Getter
    private final String description;
    @Getter
    private final Category category;

    private boolean toggled;

    private final ImBoolean activated = new ImBoolean(false);



    public Module(Client client, String name, String description, Category category) {
        this.client = client;
        this.name = name;
        this.description = description;
        this.category = category;
    }

    protected abstract void onEnable();
    protected abstract void onDisable();
    protected abstract void render();

    public void setToggled(boolean toggled) {
        if (this.toggled != toggled) {
            this.toggled = !this.toggled;
            if (this.toggled) {
                EventManager.EVENT_BUS.register(this);
                onEnable();
            } else {
                EventManager.EVENT_BUS.unregister(this);
                onDisable();
            }
        }
    }

    public final void renderModule() {
        float oldCursor = ImGui.getCursorPosX();
        ImGui.setCursorPosX(20);
        ImGui.beginChild(getName(), ImGui.getWindowWidth() - ImGui.getCursorPosX()*2, 300);
        if (ImGui.checkbox(getName(), activated)) {
            setToggled(activated.get());
        }
        ImGui.textWrapped(getDescription());
        render();
        ImGui.endChild();
    }

}
