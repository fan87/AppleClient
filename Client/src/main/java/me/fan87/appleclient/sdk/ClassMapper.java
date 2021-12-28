package me.fan87.appleclient.sdk;

import javassist.CtClass;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import me.fan87.appleclient.Client;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

public abstract class ClassMapper {

    @Getter
    private final MappingManager mappingManager;
    @Getter
    private final String originalClassName;

    @Getter
    @Setter
    private CtClass obfuscatedClass;


    @SneakyThrows
    public FieldMapper[] getFieldMappers() {
        List<FieldMapper> list = new ArrayList<>();
        for (Field declaredField : getClass().getDeclaredFields()) {
            if (!declaredField.getType().getSuperclass().equals(FieldMapper.class)) continue;
            if (Modifier.isStatic(declaredField.getModifiers())) continue;
            declaredField.setAccessible(true);
            list.add(((FieldMapper) declaredField.get(this)));
        }
        return list.toArray(new FieldMapper[0]);
    }

    @SneakyThrows
    public MethodMapper[] getMethodMappers() {
        List<MethodMapper> list = new ArrayList<>();
        for (Field declaredField : getClass().getDeclaredFields()) {
            if (!declaredField.getType().getSuperclass().equals(MethodMapper.class)) continue;
            if (Modifier.isStatic(declaredField.getModifiers())) continue;
            declaredField.setAccessible(true);
            list.add(((MethodMapper) declaredField.get(this)));
        }
        return list.toArray(new MethodMapper[0]);
    }

    public Client getClient() {
        return mappingManager.getClient();
    }

    public ClassMapper(MappingManager mappingManager, String originalClassName) {
        this.mappingManager = mappingManager;
        this.originalClassName = originalClassName;
    }

    public abstract void processClass(CtClass ctClass) throws Exception;

}
