package dev.vankka.mcdependencydownload.bungee.loader;

import dev.vankka.dependencydownload.jarinjar.classloader.JarInJarClassLoader;
import dev.vankka.dependencydownload.jarinjar.loader.ILoader;
import dev.vankka.mcdependencydownload.bungee.bootstrap.IBungeeBootstrap;
import net.md_5.bungee.api.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.util.Optional;

/**
 * The loader class, the class that extends this should be the bungee.ymls main property.
 */
@SuppressWarnings("unused") // API
public abstract class BungeeLoader extends Plugin implements ILoader {

    protected final JarInJarClassLoader classLoader;
    private IBungeeBootstrap bootstrap;

    public BungeeLoader() {
        super();
        this.classLoader = initialize();
    }

    private Optional<IBungeeBootstrap> bootstrap() {
        return Optional.ofNullable(bootstrap);
    }

    @SuppressWarnings("NonExtendableApiUsage") // final
    @Override
    public final JarInJarClassLoader initialize() {
        return ILoader.super.initialize();
    }

    @Override
    public final void initiateBootstrap(Class<?> bootstrapClass, @NotNull JarInJarClassLoader classLoader) throws ReflectiveOperationException {
        Constructor<?> constructor = bootstrapClass.getConstructor(JarInJarClassLoader.class, Plugin.class);
        bootstrap = (IBungeeBootstrap) constructor.newInstance(classLoader, this);
    }

    @Override
    public final @NotNull String getName() {
        return getDescription().getName();
    }

    @Override
    public final @NotNull ClassLoader getParentClassLoader() {
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
