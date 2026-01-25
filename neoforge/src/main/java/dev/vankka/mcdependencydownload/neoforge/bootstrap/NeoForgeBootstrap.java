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

package dev.vankka.mcdependencydownload.neoforge.bootstrap;

import dev.vankka.dependencydownload.jarinjar.bootstrap.AbstractBootstrap;
import dev.vankka.dependencydownload.jarinjar.bootstrap.classpath.JarInJarClasspathAppender;
import dev.vankka.dependencydownload.jarinjar.classloader.JarInJarClassLoader;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;

/**
 * A bootstrap for NeoForge mods.
 */
@SuppressWarnings("unused") // API
public abstract class NeoForgeBootstrap extends AbstractBootstrap implements INeoForgeBootstrap {

    private final JarInJarClasspathAppender classpathAppender;
    private final ModContainer modContainer;
    private final IEventBus eventBus;

    /**
     * Do not modify the parameters if you're using the NeoForgeLoader.
     *
     * @param classLoader the ClassLoader that loaded this class
     * @param modContainer the ModContainer of the mod being loaded
     * @param eventBus the EventBus of the mod being loaded
     */
    public NeoForgeBootstrap(JarInJarClassLoader classLoader, ModContainer modContainer, IEventBus eventBus) {
        super(classLoader);
        this.classpathAppender = new JarInJarClasspathAppender(classLoader);
        this.modContainer = modContainer;
        this.eventBus = eventBus;
    }

    /**
     * Gets an instance of {@link JarInJarClassLoader} that was created with the {@link JarInJarClassLoader} that loaded this class.
     * @return a {@link JarInJarClassLoader}
     */
    public JarInJarClasspathAppender getClasspathAppender() {
        return classpathAppender;
    }

    /**
     * Gets the ModContainer of the mod being loaded.
     * @return the ModContainer of the mod being loaded
     */
    public ModContainer getModContainer() {
        return modContainer;
    }

    /**
     * Gets the EventBus of the mod being loaded.
     * @return the EventBus of the mod being loaded
     */
    public IEventBus getEventBus() {
        return eventBus;
    }
}
