package dev.vankka.mcruntimedependencydownload.bungee.bootstrap;

import dev.vankka.mcruntimedependencydownload.bootstrap.AbstractBootstrap;
import dev.vankka.mcruntimedependencydownload.classloader.JarInJarClassLoader;
import net.md_5.bungee.api.plugin.Plugin;

/**
 * A bootstrap for Bungee plugins.
 */
@SuppressWarnings("unused") // API
public abstract class BungeeBootstrap extends AbstractBootstrap implements IBungeeBootstrap {

    private final Plugin plugin;

    /**
     * Do not modify the parameters if you're using the BungeeLoader.
     *
     * @param classLoader the ClassLoader that loaded this class
     * @param plugin the plugin instance
     */
    public BungeeBootstrap(JarInJarClassLoader classLoader, Plugin plugin) {
        super(classLoader);
        this.plugin = plugin;
    }

    /**
     * Gets the {@link Plugin} instance.
     * @return the {@link Plugin} instance provided by the loader.
     */
    public Plugin getPlugin() {
        return plugin;
    }

    public void onLoad() {}
    public void onEnable() {}
    public void onDisable() {}
}
