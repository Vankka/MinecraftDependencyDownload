package dev.vankka.mcdependencydownload.fabric.classpath;

import dev.vankka.dependencydownload.classpath.ClasspathAppender;
import net.fabricmc.loader.launch.common.FabricLauncherBase;

import java.net.MalformedURLException;
import java.nio.file.Path;

/**
 * A {@link ClasspathAppender} that appends dependencies directly via Fabric methods.
 */
@SuppressWarnings("unused") // API
public class FabricClasspathAppender implements ClasspathAppender {

    @Override
    public void appendFileToClasspath(Path path) throws MalformedURLException {
        FabricLauncherBase.getLauncher().propose(path.toUri().toURL());
    }
}
