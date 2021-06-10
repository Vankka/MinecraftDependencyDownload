package dev.vankka.mcruntimedependencydownload.classloader;

import dev.vankka.dependencydownload.classpath.ClasspathAppender;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.*;

/**
 * Loads a jar from a resource into a temporary file to avoid illegal reflection.
 * Extends {@link ClasspathAppender} for use with {@link dev.vankka.dependencydownload.DependencyManager}.
 */
public class JarInJarClassLoader extends URLClassLoader implements ClasspathAppender {

    static {
        ClassLoader.registerAsParallelCapable();
    }

    public JarInJarClassLoader(String tempFilePrefix, URL resourceURL, ClassLoader parent) throws IOException {
        super(new URL[] {asTempFileURL(tempFilePrefix, resourceURL)}, parent);
    }

    private static URL asTempFileURL(String filePrefix, URL resourceURL) throws IOException {
        if (filePrefix == null) {
            throw new NullPointerException("filePrefix");
        } else if (resourceURL == null) {
            throw new NullPointerException("resourceURL");
        }

        Path tempFile = Files.createTempFile(filePrefix, ".jar.tmp");
        try (InputStream inputStream = resourceURL.openStream()) {
            Files.copy(inputStream, tempFile, StandardCopyOption.REPLACE_EXISTING);
        }

        tempFile.toFile().deleteOnExit();
        return tempFile.toUri().toURL();
    }

    @Override
    public void close() throws IOException {
        super.close();

        URL url = getURLs()[0];
        if (url != null) {
            Path path;
            try {
                path = Paths.get(url.toURI());
            } catch (URISyntaxException ignored) {
                return;
            }
            Files.deleteIfExists(path);
        }
    }

    @Override
    public void appendFileToClasspath(Path path) throws MalformedURLException {
        addURL(path.toUri().toURL());
    }
}
