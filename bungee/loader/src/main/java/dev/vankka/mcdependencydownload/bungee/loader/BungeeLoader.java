/*
 * MIT License
 *
 * Copyright (c) 2021-2024 Vankka
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

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
