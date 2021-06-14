package dev.vankka.mcdependencydownload.velocity.classpath;

import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.proxy.ProxyServer;
import dev.vankka.dependencydownload.classpath.ClasspathAppender;

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
    public void appendFileToClasspath(Path path) {
        proxyServer.getPluginManager().addToClasspath(plugin, path);
    }
}
