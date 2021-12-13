package me.fan87.appleclient;

import java.io.File;
import java.lang.instrument.Instrumentation;
import java.lang.reflect.Field;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Vector;

public class AgentMain {

    public static URLClassLoader loader;

    public static Instrumentation instrumentation;

    // -javaagent:path/to/loader=path/to/client
    public static void premain(String arg, Instrumentation instrumentation) {
        AgentMain.instrumentation = instrumentation;
        try {
            System.out.println("Loading Apple Client by fan87.....");
            File clientFile = new File(arg);
            loader = new URLClassLoader(new URL[] { clientFile.toURI().toURL() }, ClassLoader.getSystemClassLoader().getParent());
            Class<?> clientClass = loadClientClass("me.fan87.appleclient.Client");
            clientClass.getDeclaredMethod("init", Class.class, Instrumentation.class).invoke(null, AgentMain.class, instrumentation);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void agentmain(String arg, Instrumentation instrumentation) {

    }


    public static Class<?> loadClientClass(String name) {
        try {
            return loader.loadClass(name);
        } catch (Exception ignored) {
            try {
                ClassLoader classLoader = loader;
                Class[] o = instrumentation.getInitiatedClasses(classLoader);
                for (Class<?> aClass : o) {
                    if (aClass.getName().equals(name)) {
                        return aClass;
                    }
                }
                return null;
            } catch (Exception ee) {
                ee.printStackTrace();
                return null;
            }
        }
    }

}
