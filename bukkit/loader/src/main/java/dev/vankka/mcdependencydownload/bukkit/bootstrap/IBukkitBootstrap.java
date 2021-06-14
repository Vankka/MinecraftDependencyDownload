package dev.vankka.mcdependencydownload.bukkit.bootstrap;

public interface IBukkitBootstrap {

    default void onLoad() {}
    default void onEnable() {}
    default void onDisable() {}

}
