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

package dev.vankka.mcdependencydownload.neoforge.loader;

import com.mojang.logging.LogUtils;
import dev.vankka.dependencydownload.jarinjar.classloader.JarInJarClassLoader;
import dev.vankka.dependencydownload.jarinjar.loader.ILoader;
import dev.vankka.mcdependencydownload.neoforge.bootstrap.INeoForgeBootstrap;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.util.Optional;

/**
 * A loader for NeoForge mods. Needs to have @Mod annotation on the class.
 */
@SuppressWarnings("unused") // API
public abstract class NeoForgeLoader implements ILoader {

    protected final JarInJarClassLoader classLoader;
    private INeoForgeBootstrap bootstrap;
    private final ModContainer modContainer;
    private final IEventBus eventBus;

    public NeoForgeLoader(IEventBus eventBus, ModContainer modContainer) {
        super();
        this.modContainer = modContainer;
        this.eventBus = eventBus;
        this.classLoader = initialize();
        eventBus.addListener(bootstrap::onCommonSetup);
    }

    private Optional<INeoForgeBootstrap> bootstrap() {
        return Optional.ofNullable(bootstrap);
    }

    @SuppressWarnings("NonExtendableApiUsage") // final
    @Override
    public final JarInJarClassLoader initialize() {
        return ILoader.super.initialize();
    }

    @Override
    public final void initiateBootstrap(Class<?> bootstrapClass, @NotNull JarInJarClassLoader classLoader) throws ReflectiveOperationException {
        Constructor<?> constructor = bootstrapClass.getConstructor(JarInJarClassLoader.class, ModContainer.class, IEventBus.class);
        bootstrap = (INeoForgeBootstrap) constructor.newInstance(classLoader, modContainer, eventBus);
    }

    @Override
    public final @NotNull ClassLoader getParentClassLoader() {
        return getClass().getClassLoader();
    }

    protected void close() {
        try {
            classLoader.close();
        } catch (IOException e) {
            LogUtils.getLogger().error("Failed to close JarInJarClassLoader");
            e.printStackTrace();
        }
    }
}
