package dev.vankka.mcdependencydownload.bukkit.loader;

import dev.vankka.dependencydownload.jarinjar.classloader.JarInJarClassLoader;
import dev.vankka.dependencydownload.jarinjar.loader.ILoader;
import dev.vankka.mcdependencydownload.bukkit.bootstrap.IBukkitBootstrap;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.util.Optional;

/**
 * The loader class, the lass that extends this should be the plugin.ymls main property.
 */
@SuppressWarnings("unused") // API
public abstract class BukkitLoader extends JavaPlugin implements ILoader {

    protected final JarInJarClassLoader classLoader;
    private IBukkitBootstrap bootstrap;

    public BukkitLoader() {
        super();
        classLoader = initialize();
    }

    private Optional<IBukkitBootstrap> bootstrap() {
        return Optional.ofNullable(bootstrap);
    }

    @SuppressWarnings("NonExtendableApiUsage") // final
    @Override
    public final JarInJarClassLoader initialize() {
        return ILoader.super.initialize();
    }

    @Override
    public final void initiateBootstrap(Class<?> bootstrapClass, @NotNull JarInJarClassLoader classLoader) throws ReflectiveOperationException {
        Constructor<?> constructor = bootstrapClass.getConstructor(JarInJarClassLoader.class, JavaPlugin.class);
        bootstrap = (IBukkitBootstrap) constructor.newInstance(classLoader, this);
    }

    @Override
    public final @NotNull ClassLoader getParentClassLoader() {
        return getClassLoader();
    }

    @Override
    public final void onLoad() {
        bootstrap().ifPresent(IBukkitBootstrap::onLoad);
    }

    @Override
    public final void onEnable() {
        bootstrap().ifPresent(IBukkitBootstrap::onEnable);
    }

    @Override
    public final void onDisable() {
        bootstrap().ifPresent(IBukkitBootstrap::onDisable);
        close();
    }

    protected void close() {
        try {
            classLoader.close();
        } catch (IOException e) {
            getLogger().severe("Failed to close JarInJarClassLoader");
            e.printStackTrace();
        }
    }
}
