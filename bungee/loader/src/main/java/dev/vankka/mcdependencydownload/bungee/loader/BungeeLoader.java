package dev.vankka.mcdependencydownload.bungee.loader;

import dev.vankka.mcdependencydownload.bungee.bootstrap.IBungeeBootstrap;
import dev.vankka.mcdependencydownload.classloader.JarInJarClassLoader;
import dev.vankka.mcdependencydownload.loader.ILoader;
import net.md_5.bungee.api.plugin.Plugin;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Optional;

/**
 * The loader class, the class that extends this should be the bungee.ymls main property.
 */
@SuppressWarnings("unused") // API
public abstract class BungeeLoader extends Plugin implements ILoader {

    private IBungeeBootstrap bootstrap;

    public BungeeLoader() {
        super();
        initialize();
    }

    private Optional<IBungeeBootstrap> bootstrap() {
        return Optional.ofNullable(bootstrap);
    }

    @Override
    public final void initialize() {
        ILoader.super.initialize();
    }

    @Override
    public final void initiateBootstrap(Class<?> bootstrapClass, JarInJarClassLoader classLoader)
            throws InvocationTargetException, InstantiationException, IllegalAccessException, NoSuchMethodException {

        Constructor<?> constructor = bootstrapClass.getConstructor(JarInJarClassLoader.class, Plugin.class);
        bootstrap = (IBungeeBootstrap) constructor.newInstance(classLoader, this);
    }

    @Override
    public final String getName() {
        return getDescription().getName();
    }

    @Override
    public final ClassLoader getParentClassLoader() {
        return getClass().getClassLoader();
    }

    @Override
    public final void onLoad() {
        bootstrap().ifPresent(IBungeeBootstrap::onLoad);
    }

    @Override
    public final void onEnable() {
        bootstrap().ifPresent(IBungeeBootstrap::onEnable);
    }

    @Override
    public final void onDisable() {
        bootstrap().ifPresent(IBungeeBootstrap::onDisable);
    }
}
