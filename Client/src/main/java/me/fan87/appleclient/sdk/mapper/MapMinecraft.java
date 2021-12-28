package me.fan87.appleclient.sdk.mapper;

import javassist.CtClass;
import org.objectweb.asm.tree.ClassNode;
import me.fan87.appleclient.sdk.ClassMapper;
import me.fan87.appleclient.sdk.MappingManager;
import me.fan87.appleclient.utils.ASMUtils;

public class MapMinecraft extends ClassMapper {

    public MapMinecraft(MappingManager mappingManager) {
        super(mappingManager, "Minecraft");
    }

    @Override
    public void processClass(CtClass ctClass) throws Exception {
        ClassNode classNode = ASMUtils.toClassNode(ctClass);
        if (ASMUtils.hasString(classNode, "Null returned as 'hitResult', this shouldn't happen!", false) > 0) {
            setObfuscatedClass(ctClass);
        }

    }
}
