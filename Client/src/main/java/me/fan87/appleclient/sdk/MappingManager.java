package me.fan87.appleclient.sdk;

import javassist.CtClass;
import lombok.Getter;
import me.fan87.appleclient.Client;
import me.fan87.appleclient.sdk.mapper.MapMinecraft;

import java.util.ArrayList;
import java.util.List;

public class MappingManager {

    @Getter
    private final Client client;

    public final MapMinecraft mapMinecraft = new MapMinecraft(this);

    public ClassMapper[] getClassMappers() {
        return new ClassMapper[] {
            mapMinecraft
        };
    }

    public MappingManager(Client client) {
        this.client = client;
    }

    public void scanClass() {
        try {
            List<CtClass> loadedClasses = new ArrayList<>(client.getLoadedClasses());
            for (CtClass loadedClass : loadedClasses) {
                for (ClassMapper classMapper : getClassMappers()) {
                    if (classMapper.getObfuscatedClass() == loadedClass) break;
                    if (classMapper.getObfuscatedClass() != null) continue;
                    try {
                        classMapper.processClass(loadedClass);
                        if (classMapper.getObfuscatedClass() != null) {
                            for (MethodMapper methodMapper : classMapper.getMethodMappers()) {
                                try {
                                    methodMapper.processClass(classMapper.getObfuscatedClass());
                                } catch (Exception ignored) {}
                            }
                            for (FieldMapper fieldMapper : classMapper.getFieldMappers()) {
                                try {
                                    fieldMapper.processClass(classMapper.getObfuscatedClass());
                                } catch (Exception ignored) {}
                            }
                        }
                    } catch (Exception ignored) {}
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
