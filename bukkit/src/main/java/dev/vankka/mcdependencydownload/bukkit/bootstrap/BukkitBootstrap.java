package dev.vankka.mcdependencydownload.bukkit.bootstrap;

import dev.vankka.mcdependencydownload.bootstrap.AbstractBootstrap;
import dev.vankka.mcdependencydownload.classloader.JarInJarClassLoader;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * A bootstrap for Bukkit plugins.
 */
@SuppressWarnings("unused") // API
public abstract class BukkitBootstrap extends AbstractBootstrap implements IBukkitBootstrap {

    private final JavaPlugin plugin;

    /**
     * Do not modify the parameters if you're using the BukkitLoader.
     *
     * @param classLoader the ClassLoader that loaded this class
     * @param plugin the plugin instance
     */
    public BukkitBootstrap(JarInJarClassLoader classLoader, JavaPlugin plugin) {
        super(classLoader);
        this.plugin = plugin;
    }

    /**
     * Gets the {@link JavaPlugin} instance.
     * @return the {@link JavaPlugin} instance provided by the loader.
     */
    public final JavaPlugin getPlugin() {
        return plugin;
    }

}
