package me.fan87.appleclient.sdk;

import javassist.CtClass;
import javassist.CtField;
import javassist.CtMethod;
import lombok.Getter;
import lombok.Setter;
import me.fan87.appleclient.Client;

public abstract class MethodMapper {

    @Getter
    private final MappingManager mappingManager;
    @Getter
    private final String originalMethodName;

    @Getter
    @Setter
    private CtMethod obfuscatedMethod;

    public Client getClient() {
        return mappingManager.getClient();
    }

    public MethodMapper(MappingManager mappingManager, String originalMethodName) {
        this.mappingManager = mappingManager;
        this.originalMethodName = originalMethodName;
    }

    public abstract void processClass(CtClass ctClass) throws Exception;

}
