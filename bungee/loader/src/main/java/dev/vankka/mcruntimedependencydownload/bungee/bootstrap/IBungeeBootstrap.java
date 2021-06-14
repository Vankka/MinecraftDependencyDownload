package dev.vankka.mcruntimedependencydownload.bungee.bootstrap;

public interface IBungeeBootstrap {

    default void onLoad() {}
    default void onEnable() {}
    default void onDisable() {}

}
