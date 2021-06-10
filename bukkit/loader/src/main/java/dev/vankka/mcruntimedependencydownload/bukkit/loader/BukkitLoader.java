package dev.vankka.mcruntimedependencydownload.bukkit.loader;

import dev.vankka.mcruntimedependencydownload.bukkit.boostrap.IBukkitBootstrap;
import dev.vankka.mcruntimedependencydownload.classloader.JarInJarClassLoader;
import dev.vankka.mcruntimedependencydownload.loader.ILoader;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

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

    @Override
    public void initiateBootstrap(Class<?> bootstrapClass, JarInJarClassLoader classLoader) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        Constructor<?> constructor = bootstrapClass.getConstructor(JarInJarClassLoader.class, JavaPlugin.class);
        bootstrap = (IBukkitBootstrap) constructor.newInstance(classLoader, this);
    }

    @Override
    public final ClassLoader getParentClassLoader() {
        return getClassLoader();
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
