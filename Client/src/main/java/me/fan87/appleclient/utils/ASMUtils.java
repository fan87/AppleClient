package me.fan87.appleclient.utils;

import javassist.CtClass;
import lombok.SneakyThrows;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.tree.*;

public class ASMUtils {

    @SneakyThrows
    public static ClassNode toClassNode(CtClass ctClass) {
        ClassReader reader = new ClassReader(ctClass.toBytecode());
        ClassNode classNode = new ClassNode();
        reader.accept(classNode, ClassReader.SKIP_DEBUG);
        return classNode;
    }

    public static int hasString(ClassNode classNode, String string, boolean ignoredCase) {
        int count = 0;
        for (MethodNode method : classNode.methods) {
            count += hasString(method, string, ignoredCase);
        }
        for (FieldNode field : classNode.fields) {
            count += hasString(field, string, ignoredCase);
        }
        return count;
    }

    public static int hasString(FieldNode fieldNode, String string, boolean ignoredCase) {
        int count = 0;
        if (fieldNode.value instanceof String && (ignoredCase?((String) fieldNode.value).equalsIgnoreCase(string):fieldNode.value.equals(string))) {
            count++;
        }
        return count;
    }

    public static int hasString(MethodNode methodNode, String string, boolean ignoredCase) {
        int count = 0;
        for (AbstractInsnNode instruction : methodNode.instructions) {
            if (instruction instanceof LdcInsnNode) {
                Object cst = ((LdcInsnNode) instruction).cst;
                if (cst instanceof String && (ignoredCase?((String) cst).equalsIgnoreCase(string):cst.equals(string))) {
                    count++;
                }
            }
        }
        return count;
    }

}
