package dev.vankka.mcdependencydownload.bungee.bootstrap;

import dev.vankka.mcdependencydownload.bootstrap.AbstractBootstrap;
import dev.vankka.mcdependencydownload.classloader.JarInJarClassLoader;
import dev.vankka.mcdependencydownload.bootstrap.classpath.JarInJarClasspathAppender;
import net.md_5.bungee.api.plugin.Plugin;

/**
 * A bootstrap for Bungee plugins.
 */
@SuppressWarnings("unused") // API
public abstract class BungeeBootstrap extends AbstractBootstrap implements IBungeeBootstrap {

    private final Plugin plugin;
    private final JarInJarClasspathAppender classpathAppender;

    /**
     * Do not modify the parameters if you're using the BungeeLoader.
     *
     * @param classLoader the ClassLoader that loaded this class
     * @param plugin the plugin instance
     */
    public BungeeBootstrap(JarInJarClassLoader classLoader, Plugin plugin) {
        super(classLoader);
        this.plugin = plugin;
        this.classpathAppender = new JarInJarClasspathAppender(classLoader);
    }

    /**
     * Gets the {@link Plugin} instance.
     * @return the {@link Plugin} instance provided by the loader.
     */
    public Plugin getPlugin() {
        return plugin;
    }

    /**
     * Gets a instance of {@link JarInJarClassLoader} that was created with the {@link JarInJarClassLoader} that loaded this class.
     * @return a {@link JarInJarClassLoader}
     */
    public JarInJarClasspathAppender getClasspathAppender() {
        return classpathAppender;
    }
}
