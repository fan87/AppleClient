package me.fan87.appleclient.module;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Category {

    VISUAL("Visual", "Render extra stuff on your screen"),
    COMBAT("Combat", "Allows you to be better at PVP"),
    MOVEMENT("Movement", "Move unusual"),
    MISC("Misc", "Literally misc."),
    EXPLOITS("Exploits", "Hacks allows you to exploit the server and do things you normally can't do."),
    PLAYER("Player", "Hacks related to your local player.");

    final String name;
    final String description;

}
