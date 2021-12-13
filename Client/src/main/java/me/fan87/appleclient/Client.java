package me.fan87.appleclient;

import lombok.Getter;
import me.fan87.appleclient.gui.Window;

import java.lang.instrument.Instrumentation;

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
    private Window window;

    public Client(Class<?> agentMain, Instrumentation instrumentation) {
        this.agentMain = agentMain;
        this.instrumentation = instrumentation;
    }

    public void init() {
        this.window = new Window(this);
        new Thread(() -> {
            window.init();
            window.run();
            window.destroy();
        }).start();
        log("Welcome to Apple Client! Client has been successfully loaded!");
    }




}
