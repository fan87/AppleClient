package me.fan87.appleclient.sdk;

import javassist.CtClass;
import javassist.CtField;
import lombok.Getter;
import lombok.Setter;
import me.fan87.appleclient.Client;

public abstract class FieldMapper {

    @Getter
    private final MappingManager mappingManager;
    @Getter
    private final String originalFieldName;

    @Getter
    @Setter
    private CtField obfuscatedField;

    public Client getClient() {
        return mappingManager.getClient();
    }

    public FieldMapper(MappingManager mappingManager, String originalFieldName) {
        this.mappingManager = mappingManager;
        this.originalFieldName = originalFieldName;
    }

    public abstract void processClass(CtClass ctClass) throws Exception;

}
