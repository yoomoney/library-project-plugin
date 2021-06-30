package ru.yoomoney.gradle.plugins.library;

import io.spring.gradle.dependencymanagement.DependencyManagementPlugin;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.util.VersionNumber;
import ru.yoomoney.gradle.plugins.backend.build.JavaPlugin;
import ru.yoomoney.gradle.plugins.javapublishing.JavaArtifactPublishPlugin;
import ru.yoomoney.gradle.plugins.library.dependencies.CheckDependenciesPlugin;
import ru.yoomoney.gradle.plugins.release.ReleasePlugin;

import java.util.Arrays;
import java.util.Collection;

/**
 * Входная точка library-plugin'а, подключает все необходимые плагины-зависимости.
 *
 * @author Kirill Bulatov (mail4score@gmail.com)
 * @since 22.12.2016
 */
public class LibraryProjectPlugin implements Plugin<Project> {

    private static final Collection<Class<?>> PLUGINS_TO_APPLY = Arrays.asList(
            JavaArtifactPublishPlugin.class,
            ReleasePlugin.class,
            JavaPlugin.class,
            CheckDependenciesPlugin.class,
            DependencyManagementPlugin.class
    );

    @Override
    public void apply(Project project) {
        if (VersionNumber.parse(project.getGradle().getGradleVersion()).compareTo(VersionNumber.parse("6.0.1'")) < 0) {
            throw new IllegalStateException("Gradle >= 6.0.1 is required");
        }

        new JavaArtifactPublishConfigurator().configure(project);
        PLUGINS_TO_APPLY.forEach(pluginClass -> project.getPluginManager().apply(pluginClass));
        ExtensionConfigurator.configure(project);
    }

}
