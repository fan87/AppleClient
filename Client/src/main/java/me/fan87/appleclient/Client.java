package me.fan87.appleclient;

import javassist.ClassPool;
import javassist.CtClass;
import lombok.Getter;
import me.fan87.appleclient.gui.Window;
import me.fan87.appleclient.module.ModuleManager;
import me.fan87.appleclient.sdk.MappingManager;
import org.reflections.Reflections;

import java.io.ByteArrayInputStream;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.lang.instrument.Instrumentation;
import java.security.ProtectionDomain;
import java.util.ArrayList;
import java.util.List;

public class Client {

    public static void init(Class<?> agentMain, Instrumentation instrumentation) {
        new Client(agentMain, instrumentation).init();
    }

    public static void log(Object message) {
        System.out.println("[Apple Client]  " + message);
    }

    @Getter
    private final Class<?> agentMain;
    @Getter
    private final Instrumentation instrumentation;

    @Getter
    private final Reflections reflections = new Reflections();

    @Getter
    private ModuleManager moduleManager;
    @Getter
    private MappingManager mappingManager;

    @Getter
    private Window window;

    @Getter
    private final ClassPool classPool = ClassPool.getDefault();

    @Getter
    private final List<CtClass> loadedClasses = new ArrayList<>();

    public Client(Class<?> agentMain, Instrumentation instrumentation) {
        this.agentMain = agentMain;
        this.instrumentation = instrumentation;
    }

    public void init() {

        this.instrumentation.addTransformer(new ClassFileTransformer() {
            @Override
            public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {
                if (
                        className.startsWith("me/fan87/appleclient") ||
                        className.startsWith("java") ||
                        className.startsWith("jdk") ||
                        className.startsWith("javassist")
                ) {
                    return classfileBuffer;
                }
                try {
                    loadedClasses.add(classPool.makeClass(new ByteArrayInputStream(classfileBuffer)));
                } catch (Exception ignored) {}
                return classfileBuffer;
            }
        });

        this.moduleManager = new ModuleManager(this);
        this.mappingManager = new MappingManager(this);
        this.window = new Window(this);
        new Thread(() -> {
            window.init();
            window.run();
            window.destroy();
        }).start();
        log("Welcome to Apple Client! Client has been successfully loaded!");
    }

}
