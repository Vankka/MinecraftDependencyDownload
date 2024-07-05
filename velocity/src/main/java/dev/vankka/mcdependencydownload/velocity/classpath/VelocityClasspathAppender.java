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

package dev.vankka.mcdependencydownload.velocity.classpath;

import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.proxy.ProxyServer;
import dev.vankka.dependencydownload.classpath.ClasspathAppender;
import org.jetbrains.annotations.NotNull;

import java.nio.file.Path;

/**
 * A {@link ClasspathAppender} that appends dependencies directly via Velocity methods.
 */
@SuppressWarnings("unused") // API
public class VelocityClasspathAppender implements ClasspathAppender {

    private final Object plugin;
    private final ProxyServer proxyServer;

    /**
     * Create a new instance of {@link VelocityClasspathAppender}.
     *
     * @param plugin the Velocity plugin object, annotated with {@link Plugin}
     * @param proxyServer the instance of {@link ProxyServer}
     */
    public VelocityClasspathAppender(Object plugin, ProxyServer proxyServer) {
        if (!plugin.getClass().isAnnotationPresent(Plugin.class)) {
            throw new IllegalArgumentException("Not a plugin");
        }
        this.plugin = plugin;
        this.proxyServer = proxyServer;
    }

    @Override
    public void appendFileToClasspath(@NotNull Path path) {
        proxyServer.getPluginManager().addToClasspath(plugin, path);
    }
}
