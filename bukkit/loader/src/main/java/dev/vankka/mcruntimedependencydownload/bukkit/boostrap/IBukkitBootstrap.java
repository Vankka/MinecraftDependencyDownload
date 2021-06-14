package dev.vankka.mcruntimedependencydownload.bukkit.boostrap;

public interface IBukkitBootstrap {

    default void onLoad() {}
    default void onEnable() {}
    default void onDisable() {}

}
