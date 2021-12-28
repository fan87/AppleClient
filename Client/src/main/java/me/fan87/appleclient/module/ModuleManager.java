package me.fan87.appleclient.module;

import lombok.Getter;
import me.fan87.appleclient.Client;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class ModuleManager {

    private final List<Module> modules = new ArrayList<>();
    @Getter
    private final Client client;

    public List<Module> getModules() {
        return new ArrayList<>(modules);
    }

    public ModuleManager(Client client) {
        this.client = client;
        registerModules();
    }

    public void registerModules() {
        for (Class<? extends Module> moduleClazz : client.getReflections().getSubTypesOf(Module.class)) {
            registerModule(moduleClazz);
        }
    }

    public <T extends Module> T registerModule(Class<T> clazz) {
        try {
            Module module = clazz.getConstructor(Client.class).newInstance(client);
            modules.add(module);
            return (T) module;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public <T extends Module> T getModule(Class<T> clazz) {
        for (Module module : getModules()) {
            if (clazz.isInstance(module)) {
                return (T) module;
            }
        }
        return null;
    }

    public List<Module> getModulesByCategory(Category category) {
        return getModules().stream().filter(module -> module.getCategory() == category).collect(Collectors.toList());
    }

}
