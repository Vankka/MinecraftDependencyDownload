package dev.vankka.mcdependencydownload.bukkit.loader;

import dev.vankka.mcdependencydownload.bukkit.bootstrap.IBukkitBootstrap;
import dev.vankka.mcdependencydownload.classloader.JarInJarClassLoader;
import dev.vankka.mcdependencydownload.loader.ILoader;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Optional;

/**
 * The loader class, the lass that extends this should be the plugin.ymls main property.
 */
@SuppressWarnings("unused") // API
public abstract class BukkitLoader extends JavaPlugin implements ILoader {

    private IBukkitBootstrap bootstrap;

    public BukkitLoader() {
        super();
        initialize();
    }

    private Optional<IBukkitBootstrap> bootstrap() {
        return Optional.ofNullable(bootstrap);
    }

    @Override
    public final void initialize() {
        ILoader.super.initialize();
    }

    @Override
    public final void initiateBootstrap(Class<?> bootstrapClass, JarInJarClassLoader classLoader)
            throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {

        Constructor<?> constructor = bootstrapClass.getConstructor(JarInJarClassLoader.class, JavaPlugin.class);
        bootstrap = (IBukkitBootstrap) constructor.newInstance(classLoader, this);
    }

    @Override
    public final ClassLoader getParentClassLoader() {
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
    }
}
