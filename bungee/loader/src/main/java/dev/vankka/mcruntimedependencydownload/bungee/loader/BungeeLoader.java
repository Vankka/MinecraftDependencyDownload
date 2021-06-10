package dev.vankka.mcruntimedependencydownload.bungee.loader;

import dev.vankka.mcruntimedependencydownload.bungee.bootstrap.IBungeeBootstrap;
import dev.vankka.mcruntimedependencydownload.classloader.JarInJarClassLoader;
import dev.vankka.mcruntimedependencydownload.loader.ILoader;
import net.md_5.bungee.api.plugin.Plugin;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

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
        bootstrap.onLoad();
    }

    @Override
    public final void onEnable() {
        bootstrap.onEnable();
    }

    @Override
    public final void onDisable() {
        bootstrap.onDisable();
    }
}
