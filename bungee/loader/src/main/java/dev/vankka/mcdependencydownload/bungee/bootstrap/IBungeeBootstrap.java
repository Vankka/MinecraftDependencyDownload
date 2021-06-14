package dev.vankka.mcdependencydownload.bungee.bootstrap;

public interface IBungeeBootstrap {

    default void onLoad() {}
    default void onEnable() {}
    default void onDisable() {}

}
