package ru.yandex.money.gradle.plugins.library;

import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.util.VersionNumber;
import ru.yandex.money.gradle.plugin.architecturetest.ArchitectureTestPlugin;
import ru.yandex.money.gradle.plugins.backend.build.JavaModulePlugin;
import ru.yandex.money.gradle.plugins.javapublishing.JavaArtifactPublishPlugin;
import ru.yandex.money.gradle.plugins.library.git.expired.branch.GitExpiredBranchPlugin;
import ru.yandex.money.gradle.plugins.release.ReleasePlugin;
import ru.yandex.money.gradle.plugins.task.monitoring.BuildMonitoringPlugin;

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
            JavaModulePlugin.class,
            JavaArtifactPublishPlugin.class,
            ReleasePlugin.class,
            GitExpiredBranchPlugin.class,
            BuildMonitoringPlugin.class,
            ArchitectureTestPlugin.class
    );

    @Override
    public void apply(Project project) {
        if (VersionNumber.parse(project.getGradle().getGradleVersion()).compareTo(VersionNumber.parse("4.10.2'")) < 0) {
            throw new IllegalStateException("Gradle >= 4.10.2 is required");
        }
        ExtensionConfigurator.configurePublishPlugin(project);
        PLUGINS_TO_APPLY.forEach(pluginClass -> project.getPluginManager().apply(pluginClass));
        ExtensionConfigurator.configure(project);
    }

}
